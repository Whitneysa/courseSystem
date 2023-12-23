package com.course.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author YangHaixiong
 * @date 2023/12/23 15:57
 */
@Data
@ApiModel("课程")
public class Course {

    private Long courseId;

    @ApiModelProperty("课程名")
    @NotNull
    private String courseName;

    @ApiModelProperty("课程描述")
    @NotNull
    private String courseDescription;

    @ApiModelProperty("授课老师")
    @NotNull
    private String courseTeacher;

    @ApiModelProperty("最多报名人数")
    @NotNull
    private Integer courseCapacity;

    @ApiModelProperty("授课时间")
    @NotNull
    private String courseTime;
}
