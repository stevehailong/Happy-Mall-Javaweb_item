package com.xiaole.shopping.Enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum GenderEnums {

    WOMAN(0, "女"),
    MAN(1, "男");

    @EnumValue
    private Integer code;
    private String value;

    GenderEnums(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
