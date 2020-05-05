package com.zte.sms.service;

import java.util.List;

import com.zte.sms.entity.Course;
import com.zte.sms.exception.CourseNameExistException;

public interface CourseService {

	public List<Course> findAll();

	Course findByCname(String cname) throws CourseNameExistException;

	void addCourse(Course course);

    Course findById(int cid);

	void modifyCourse(Course course);

	void removeById(int cid);
}
