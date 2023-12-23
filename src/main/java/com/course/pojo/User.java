package com.course.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author YangHaixiong
 * @date 2023/12/23 15:53
 */

@Data
@ApiModel
public class User {
    @ApiModelProperty("id")
    private Long id;

    @NotNull
    @ApiModelProperty("用户吗")
    private String username;

    @NotNull
    @ApiModelProperty("密码")
    private String password;

    @NotNull
    @ApiModelProperty("用户角色")
    private String role;

    @ApiModelProperty("是否在线")
    private boolean isOnline;
}
