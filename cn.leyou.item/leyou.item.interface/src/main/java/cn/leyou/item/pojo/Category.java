package cn.leyou.item.pojo;

import javax.persistence.*;

@Table(name="tb_category")
public class Category {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Long id;
        private String name;
        private Long parentId;
//        @Transient表里没有这个字段就要添加这个字段

//        变量不建议以is开头否者在自动创建get和set的时候就会少一个is需要手动添加
        private Boolean isParent; // 注意isParent生成的getter和setter方法需要手动加上Is
        private Integer sort;
        // getter和setter略

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}

