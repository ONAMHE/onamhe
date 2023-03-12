package cn.leyou.common.pojo;

import java.util.List;

public class PageResult<T> {
    private Long total;//数据总数
    private Integer totalPage;//总页数
    private List<T> items;//当前页数据集合
    //这里使用泛型的话那么就需要在方法体中声明

    public PageResult() {
    }
    public PageResult(Long total, List<T> list) {
            this.total=total;
            this.items=list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
