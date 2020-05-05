package com.zte.sms.service.proxy;

import java.io.File;
import java.util.List;

import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.StudentVO;
import com.zte.sms.exception.StudentImportException;
import com.zte.sms.exception.StudentUsernameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.StudentService;
import com.zte.sms.transaction.TransactionManager;

public class StudentProxy implements StudentService{

	@Override
	public List<Student> findStudentByPage() {
		// TODO Auto-generated method stub
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		List<Student> students=null;
		try {
			tran.beginTransaction();
			students=studentService.findStudentByPage();
			tran.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
		}
		return students;
	}

	@Override
	public Student findByUsername(String username) throws StudentUsernameExistException {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		Student student=null;
		try {
			tran.beginTransaction();
			student=studentService.findByUsername(username);
			tran.commit();
		} catch (StudentUsernameExistException e) {
			// TODO: handle exception
			throw e;
		}
		return student;
	}

	@Override
	public void addStudent(Student student) {
		// TODO Auto-generated method stub
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		try {
			tran.beginTransaction();
		    studentService.addStudent(student);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
		
		
	}

	@Override
	public Student findById(int sid) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		Student student=null;
		try {
			tran.beginTransaction();
			student=studentService.findById(sid);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
		return student;
	}

	@Override
	public void modifyStudent(Student student) {
		// TODO Auto-generated method stub
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		try {
			tran.beginTransaction();
		    studentService.modifyStudent(student);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
	}

	@Override
	public void removeById(int sid) {
		// TODO Auto-generated method stub
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		try {
			tran.beginTransaction();
		    studentService.removeById(sid);
			tran.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			tran.rollback();
			//需要向外部抛出异常，将异常交给action层处理
			throw new RuntimeException("删除失败");
		}
	}

	@Override
	public void importExcel(String fileName, File file) throws StudentImportException {
		// TODO Auto-generated method stub
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		try {
			tran.beginTransaction();
		    studentService.importExcel(fileName, file);
			tran.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			tran.rollback();
			//需要向外部抛出异常，将异常交给action层处理
			throw new RuntimeException("导入数据失败");
		}
	}

	@Override
	public List<Student> findStudentByCondition(StudentVO queryVO) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		List<Student> students=null;
		try {
			tran.beginTransaction();
			students=studentService.findStudentByCondition(queryVO);
			tran.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
		}
		return students;
	}

	@Override
	public Student findByName(String username) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		Student student=null;
		try {
			tran.beginTransaction();
			student=studentService.findByName(username);
			tran.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tran.rollback();
		}
		return student;
	}

	@Override
	public void modifyMyInfo(Student student) {
		TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
		StudentService studentService=(StudentService)ObjectFactory.getObject("studentService");
		try {
			tran.beginTransaction();
			studentService.modifyMyInfo(student);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		}
	}

}
