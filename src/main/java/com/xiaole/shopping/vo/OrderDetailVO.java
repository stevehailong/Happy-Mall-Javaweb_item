package com.xiaole.shopping.vo;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class OrderDetailVO {
    private String name; //product
    private String fileName; // product
    private Integer quantity;   //detail
    private Float price;  //product
    private Float cost; //detail
}
