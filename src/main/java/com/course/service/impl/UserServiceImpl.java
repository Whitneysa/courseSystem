package com.course.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.course.mapper.UserMapper;
import com.course.pojo.User;
import com.course.pojo.dto.LoginDTO;
import com.course.service.UserService;
import com.course.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author YangHaixiong
 * @date 2023/12/23 14:40
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean hasPermission(List<String> roles, String token ) {

        Long id = null;
        try {
            id = Long.valueOf(token);
        }catch (Exception e) {
            log.error("token转换异常", e);
            throw new RuntimeException("token转换错误，请输入正确类型token");
        }

        User user = userMapper.getUserById(id);
        if (Objects.isNull(user)) {
            log.error("该用户不存在");
            throw new RuntimeException("该用户不存在，请重新登陆");
        }

        return roles.contains(user.getRole());
    }

    @Override
    public Long register(User user) {
        if (!Arrays.asList("student", "admin").contains(user.getRole())) {
            throw new RuntimeException("系统只支持student，admin两种角色，请重新输入");
        }
        long id = System.currentTimeMillis();
        String password = user.getPassword();
        String realPassword = MD5Util.encode(password);
        user.setPassword(realPassword);
        user.setId(id);
        userMapper.inert(user);
        return id;
    }

    @Override
    public Long login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        User user = userMapper.getUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new RuntimeException("该用户不存在，请重新输入用户名密码");
        }

        String realPassword = MD5Util.encode(loginDTO.getPassword());
        if (!realPassword.equals(user.getPassword())) {
            throw new RuntimeException("密码错误，请重新输入");
        }

        user.setOnline(true);
        userMapper.update(user.getId());
        return user.getId();
    }
}
