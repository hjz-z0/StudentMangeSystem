package com.zte.sms.service;

import java.util.List;

import com.zte.sms.entity.Grade;
import com.zte.sms.entity.vo.GradeVO;
import com.zte.sms.exception.GradeNameExistException;

public interface GradeService {

	public List<Grade> findAll();


    List<Grade> findGradeByCondition(GradeVO queryVO);

    Grade findByGname(String gname) throws GradeNameExistException;

    void addGrade(Grade grade);

    Grade findById(int gid);

    void modifyGrade(Grade grade);

    void removeById(int gid);

    void kaiqiGrade(Grade grade);
}
