
package com.yoziming.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yoziming
 * @Date: 2022/1/15 14:54
 * @Description:
 */

@Data
public class MemberPrice {

  private Long id;
  private String name;
  private BigDecimal price;

}
