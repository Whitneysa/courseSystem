package com.course.controller;

import com.course.common.annotate.Permission;
import com.course.common.response.RespResult;
import com.course.common.response.Result;
import com.course.pojo.Course;
import com.course.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author YangHaixiong
 * @date 2023/12/23 15:16
 */

@RestController
@Api("课程管理")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Permission("admin")
    @ApiOperation("发布课程")
    @PostMapping("/course/add")
    public Result<Long> addCourse(@RequestHeader(value = "token",required = false) String token, @RequestBody @Valid Course course) {
        courseService.addCourse(course);
        return RespResult.success("发布课程成功");
    }

    @Permission("admin")
    @ApiOperation("修改课程")
    @PostMapping("/course/update")
    public Result<Long> updateCourse(@RequestHeader(value = "token",required = false) String token, @RequestBody @Valid Course course) {
        courseService.updateCourse(course);
        return RespResult.success("修改课程成功");
    }

    @ApiOperation("查询课程")
    @GetMapping("/course/query")
    public Result<Course> queryCourse(Long courseID) {
        Course course = courseService.queryCourse(courseID);
        return RespResult.success(course);
    }

    @ApiOperation("查询所有课程")
    @GetMapping("/course/queryAll")
    public Result<List<Course>> queryAllCourse() {
        List<Course> courseList = courseService.queryAllCourse();
        return RespResult.success(courseList);
    }

    @Permission("student")
    @ApiOperation("导出课表")
    @PostMapping("/course/export")
    public Result<String> export(@RequestHeader(value = "token",required = false) String token) throws FileNotFoundException {
        courseService.export();
        return RespResult.success("导出成功");
    }

    @Permission("student")
    @ApiOperation("选课")
    @GetMapping("/course/selectCourse")
    public Result<Long> selectCourse(@RequestHeader(value = "token",required = false) String token, Long courseId) {
        courseService.selectCourse(courseId);
        return RespResult.success("选课成功");
    }
}
