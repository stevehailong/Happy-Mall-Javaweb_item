package com.xiaole.shopping.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component  //加入IOC容器
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**结合@TableField注解，操作这个类对象的时候，会自动触发这个拦截器
     * */

    //每注册一个用户，自动添加当前时间
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
    }
}
