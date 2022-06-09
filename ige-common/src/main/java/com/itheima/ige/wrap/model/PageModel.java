package com.itheima.ige.wrap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author：
 * @Description：批量静态化
 ***/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageModel implements Serializable {

    //当前页
    private Integer page;

    //总页数
    private Integer pages;

    //每页显示条数
    private Integer size;

    //总记录数
    private Long total;

    //要生成的页面元素
    private List<DataModel> data;

    public PageModel(Integer size,Integer page,Long total, List<DataModel> data) {
        this.total = total;
        this.data = data;
        this.size = size;
        this.page = page;
        this.pages= (int)(total%size==0? total/size:total/size+1);
    }
}
