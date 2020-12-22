package com.xiaole.shopping.service.impl;

import com.xiaole.shopping.entity.User;
import com.xiaole.shopping.mapper.UserMapper;
import com.xiaole.shopping.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}
