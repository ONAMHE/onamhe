package cn.leyou.item.mapper;


import cn.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
@Repository   //将对象放到ioc容器中使用mapperscan后可以不写这个但是自动注入该类的时候会误报，最好加上
public interface HCategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {
    //SelectByIdListMapper方法可以通过多个id来查询需要的之，返回一个list的集合，同时需要传递一个list的id集合
    //继承这个接口的时候需要传递两个泛型，一个是返回值的类型，一个是传递值的泛型
    @Select("SELECT * from tb_category WHERE id=(SELECT category_id FROM `tb_category_brand` WHERE brand_id=#{bid});")
    Category queryByBrandId(Long bid);
}
