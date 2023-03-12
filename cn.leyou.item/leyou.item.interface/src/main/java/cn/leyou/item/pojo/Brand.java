package cn.leyou.item.pojo;

import javax.persistence.*;

@Table(name = "tb_brand")
public class Brand {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
//        @Transient就是表里没有这个字段就要添加这个注解
        private String name;// 品牌名称
        private String image;// 品牌图片
        private Character letter;//char的封装类型，在
        // getter setter 略

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }
}
