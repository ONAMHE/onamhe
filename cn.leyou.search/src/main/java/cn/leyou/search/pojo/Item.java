package cn.leyou.search.pojo;

import net.bytebuddy.asm.Advice;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "item",createIndex = true)
public class Item {
    @Id
    private Long id;
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word",store = true,index = true)
    private String title; //标题
    
    @Field(type = FieldType.Keyword,store = true,index = true)
    private String category;// 分类
    
    @Field(type = FieldType.Keyword,store = true,index = true)
    private String brand; // 品牌
    
    @Field(type = FieldType.Double,store = true,index = true)
    private Double price; // 价格
    
    @Field(type = FieldType.Keyword,store = true,index = true)
    private String images; // 图片地址

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Item(Long id, String title, String category, String brand, Double price, String images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.images = images;
    }
    public Item(){};
}