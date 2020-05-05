package com.zte.sms.service.proxy;

import java.util.List;

import com.zte.sms.entity.Course;
import com.zte.sms.entity.Grade;
import com.zte.sms.exception.CourseNameExistException;
import com.zte.sms.exception.GradeNameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.CourseService;
import com.zte.sms.service.GradeService;
import com.zte.sms.transaction.TransactionManager;

public class CourseProxy implements CourseService {

	@Override
	public List<Course> findAll() {
		TransactionManager tran=(TransactionManager)ObjectFactory.getObject("transaction");
		CourseService courseServcie = (CourseService)ObjectFactory.getObject("courseService");
		List<Course> courses=null;
		try {
			tran.beginTransaction();
			courses=courseServcie.findAll();
			tran.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
		}
		return courses;
	}

	@Override
	public Course findByCname(String cname) throws CourseNameExistException {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		CourseService courseServcie = (CourseService)ObjectFactory.getObject("courseService");
		Course course=null;
		try {
			tran.beginTransaction();
			course=courseServcie.findByCname(cname);
			tran.commit();
		} catch (CourseNameExistException e) {
			// TODO: handle exception
			throw e;
		}
		return course;
	}

	@Override
	public void addCourse(Course course) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		CourseService courseServcie = (CourseService)ObjectFactory.getObject("courseService");
		try {
			tran.beginTransaction();
			courseServcie.addCourse(course);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
	}

	@Override
	public Course findById(int cid) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		CourseService courseServcie = (CourseService)ObjectFactory.getObject("courseService");
		Course course=null;
		try {
			tran.beginTransaction();
			course=courseServcie.findById(cid);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
		return course;
	}

	@Override
	public void modifyCourse(Course course) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		CourseService courseServcie = (CourseService)ObjectFactory.getObject("courseService");
		try {
			tran.beginTransaction();
			courseServcie.modifyCourse(course);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
	}

	@Override
	public void removeById(int cid) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		CourseService courseServcie = (CourseService)ObjectFactory.getObject("courseService");
		try {
			tran.beginTransaction();
			courseServcie.removeById(cid);
			tran.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			tran.rollback();
			//需要向外部抛出异常，将异常交给action层处理
			throw new RuntimeException("删除失败");
		}
	}

}
