package com.zte.sms.dao;

import java.util.List;

import com.zte.sms.entity.Course;

public interface CourseDAO {

	List<Course> selectAll();

	Integer selectIdByName(String stringCellValue);

    Course selectByCname(String cname);

	void insertCourse(Course course);

    Course selectById(int cid);

    void updateCourse(Course course);

    void deleteCourse(int cid);
}
