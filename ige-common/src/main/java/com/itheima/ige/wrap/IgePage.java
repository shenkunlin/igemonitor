package com.itheima.ige.wrap;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author：
 * @Description：
 ***/
@Data
@NoArgsConstructor
public class IgePage {

    private List<IgeAttribute> data;

    private Integer totalPages;

    private Integer size;

    public IgePage(List<IgeAttribute> data, Integer totalPages, Integer size) {
        this.data = data;
        this.totalPages = totalPages;
        this.size = size;
    }

    public IgePage(List<IgeAttribute> data, Integer totalPages) {
        this.data = data;
        this.totalPages = totalPages;
    }
}
