package cn.leyou.item.controller;


import cn.leyou.item.item.service.BrandService;
import cn.leyou.item.item.service.CategoryService;
import cn.leyou.item.pojo.Brand;
import cn.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 所有品牌查询
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> findAll(@RequestParam(value = "pid",defaultValue = "0")Long pid){
        if(pid == null || pid < 0){
//            400参数不合法
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
//            楼上的三个方法效果一样，但是代码越来越简洁
        }

        List<Category> list=categoryService.findAll(pid);
        if(CollectionUtils.isEmpty(list)){//也可以判断list.size 和  list==null  但是这样写更加高级
//            404资源未找到
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            return ResponseEntity.notFound().build();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        200请求成功
        return ResponseEntity.ok(list);
//        程序出错的时候会自动响应500不用单独来判断

    }

    /**
     * 品牌修改时通过品牌id来查询到品牌的分类category信息
     * @param bid
     * @return 返回一个品牌
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<Category> queryByBrandId(@PathVariable("bid")Long bid){
  Category category= categoryService.queryByBrandId(bid);
  if (category==null){
      return ResponseEntity.notFound().build();
  }

    return ResponseEntity.ok(category);
    }




























}
