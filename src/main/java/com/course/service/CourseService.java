package com.course.service;

import com.course.pojo.Course;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author YangHaixiong
 * @date 2023/12/23 15:17
 */

public interface CourseService {
    void addCourse(Course course);

    void updateCourse(Course course);

    Course queryCourse(Long courseID);

    List<Course> queryAllCourse();

    void export() throws FileNotFoundException;

    void selectCourse(Long courseId);
}
