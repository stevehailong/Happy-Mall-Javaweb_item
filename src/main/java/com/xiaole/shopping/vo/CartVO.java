package com.xiaole.shopping.vo;

import lombok.Data;

/**
 * 因为没有包含以下属性的实体类，所以创建视图需要的VO类*/

@Data
public class CartVO {
    private Integer id;
    private Integer quantity;
    private Float cost;
    private Float price;
    private String name;
    private String fileName;
    private Integer productId;
    private Integer stock;
}
