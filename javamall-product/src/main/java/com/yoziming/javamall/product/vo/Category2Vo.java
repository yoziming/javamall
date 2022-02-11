package com.yoziming.javamall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: yoziming
 * @Date: 2022/4/19 21:43
 */
@Data
public class Category2Vo {

    String name;
    Long id;
    List<Category3Vo> cat3;

}
