package com.zte.sms.service.proxy;

import java.util.List;

import com.zte.sms.entity.Grade;
import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.GradeVO;
import com.zte.sms.exception.GradeNameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.GradeService;
import com.zte.sms.service.StudentService;
import com.zte.sms.transaction.TransactionManager;

public class GradeProxy implements GradeService {

	@Override
	public List<Grade> findAll() {
		// TODO Auto-generated method stub
		TransactionManager tran=(TransactionManager)ObjectFactory.getObject("transaction");
		GradeService gradeServcie = (GradeService)ObjectFactory.getObject("gradeService");
		List<Grade> grades=null;
		try {
			tran.beginTransaction();
			grades=gradeServcie.findAll();
			tran.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
		}
		return grades;
	}

	@Override
	public List<Grade> findGradeByCondition(GradeVO queryVO) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		GradeService gradeServcie = (GradeService)ObjectFactory.getObject("gradeService");
		List<Grade> grades=null;
		try {
			tran.beginTransaction();
			grades=gradeServcie.findGradeByCondition(queryVO);
			tran.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
		}
		return grades;
	}

	@Override
	public Grade findByGname(String gname) throws GradeNameExistException {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		GradeService gradeServcie = (GradeService)ObjectFactory.getObject("gradeService");
		Grade grade=null;
		try {
			tran.beginTransaction();
			grade=gradeServcie.findByGname(gname);
			tran.commit();
		} catch (GradeNameExistException e) {
			// TODO: handle exception
			throw e;
		}
		return grade;
	}

	@Override
	public void addGrade(Grade grade) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		GradeService gradeServcie = (GradeService)ObjectFactory.getObject("gradeService");
		try {
			tran.beginTransaction();
			gradeServcie.addGrade(grade);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
	}

	@Override
	public Grade findById(int gid) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		GradeService gradeServcie = (GradeService)ObjectFactory.getObject("gradeService");
		Grade grade=null;
		try {
			tran.beginTransaction();
			grade=gradeServcie.findById(gid);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
		return grade;
	}

	@Override
	public void modifyGrade(Grade grade) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		GradeService gradeServcie = (GradeService)ObjectFactory.getObject("gradeService");
		try {
			tran.beginTransaction();
			gradeServcie.modifyGrade(grade);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
	}

	@Override
	public void removeById(int gid) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		GradeService gradeServcie = (GradeService)ObjectFactory.getObject("gradeService");
		try {
			tran.beginTransaction();
			gradeServcie.removeById(gid);
			tran.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			tran.rollback();
			//需要向外部抛出异常，将异常交给action层处理
			throw new RuntimeException("删除失败");
		}
	}

	@Override
	public void kaiqiGrade(Grade grade) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		GradeService gradeServcie = (GradeService)ObjectFactory.getObject("gradeService");
		try {
			tran.beginTransaction();
			gradeServcie.kaiqiGrade(grade);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
	}

}
