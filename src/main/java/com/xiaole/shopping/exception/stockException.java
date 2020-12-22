package com.xiaole.shopping.exception;


import com.xiaole.shopping.Enums.ResultEnum;

//库存异常自定义
public class stockException extends RuntimeException {
    public stockException(String error) {
        super(error);
    }

    //重载
    public stockException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
    }
}
