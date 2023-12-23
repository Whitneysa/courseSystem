package com.course.mapper;

import com.course.pojo.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author YangHaixiong
 * @date 2023/12/23 18:09
 */

@Mapper
public interface CourseMapper {
    @Insert("insert into course(course_id, course_name, course_description, course_teacher, course_capacity, course_time)" +
            "values(#{courseId}, #{courseName}, #{courseDescription}, #{courseTeacher}, #{courseCapacity}, #{courseTime})")
    void inert(Course course);

    @Update("update course set course_name =#{courseName}, course_description = #{courseDescription}, course_teacher = #{courseTeacher}," +
            "course_capacity = #{courseCapacity}, course_time = #{courseTime}")
    void update(Course course);

    @Select("select * from course where course_id = #{courseId}")
    Course queryById(@Param("courseId") Long courseId);

    @Select("select * from course")
    List<Course> queryAllList();

    @Select("select course.* " +
            "from enrollments left join course on enrollments.course_id = course.course_id " +
            "where student_id = #{id}")
    List<Course> querySelectCourse(@Param("id") Long id);
}
