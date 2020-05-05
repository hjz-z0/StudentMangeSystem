package com.zte.sms.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zte.sms.dao.CourseDAO;
import com.zte.sms.dao.GradeDAO;
import com.zte.sms.dao.StudentDAO;
import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.StudentVO;
import com.zte.sms.exception.StudentImportException;
import com.zte.sms.exception.StudentUsernameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.StudentService;
import com.zte.sms.util.MD5;

public class StudentServiceImpl implements StudentService{

	@Override
	public List<Student> findStudentByPage() {
		// TODO Auto-generated method stub
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		List<Student> students=studentDAO.selectStudentsByPage();
		return students;
	}

	@Override
	public Student findByUsername(String username) throws StudentUsernameExistException {
		// TODO Auto-generated method stub
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		Student student=studentDAO.selectByUsername(username);
		if(student!=null){
			throw new StudentUsernameExistException("用户名("+username+")已经被占用");
		}
		return student;
	}

	@Override
	public void addStudent(Student student) {
		// TODO Auto-generated method stub
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		studentDAO.insertStudent(student);
	}

	@Override
	public Student findById(int sid) {
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		Student student=studentDAO.selectById(sid);
		return student;
	}

	@Override
	public void modifyStudent(Student student) {
		// TODO Auto-generated method stub
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		studentDAO.updateStudent(student);
	}

	@Override
	public void removeById(int sid) {
		// TODO Auto-generated method stub
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		studentDAO.deleteStudent(sid);
		
	}

	@Override
	public void importExcel(String fileName, File file) throws StudentImportException {
		// TODO Auto-generated method stub
		GradeDAO  gradeDAO=(GradeDAO)ObjectFactory.getObject("gradeDAO");
		CourseDAO  courseDAO=(CourseDAO)ObjectFactory.getObject("courseDAO");
		StudentDAO  studentDAO=(StudentDAO)ObjectFactory.getObject("studentDAO");
		try {
			//判断是否是excel文档
			if(fileName.matches("^.+\\.((?i)(xls)|(xlsx))$")){
				//1:读取工作簿
				Workbook workbook=null;
				if(fileName.matches("^.+\\.((?i)(xls))$")){
					workbook = new HSSFWorkbook(new FileInputStream(file));//03,后缀：xls
				}
				else{
					//07以上
					workbook = new XSSFWorkbook(new FileInputStream(file));//07+,后缀：xlsx
				}
				//2:读取工作表
				Sheet sheet = workbook.getSheetAt(0);
				//3:读取行
				if(sheet.getPhysicalNumberOfRows()>2){
					Student student=null;
					for(int i=2;i<sheet.getPhysicalNumberOfRows();i++){
						Row row = sheet.getRow(i);
						student = new Student();
						//读取单元格
						String username=row.getCell(0).getStringCellValue();
						student.setUsername(username);
						//密码默认123
						student.setPassword(MD5.MD5Encode("123"));
						student.setName(row.getCell(1).getStringCellValue());
						student.setGender("男".equals(row.getCell(2).getStringCellValue())?0:1);
						student.setAge((int)row.getCell(3).getNumericCellValue());
						student.setGid(gradeDAO.selectIdByName(row.getCell(4).getStringCellValue()));
						student.setCid(courseDAO.selectIdByName(row.getCell(5).getStringCellValue()));
						studentDAO.insertStudent(student);
						
					}
				}
				
				//关闭工作表
				workbook.close();
				
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new StudentImportException("导入失败："+e.getMessage());
			
		}
		
	}

	@Override
	public List<Student> findStudentByCondition(StudentVO queryVO) {
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		List<Student> students=studentDAO.selectStudentsByCondition(queryVO);
		return students;
	}

	@Override
	public Student findByName(String username) {
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		Student student=studentDAO.selectByName(username);
		return student;
	}

	@Override
	public void modifyMyInfo(Student student) {
		StudentDAO studentDAO  = (StudentDAO)ObjectFactory.getObject("studentDAO");
		studentDAO.updateMyInfo(student);
	}

}
