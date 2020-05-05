package com.zte.sms.service.impl;

import java.util.List;

import com.zte.sms.dao.CourseDAO;
import com.zte.sms.dao.GradeDAO;
import com.zte.sms.entity.Course;
import com.zte.sms.entity.Grade;
import com.zte.sms.exception.CourseNameExistException;
import com.zte.sms.exception.GradeNameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.CourseService;

public class CourseServiceImpl implements CourseService {

	@Override
	public List<Course> findAll() {
		// TODO Auto-generated method stub
		CourseDAO  courseDAO = (CourseDAO)ObjectFactory.getObject("courseDAO");
		List<Course> courses=courseDAO.selectAll();
		return courses;
	}

	@Override
	public Course findByCname(String cname) throws CourseNameExistException {
		CourseDAO courseDAO  = (CourseDAO) ObjectFactory.getObject("courseDAO");
		Course course = courseDAO.selectByCname(cname);
		if(course!=null){
			throw new CourseNameExistException("课程名("+cname+")已经被占用");
		}
		return course;
	}

	@Override
	public void addCourse(Course course) {
		CourseDAO courseDAO  = (CourseDAO) ObjectFactory.getObject("courseDAO");
		courseDAO.insertCourse(course);
	}

	@Override
	public Course findById(int cid) {
		CourseDAO courseDAO  = (CourseDAO) ObjectFactory.getObject("courseDAO");
		Course course=courseDAO.selectById(cid);
		return course;
	}

	@Override
	public void modifyCourse(Course course) {
		CourseDAO courseDAO  = (CourseDAO) ObjectFactory.getObject("courseDAO");
		courseDAO.updateCourse(course);
	}

	@Override
	public void removeById(int cid) {
		CourseDAO courseDAO  = (CourseDAO) ObjectFactory.getObject("courseDAO");
		courseDAO.deleteCourse(cid);
	}

}
