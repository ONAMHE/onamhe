package cn.leyou.item.controller;


import cn.leyou.common.pojo.PageResult;
import cn.leyou.item.item.service.BrandService;
import cn.leyou.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    /**
     * 通过cid来查询相关的所有的品牌列表
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategoryId(@PathVariable("cid")Long cid){
        List<Brand> brands=brandService.queryBrandByCategoryId(cid);
        if (CollectionUtils.isEmpty(brands)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }

    /**
     * 品牌新增&&品牌修改
     * @param brand  内部包装了新增品牌所需要的数据
     * @param cids  中间表
     * @return
     */
    @RequestMapping
    public ResponseEntity<Void> addBrand(Brand brand, @RequestParam(name = "cids")List<Long> cids){
//        使用vue安装qs工具后可以将对象转换为字符串（stringity）它可以直接使用楼上的方法来接收
//        接收json数据只能有一个对象，同时还要加@RequestBody   响应json数据也必须加@ResponseBody
        if (brand.getId()==null){
            brandService.saveBrand(brand,cids);
        }else {
            brandService.updateBrand(brand,cids);
        }

        //接收前端的json数据需要加注解@RequestBody  否者无法正常接收
//        return new ResponseEntity<>(HttpStatus.CREATED);
//        create表示状态码201表示数据已经成功创建并保存
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 品牌查询 | 分页查询
     * @param key 关键词搜索
     * @param page  当前页
     * @param rows  每页显示数据多少
     * @param sortBy  是否排序
     * @param desc  正|倒序
     * @return  返回一个数据集合列表
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> find(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy",required = false)String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc
    ){
        PageResult<Brand> list=brandService.findAll(key,page,rows,sortBy,desc);
        if(CollectionUtils.isEmpty(list.getItems())){
            //楼上的判断也可以是判断size>0 不等于null  但是这个方法更加的高级
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(list);
    }

}




















