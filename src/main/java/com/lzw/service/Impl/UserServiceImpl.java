package com.lzw.service.Impl;

import com.lzw.entity.User;
import com.lzw.dao.UserMapper;
import com.lzw.service.UserService;
import com.lzw.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public User login(String username, String password) {
        return userMapper.login(username, MD5Utils.code(password));
    }
}
