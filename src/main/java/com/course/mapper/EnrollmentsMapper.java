package com.course.mapper;

import com.course.pojo.Enrollments;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author YangHaixiong
 * @date 2023/12/23 20:31
 */

@Mapper
public interface EnrollmentsMapper {
    @Insert("insert into enrollments(id, student_id, course_id) values(#{id}, #{studentId}, #{courseId})")
    void inert(Enrollments enrollments);
}
