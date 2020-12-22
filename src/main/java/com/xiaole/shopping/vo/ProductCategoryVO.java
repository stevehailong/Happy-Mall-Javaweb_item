package com.xiaole.shopping.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryVO {
    private Integer id;
    private String name;
    private List<ProductCategoryVO> children;
    private String bannerImg; //底部图片
    private String topImg;   //左上图片
    private List<ProductVO> ProductVOlist;

    public ProductCategoryVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
