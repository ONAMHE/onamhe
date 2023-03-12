package cn.leyou.item.controller;

import cn.leyou.item.item.service.SelectGroupAndParamService;
import cn.leyou.item.pojo.Category;
import cn.leyou.item.pojo.SpecGroup;
import cn.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import javax.xml.ws.Response;
import java.util.List;

@Controller
@RequestMapping("spec")
public class SelectGroupAndParamController {
    @Autowired
    private SelectGroupAndParamService addGroupAndParamService;

    /**
     * 商品组的查询
     * @param cid  category_id
     * @return  返回商品组集合
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroup(@PathVariable("cid")Long cid){
            List<SpecGroup> groups = addGroupAndParamService.queryGroup(cid);
            if(CollectionUtils.isEmpty(groups)){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(groups);
    }
    /**
     * 根据调价查询spu的相关规格参数
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParam(
            @RequestParam(value="cid",required = false)Long cid,
            @RequestParam(value="gid",required = false)Long gid,
            @RequestParam(value="searching",required = false)Boolean searching,
            @RequestParam(value="generic",required = false)Boolean generic){
        List<SpecParam> groups=addGroupAndParamService.queryParam(cid,gid,searching,generic);
        if(CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(groups);
    }



    /**
     * 新增&&修改分组
     * @param specGroup
     * @return
     */
    @RequestMapping("group")
    public ResponseEntity<Void> addGroup(@RequestBody SpecGroup specGroup){
        Long cid= specGroup.getCid();
        String name=specGroup.getName();
        Long id=specGroup.getId();
            addGroupAndParamService.addGroup(cid,name,id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除组
     * @param id
     * @return
     */
    @RequestMapping("group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable(value = "id")Long id){
        addGroupAndParamService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

}
