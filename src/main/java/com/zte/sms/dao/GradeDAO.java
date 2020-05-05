package com.zte.sms.dao;

import java.util.List;

import com.zte.sms.entity.Grade;
import com.zte.sms.entity.vo.GradeVO;

public interface GradeDAO {

	public List<Grade> selectAll();

	public Integer selectIdByName(String stringCellValue);

    List<Grade> selectGradesByCondition(GradeVO queryVO);

	Grade selectByGname(String gname);

    void insertGrade(Grade grade);

    Grade selectById(int gid);

    void updateGrade(Grade grade);

    void deleteGrade(int gid);

    void updateGradeByState(Grade grade);
}
