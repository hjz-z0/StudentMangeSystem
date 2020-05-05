package com.zte.sms.service.impl;

import java.util.List;

import com.zte.sms.dao.GradeDAO;
import com.zte.sms.dao.StudentDAO;
import com.zte.sms.entity.Grade;
import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.GradeVO;
import com.zte.sms.exception.GradeNameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.GradeService;

public class GradeServiceImpl implements GradeService {

	@Override
	public List<Grade> findAll() {
		// TODO Auto-generated method stub
		GradeDAO  gradeDAO= (GradeDAO)ObjectFactory.getObject("gradeDAO");
		List<Grade> grades=gradeDAO.selectAll();
		return grades;
	}

	@Override
	public List<Grade> findGradeByCondition(GradeVO queryVO) {
		GradeDAO  gradeDAO= (GradeDAO)ObjectFactory.getObject("gradeDAO");
		List<Grade> grades = gradeDAO.selectGradesByCondition(queryVO);
		return grades;
	}

	@Override
	public Grade findByGname(String gname) throws GradeNameExistException {
		GradeDAO gradeDAO  = (GradeDAO) ObjectFactory.getObject("gradeDAO");
		Grade grade = gradeDAO.selectByGname(gname);
		if(grade!=null){
			throw new GradeNameExistException("班级名称("+gname+")已经被占用");
		}
		return grade;
	}

	@Override
	public void addGrade(Grade grade) {
		GradeDAO gradeDAO  = (GradeDAO) ObjectFactory.getObject("gradeDAO");
		gradeDAO.insertGrade(grade);
	}

	@Override
	public Grade findById(int gid) {
		GradeDAO gradeDAO  = (GradeDAO) ObjectFactory.getObject("gradeDAO");
		Grade grade=gradeDAO.selectById(gid);
		return grade;
	}

	@Override
	public void modifyGrade(Grade grade) {
		GradeDAO gradeDAO  = (GradeDAO) ObjectFactory.getObject("gradeDAO");
		gradeDAO.updateGrade(grade);
	}

	@Override
	public void removeById(int gid) {
		GradeDAO gradeDAO  = (GradeDAO) ObjectFactory.getObject("gradeDAO");
		gradeDAO.deleteGrade(gid);
	}

	@Override
	public void kaiqiGrade(Grade grade) {
		GradeDAO gradeDAO  = (GradeDAO) ObjectFactory.getObject("gradeDAO");
		gradeDAO.updateGradeByState(grade);
	}

}
