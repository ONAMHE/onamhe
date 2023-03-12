package cn.leyou.item.item.service;

import cn.leyou.item.mapper.HBrandMapper;
import cn.leyou.item.mapper.HCategoryMapper;
import cn.leyou.item.pojo.Brand;
import cn.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService{
    @Autowired
    private HBrandMapper hBrandMapper;
    @Autowired
    private HCategoryMapper hCategoryMapper;

    /**
     * 根据父节点查询子节点
     * @param pid
     * @return
     */

    public List<Category> findAll(Long pid) {
        Category category=new Category();
        category.setParentId(pid);
        return hCategoryMapper.select(category);
    }

    /**
     * 通过多个id来查询分类名称
     * @param ids
     * @return
     */
    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> categorys = hCategoryMapper.selectByIdList(ids);
        return categorys.stream().map(category -> category.getName()).collect(Collectors.toList());
        //使用楼上的箭头函数如果有大括号那么就必须要有返回值，否者就无法使用，同时直接报错

    }

    /**
     * 用于品牌修改时查询category分类信息
     * @param bid
     */
    public Category queryByBrandId(Long bid) {
         Category category = hCategoryMapper.queryByBrandId(bid);
        return category;
    }
}

















