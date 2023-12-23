package com.course.service;
import com.course.pojo.User;
import com.course.pojo.dto.LoginDTO;

import java.util.List;

/**
 * @author YangHaixiong
 * @date 2023/12/23 14:39
 */

public interface UserService {

    boolean hasPermission(List<String> roles, String token);

    Long register(User user);

    Long login(LoginDTO loginDTO);
}
