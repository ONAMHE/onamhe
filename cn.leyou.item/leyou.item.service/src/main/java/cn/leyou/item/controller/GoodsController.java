package cn.leyou.item.controller;

import cn.leyou.common.pojo.PageResult;
import cn.leyou.item.item.service.GoodsService;
import cn.leyou.item.pojo.SpuBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;

@Controller
@RequestMapping("spu")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 商品查询页面
     * @param key
     * @param saleable  是否在售
     * @param page
     * @param rows
     * @return 商品集合以及品牌
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<SpuBo>> queryGoods(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "saleable",required = false)Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows
            ) {
        PageResult<SpuBo> goods = goodsService.queryGoods(key,saleable,page,rows);
        if (CollectionUtils.isEmpty(goods.getItems())) {

            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goods);
    }
    @GetMapping("detail/{gid}")
    public ResponseEntity<Void> updateGood(@PathVariable("gid")Long id){
        return ResponseEntity.ok().build();
    }

}
