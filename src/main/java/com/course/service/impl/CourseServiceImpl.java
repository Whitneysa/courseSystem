package com.course.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.course.mapper.CourseMapper;
import com.course.mapper.EnrollmentsMapper;
import com.course.pojo.Course;
import com.course.pojo.Enrollments;
import com.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author YangHaixiong
 * @date 2023/12/23 15:18
 */

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private EnrollmentsMapper enrollmentsMapper;

    @Override
    public void addCourse(Course course) {
        course.setCourseId(System.currentTimeMillis());
        courseMapper.inert(course);
    }

    @Override
    public void updateCourse(Course course) {
        courseMapper.update(course);
    }

    @Override
    public Course queryCourse(Long courseID) {
        return courseMapper.queryById(courseID);
    }

    @Override
    public List<Course> queryAllCourse() {
        return courseMapper.queryAllList();
    }

    @Override
    public void export() throws FileNotFoundException {
        // 通过代码获取
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            throw new RuntimeException("系统异常");
        }

        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");

        if (Objects.isNull(token)) {
            throw new RuntimeException("请在swagger的token中输入用户的id");
        }

        Long id = Long.valueOf(token);

        // 导出的位子，可以自己改
        String path = "D://course.xls";
        List<Course> courses = courseMapper.querySelectCourse(id);
        ExcelWriter writer = ExcelUtil.getWriter(false);
        writer.write(courses, true);

        writer.flush(new FileOutputStream("course.xls"));
    }

    /**
     * 事物保证选课的一致性
     * synchronized保证并发环境下不会超选
     */
    @Override
    @Transactional
    public  void selectCourse(Long courseId) {
        Course course = courseMapper.queryById(courseId);

        synchronized (course.getCourseId()) {
            if (course.getCourseCapacity() > 0) {
                course.setCourseCapacity(course.getCourseCapacity() - 1);

                Enrollments enrollments = new Enrollments();
                enrollments.setId(System.currentTimeMillis());
                // 通过代码获取
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (Objects.isNull(servletRequestAttributes)) {
                    throw new RuntimeException("系统异常");
                }

                HttpServletRequest request = servletRequestAttributes.getRequest();
                String token = request.getHeader("token");

                if (Objects.isNull(token)) {
                    throw new RuntimeException("请在swagger的token中输入用户的id");
                }
                enrollments.setStudentId(Long.valueOf(token));
                enrollments.setCourseId(courseId);
                enrollmentsMapper.inert(enrollments);
                courseMapper.update(course);
            }
        }
    }
}
