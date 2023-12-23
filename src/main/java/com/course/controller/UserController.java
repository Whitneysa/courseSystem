package com.course.controller;
import com.course.common.response.RespResult;
import com.course.common.response.Result;
import com.course.pojo.User;
import com.course.pojo.dto.LoginDTO;
import com.course.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author YangHaixiong
 * @date 2023/12/23 14:03
 */

@Api("用户管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("注册")
    @PostMapping ("/register")
    public Result<Long> register(@RequestBody @Valid User user) {
        Long id = userService.register(user);
        return RespResult.success("注册成功，token：" + id);
    }


    @ApiOperation("登陆")
    @PostMapping("/login")
    public Result<String> register(@Valid @RequestBody LoginDTO loginDTO) {
        userService.login(loginDTO);
        return RespResult.success("登陆成功");
    }
}
