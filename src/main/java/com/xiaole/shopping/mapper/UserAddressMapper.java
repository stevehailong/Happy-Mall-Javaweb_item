package com.xiaole.shopping.mapper;

import com.xiaole.shopping.entity.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    @Delete("DELETE FROM `user_address` WHERE `id` = #{id};")
    boolean deletbyId(Integer id);
}
