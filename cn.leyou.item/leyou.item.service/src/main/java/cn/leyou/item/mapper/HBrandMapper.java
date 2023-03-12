package cn.leyou.item.mapper;

import cn.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface HBrandMapper extends Mapper<Brand> {
    @Insert("INSERT INTO tb_category_brand (category_id,brand_id) VALUES (#{cid},#{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid,@Param("bid") Long bid);
    @Update("UPDATE tb_category_brand SET category_id=#{cid} WHERE brand_id=#{bid}")
    void updateCategoryAndBrand(@Param("cid")Long cid,@Param("bid")Long bid);

    @Select("SELECT * FROM tb_brand WHERE id IN(SELECT brand_id FROM `tb_category_brand` WHERE category_id=#{cid})")
    List<Brand> queryBrandByCategoryId(Long cid);
}
