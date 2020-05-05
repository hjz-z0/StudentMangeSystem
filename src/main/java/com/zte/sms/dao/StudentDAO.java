package com.zte.sms.dao;

import java.util.List;

import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.StudentVO;

public interface StudentDAO {
	
	public List<Student> selectStudentsByPage();

	public Student selectByUsername(String username);

	public void insertStudent(Student student);

	public Student selectById(int sid);

	public void updateStudent(Student student);

	public void deleteStudent(int sid);

	public List<Student> selectStudentsByCondition(StudentVO queryVO);

	Student selectByName(String username);

    void updateMyInfo(Student student);
}
