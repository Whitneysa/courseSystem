package com.course.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author YangHaixiong
 * @date 2023/12/23 17:26
 */

@Data
@ApiModel
public class LoginDTO {

    @ApiModelProperty
    @NotNull
    private String username;

    @ApiModelProperty
    @NotNull
    private String password;
}
