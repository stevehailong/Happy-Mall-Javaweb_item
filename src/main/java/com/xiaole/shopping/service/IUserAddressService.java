package com.xiaole.shopping.service;

import com.xiaole.shopping.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
public interface IUserAddressService extends IService<UserAddress> {
    @Delete("DELETE FROM `user_address` WHERE `id` = #{id};")
    boolean deletbyId(Integer id);
}
