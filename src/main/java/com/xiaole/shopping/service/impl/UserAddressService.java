package com.xiaole.shopping.service.impl;

import com.xiaole.shopping.entity.UserAddress;
import com.xiaole.shopping.mapper.UserAddressMapper;
import com.xiaole.shopping.service.IUserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Service
public class UserAddressService extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public boolean deletbyId(Integer id) {
        userAddressMapper.deleteById(id);
        return true;
    }
}
