package com.yoziming.common.to;

import lombok.Data;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/4/19 17:13
 * @Description:
 */
@Data
public class CategoryVo {

    String name;
    Long id;
    List<Category2Vo> cat2;
}
