package com.xiaole.shopping.vo;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class OrderVO {
    private Integer id;
    private String loginName;
    private String serialnumber;
    private String userAddress;
    private Float cost;
    private List<OrderDetailVO> orderDetailVOList;

    public OrderVO(Integer id, String loginName, String serialnumber, String userAddress, Float cost) {
        this.id = id;
        this.loginName = loginName;
        this.serialnumber = serialnumber;
        this.userAddress = userAddress;
        this.cost = cost;
    }
}
