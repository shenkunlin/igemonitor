package com.itheima.ige.wrap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：
 * @Description：
 ***/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqPage {

    private Integer page;

    private Integer size;
}
