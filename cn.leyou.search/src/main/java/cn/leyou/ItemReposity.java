package cn.leyou;

import cn.leyou.search.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ItemReposity extends ElasticsearchRepository<Item,Long>, Repository<Item,Long> {
    //继承 ElasticsearchRepository 后就可以实现crud的操作
    //继承时需要添加实现类的泛型还有主键id的类型


    //简单的crud也可以实现repository方法
    List<Item> findByTitle(String title);
    List<Item> findByCategoryAndBrand(String category,String brand);
}
