package com.zte.sms.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.zte.sms.entity.Student;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.StudentService;

public class POITest {
	
	public static void main(String[] args) throws FileNotFoundException {
		StudentService studentProxy=(StudentService)ObjectFactory.getObject("studentProxy");
		List<Student> students = studentProxy.findStudentByPage();
		/*for (Student student : students) {
			System.out.println(student);
		}*/
		ExcelUtil.exportStudent(students,new FileOutputStream(new File("d:/a.xls")));
	}

}
