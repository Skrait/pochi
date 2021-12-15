package com.jg.pochi.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页类
 * @Author Peekaboo
 * @Date 2021/11/19 17:36
 */
@Data
public class Page<T> implements Serializable {

    //当前页数,从第一页开始
    private Integer pageNumber;

    //每页显示条数
    private Integer pageSize;

    //总条数
    private Integer totalCount;

    //总页数
    private Integer totalPage;

    //数据
    private List<T> list;

    /**
     * 传参数据
     * 由于传参不固定因此用Map，其他情况一般不随便用
     */
    private Map<String, Object> params = new HashMap<>(8);

    /**
     * 排序列
     */
    private String sortColumn;

    /**
     * 排序方式，asc或者desc
     */
    private String sortMethod;

    //由于数据库是从第0条开始的
    /**
     * 获取index
     * 因为MySql的Index是从0开始的检索条数,
     * 因此需要对前端传进来的当前页数(pageNumber)转换成Index
     * @return
     */
    public Integer getIndex(){
        return (pageNumber - 1) * pageSize;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        if (this.pageNumber == null || this.pageNumber < 1) {
            this.pageNumber = 1;
        }
    }

    /**
     * 在设置总条数时，计算并设置总页数
     */
    public void setTotalCount(Integer totalCount){
        this.totalCount = totalCount;
        this.totalPage = (int)Math.ceil(totalCount * 1.0 / pageSize);
    }
}
