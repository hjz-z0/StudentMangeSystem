package com.zte.sms.service;

import java.io.File;
import java.util.List;

import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.StudentVO;
import com.zte.sms.exception.StudentImportException;
import com.zte.sms.exception.StudentUsernameExistException;

public interface StudentService {
	
	public List<Student> findStudentByPage();

	public Student findByUsername(String username)throws StudentUsernameExistException;

	public void addStudent(Student student);

	public Student findById(int sid);

	public void modifyStudent(Student student);

	public void removeById(int sid);

	public void importExcel(String fileName, File file) throws StudentImportException;

	public List<Student> findStudentByCondition(StudentVO queryVO);

	Student findByName(String username);

	void modifyMyInfo(Student student);
}
