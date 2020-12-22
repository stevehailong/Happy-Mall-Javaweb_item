package com.xiaole.shopping.vo;

/**
 * 简化实体类Product的属性*/

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductVO {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 价格
     */
    private Float price;
    /**
     * 图片
     */
    private String fileName;
}
