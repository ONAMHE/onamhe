package cn.leyou.item.item.service;

import cn.leyou.common.pojo.PageResult;
import cn.leyou.item.mapper.HBrandMapper;
import cn.leyou.item.mapper.SpuMapper;
import cn.leyou.item.pojo.Brand;
import cn.leyou.item.pojo.Spu;
import cn.leyou.item.pojo.SpuBo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private HBrandMapper hBrandMapper;
    @Autowired
    private CategoryService categoryService;

    /**
     * 商品查询的service
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuBo> queryGoods(String key,Boolean saleable,Integer page,Integer rows){
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
       if (StringUtil.isNotEmpty(key)){
           criteria.andLike("title","%"+key+"%");
       }
       if (saleable != null) {
           criteria.andEqualTo("saleable",saleable);
       }
        PageHelper.startPage(page,rows);//分页
        List<Spu> spus = spuMapper.selectByExample(example);
        PageInfo<Spu> info = new PageInfo<>(spus);//方便得到后面夜宿等数据

        List<SpuBo> spuBos = spus.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu,spuBo);
            //设置品牌名称
         Brand brand = hBrandMapper.selectByPrimaryKey(spu.getBrandId());
         spuBo.setBname(brand.getName());
         //设置分类名称     //使用arrays.tolist时要注意只能再括号中写，不能使用add,remove,clear等方法
         List<Long> ids = Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3());
         List<String> names = categoryService.queryNamesByIds(ids) ;
         spuBo.setCname(StringUtils.join(names,"-"));
         return spuBo;//加大括号的话那么就必须要有返回值
        }).collect(Collectors.toList());
        PageResult<SpuBo> pageResult = new PageResult<SpuBo>(info.getTotal(),spuBos);
        return pageResult;
    }
}







































