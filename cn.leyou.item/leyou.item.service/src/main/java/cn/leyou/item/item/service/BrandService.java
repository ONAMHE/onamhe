package cn.leyou.item.item.service;

import cn.leyou.common.pojo.PageResult;
import cn.leyou.item.mapper.HBrandMapper;
import cn.leyou.item.pojo.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
public class BrandService{
    @Autowired
    private HBrandMapper hBrandMapper;

    public PageResult<Brand> findAll(String key, Integer page, Integer rows, String sortBy, Boolean desc) {



        //通用mapper如果直接使用delete方法的话那么如果是一个对象那么就表示只有当该对象中所有的数据都和某条数据
        //对应的时候才会删除，先当与对象的各个属性之间是用and来连接的  name="j"and id="df'这种的
//        Example example1=new Example(Brand.class);
//        Example.Criteria criteria1 = example1.createCriteria();
//        criteria1.andEqualTo("name",2);//表示name这个键值等于2的条件
//        criteria1.andGreaterThan("age",23).andGreaterThan("price",45);//这个表示当age大于否个值的时候
//        example1.setOrderByClause("id desc");//这个就表示将id主键以  desc的排序方式来排序
//        //可以连着写多个条件效果都一样
//        Example.Criteria criteria2 = example1.createCriteria();
//        criteria2.andEqualTo("name",4);
//        example1.or(criteria2);


//使用通用mapper如果时传递入一个对象那么就表示是以对象中有的参数来进行查询
        //相当于是使用了and的语句来拼接字符串









        //此处有疑问
        Example example=new Example(Brand.class);
        Example.Criteria criteria=example.createCriteria();

        if(StringUtil.isNotEmpty(key)){
           criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }
        PageHelper.startPage(page,rows);
        //判断是否排序
        if(StringUtil.isNotEmpty(sortBy)){
            example.setOrderByClause(sortBy+" "+(desc ? "desc":"asc"));
        }
        List<Brand> list=this.hBrandMapper.selectByExample(example);
        //通用mapper就只有这两个方法，之前在contollor中的是因为需要返回pageinfo在这里不需要所以就写在一起了
        PageInfo<Brand> info=new PageInfo<Brand>(list);
        return new PageResult<>(info.getTotal(),info.getList());//使用这个返回结果集需要有构造参数
    }

    /**
     * 品牌新增service
     * @param brand
     * @param cids
     */
    @Transactional
    //加事物后可以不用判断某个表是否新增成功，因为一个新增失败那么就会全部失败
    public void saveBrand(Brand brand, List<Long> cids) {
//        insert会将所有的参数一起写上，但是如果参数为空那么就设置值为null但是insertselective会将为空的参数直接忽略掉，这样可以提高数据库效率
            hBrandMapper.insertSelective(brand);//如果新增成功那么一定是返回1

            cids.forEach(cid -> {
                this.hBrandMapper.insertCategoryAndBrand(cid,brand.getId());
      });
    }

    /**
     * 根据品牌id来实现品牌的更新，他和品牌新增共用同一个contollor方法
     * @param brand
     * @param cids
     */
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        hBrandMapper.updateByPrimaryKeySelective(brand);
        cids.forEach(cid -> {
            //由于没有中间表的实体类所以只能用原始方法来修改数据
            this.hBrandMapper.updateCategoryAndBrand(cid,brand.getId());
        });
    }

    /**
     * 通过category来查询对应的所有的品牌列表
     * @param cid
     * @return
     */
    public List<Brand> queryBrandByCategoryId(Long cid) {
        List<Brand> brands=hBrandMapper.queryBrandByCategoryId(cid);
        return brands;
    }


//    使用通用mappper注意如下

    //注意增删改需要开启啊事物

    //sql语句的拼接中#表示占位符，$表示直接拼接   前者效率更高所以使用前者最好
//    当有多个参数的时候需要使用@param("djk")来指明参数
}
