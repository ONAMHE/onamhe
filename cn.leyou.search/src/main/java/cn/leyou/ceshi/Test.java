package cn.leyou.ceshi;

import cn.leyou.ItemReposity;
import cn.leyou.search.pojo.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;//实现了楼下接口的子类，相当是楼下接口的拓展
    //楼下的方法使用来序列化json数据的，如果是将数据转化为json的数据那么就是用writevalueasString
    //反序列化就用readvalue这个方法
    private static final ObjectMapper mapper=new ObjectMapper();
    //楼上的反序列化还可以使用readvalue(数据源,new TypeReference<Map<String,String>>)这个方法来实现
    //楼上的表示将数据源反序列化为一个指定类型的数据，可以直接写一个对象的class类比如item.class
    @Autowired
    private ItemReposity itemReposity;
    @org.junit.Test
    public void testIndex(){
        IndexOperations indexOperations=elasticsearchRestTemplate.indexOps(Item.class);
        indexOperations.create();//创建一个库 index
        indexOperations.putMapping();//创建映射
//        indexOperations.delete();//删除库


    }
    @org.junit.Test
    public void testCreat(){
//        Item item = new Item(1L, "小米手机7", " 手机",
//                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
//        this.itemReposity.save(item);//实现elasticsearchresponseity类的时候要添加实现类泛型和主键id类型

        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        // 接收对象集合，实现批量新增
        itemReposity.saveAll(list);
//        当id相同的时候就是修改，所以没有单独修改的方法

        this.itemReposity.deleteById(1L);//表示通过指定的id来删除数据
        this.itemReposity.deleteAll();//删除所有的数据
    }
    @org.junit.Test
    public void find(){
        Optional<Item> byId = this.itemReposity.findById(1L);
        //取出来的optional可以直接通过get()方法来拿到单个item
        Iterable<Item> all = this.itemReposity.findAll();
        //得到的数据没有数组长度
        System.out.println(byId.get().getImages());
        for (Item item:all){
            System.out.println(item.getImages());
        }
    }
    @org.junit.Test
    public void findBySort(){
        Iterable<Item> price = this.itemReposity.findAll(Sort.by("price").descending());
        //这个方法是通过指定的键来进行排序查询，需要传递一个排序的主键列，和排序类型（升序降序） escending
        for (Item item :
                price) {
            System.out.println(item.getPrice());
        }
        price.forEach(System.out::println);
        //表示将foreach便利的作为参数到括号中的地方去直接输出
    }
    @org.junit.Test
    public void findByTitle(){
        //通过自定义的方法来查询
        List<Item> t = this.itemReposity.findByTitle("手机");
        for (Item ite :t) {
            System.out.println(ite.getImages());
        }
    }
    @org.junit.Test
    public void findByCategoryAndBrand(){
        List<Item> br = this.itemReposity.findByCategoryAndBrand(" 手机", "小米");
        //通过自定义的方法来查询

//        自定义包括and is  or not between before after like 等
    }
    @org.junit.Test
    public void indexList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(6L, "小米手机11", "手机", "小米", 32199.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(7L, "坚果手机R114", "手机", "锤子", 36499.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(8L, "华为META1011", "手机", "华为", 44199.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(9L, "小米Mix2S11", "手机", "小米", 42929.00, "http://image.leyou.com/13123.jpg"));
        list.add(new Item(10L, "荣耀V101", "手机", "华为", 24799.00, "http://image.leyou.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemReposity.saveAll(list);
    }
    @org.junit.Test
    public void searchTest() {
        //先构建查询条件
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "手机");
        //构建分页条件    参数分别表示当前是第几页和当前页显示的数据量
        PageRequest pageRequest=PageRequest.of(0,4);
        //构建查询条件
        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageRequest).build();
       //开始查询
        SearchHits<Item> search = elasticsearchRestTemplate.search(build, Item.class);
        //创建分页需要的list集合
        List<Item> list=new ArrayList<>();
//        构建分页条件结果集
        PageImpl<Item> page=new PageImpl<>(list,pageRequest,search.getTotalHits());
        System.out.println(page.getTotalPages()+"总页数");
        System.out.println(page.getTotalElements()+"总条数");
        for (SearchHit<Item> item :
                search) {
            System.out.println(item.getContent().getTitle());
        }
    }
    @org.junit.Test
    public void testNative(){
        //创建查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //构建分页条件  第一个条件是显示的页码 ，第二个是一页显示多少数据
        PageRequest pageRequest= PageRequest.of(1, 2);
        //构建查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title","小米")).withPageable(pageRequest);
       //开始查询
        SearchHits<Item> search = this.elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Item.class);
        List<Item> list=new ArrayList<>();
        for (SearchHit<Item> item: search) {
           Item ite=item.getContent();
            System.out.println(ite.getTitle());
            list.add(ite);
        }
        //构建分页结果集   使用这个可以不用将向list集合中传递任何参数，但是这样后面就无法通过分页结果集来获得content
        PageImpl<Item> page=new PageImpl<>(list,pageRequest,search.getTotalHits());
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent().get(1).getBrand());
    }
    @org.junit.Test
    public void sortTest(){
        //先创建查询构建器
        NativeSearchQueryBuilder builder=new NativeSearchQueryBuilder();
        //创建分页条件
        PageRequest page=PageRequest.of(0,4);
        //通过sortbuilder来实现排序不建议使用
//      SortBuilder sortBuilder=SortBuilders.fieldSort("price").order(SortOrder.ASC);
        //通过sortby来实现排序是最建议使用的方法
      builder.withSort(Sort.by("price").descending()).withQuery(QueryBuilders.matchQuery("title","手机"));
      //实现排序条件查询   使用这个麻烦但是不使用无法获得分页
      SearchHits<Item> search = elasticsearchRestTemplate.search(builder.build(), Item.class);
      List<Item> list = new ArrayList<>();
        for (SearchHit<Item> ite :
                search) {
           list.add(ite.getContent());//可以不用添加到分页结果集中
        }
      PageImpl<Item> pageinfo=new PageImpl<>(list,page,search.getTotalHits());
        for (Item item :pageinfo
             ) {
            System.out.println(item.getPrice());
        }

    }


    /**在取出桶中数据的时候都是以parse开头的数据类型来进行强转，例如  parsestring parseavg等
     * 对数据进行聚合为桶的相关操作
     */

@org.junit.Test
    public void termsAgg() {
    //创建查询构建器
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withPageable(PageRequest.of(0, 1))
                .withSort(Sort.by("price").descending());
        //创建聚合依据规则
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("brandAgg").field("brand");
        //查询构建器添加聚合规则
       builder.withAggregations(termsAggregationBuilder);
       //添加结果集字段，第一个参数表示包含那些字段，第二个表示不包含那些字段，如果用楼下的那么就表示那个字段都不保留
       builder.withSourceFilter(new FetchSourceFilter(new String[]{"title"},null));
       //构建查询
        SearchHits<Item> searchHits = elasticsearchRestTemplate.search(builder.build(), Item.class);
        //得到所查询的所有的聚合桶
    Aggregations agg=(Aggregations) searchHits.getAggregations().aggregations();
    //将桶按照terms来创建为一个map集合
    Map<String, Aggregation> stringAggregationMap = agg.asMap();
//    将得到的数据转换为指定类型   这个类型由map集合中取出的类来决定
    ParsedStringTerms brandAgg =(ParsedStringTerms) stringAggregationMap.get("brandAgg");
//   得到聚合桶中的数据
    List<? extends Terms.Bucket> buckets = brandAgg.getBuckets();
    buckets.forEach(bucket -> {
//        得到聚合后分类的名称
        System.out.println(bucket.getKeyAsString());
//        得到聚合后没个分类的数据总数
        System.out.println(bucket.getDocCount());
    });
}


/**在取出桶中数据的时候都是以parse开头的数据类型来进行强转，例如  parsestring parseavg等
 * 平均聚合，就是将某个字段平均后得到参数
 */

@org.junit.Test
public void termsAvgAgg() {
    //创建查询构建器
    NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();


    //匹配查询
//    QueryBuilders.matchQuery("all",key)  这个方法表示传递一个有分隔符的字符串的key同时传递一个有
    //分隔符的值。那么该方法就会自动将字段匹配并查询

    builder.withQuery(QueryBuilders.matchQuery("all","na-na-al-a").operator(Operator.AND));

    //楼上的方法表示使用一个matchquery方法来自动将多个字段拼接到一起，
    //同时通过operator方法来判断多个字符串之间的拼接方式是and  or  还是其他




    builder.withPageable(PageRequest.of(0, 1))
            .withSort(Sort.by("price").descending());
    //创建聚合依据规则
    TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("brandAgg").field("brand");
   //添加一个第二个聚合查询的方式
    termsAggregationBuilder.subAggregation(AggregationBuilders.avg("price_avg").field("price"));
    //查询构建器添加聚合规则
    builder.withAggregations(termsAggregationBuilder);
    //添加结果集字段，第一个参数表示包含那些字段，第二个表示不包含那些字段，如果用楼下的那么就表示那个字段都不保留
    builder.withSourceFilter(new FetchSourceFilter(new String[]{"title"},null));
    //构建查询
    SearchHits<Item> searchHits = elasticsearchRestTemplate.search(builder.build(), Item.class);
    //得到所查询的所有的聚合桶
    Aggregations agg=(Aggregations) searchHits.getAggregations().aggregations();
    //将桶按照terms来创建为一个map集合
    Map<String, Aggregation> stringAggregationMap = agg.asMap();
//    将得到的数据转换为指定类型   这个类型由map集合中取出的类来决定
    ParsedStringTerms brandAgg =(ParsedStringTerms) stringAggregationMap.get("brandAgg");
//   得到聚合桶中的数据
    List<? extends Terms.Bucket> buckets = brandAgg.getBuckets();
    buckets.forEach(bucket -> {
//        得到聚合后分类的名称
        System.out.println(bucket.getKeyAsString());
//        得到聚合后没个分类的数据总数
        System.out.println(bucket.getDocCount());
        //获取子聚合中的聚合
        Aggregations aggregations = bucket.getAggregations();
        Map<String, Aggregation> map = aggregations.asMap();
        ParsedAvg price_avg =(ParsedAvg) map.get("price_avg");
        System.out.println(price_avg.getValue());
    });
}
}

















