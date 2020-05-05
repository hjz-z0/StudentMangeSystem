package com.zte.sms.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zte.sms.constant.Constant;
import com.zte.sms.entity.Course;
import com.zte.sms.entity.Grade;
import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.PageInfo;
import com.zte.sms.entity.vo.StudentVO;
import com.zte.sms.exception.StudentImportException;
import com.zte.sms.exception.StudentUsernameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.CourseService;
import com.zte.sms.service.GradeService;
import com.zte.sms.service.StudentService;
import com.zte.sms.util.ExcelUtil;

public class StudentAction {

	public String findStudents(HttpServletRequest req, HttpServletResponse resp) {
		// 获取记录
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		List<Student> studnets = studentProxy.findStudentByPage();
		req.setAttribute("students", studnets);

		// 返回adminMain.jsp
		return "adminMain";
	}
	public String toModifyStudentInfo(HttpServletRequest req, HttpServletResponse resp) {
		String username = req.getParameter("username");
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		Student student = studentProxy.findByName(username);
		int sid = student.getSid();
		Student student1 = studentProxy.findById(sid);
		req.setAttribute("student",student1);
		return "modifyStudentInfo";
	}
	public String modifyMyInfo(HttpServletRequest req, HttpServletResponse resp){
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		String username = req.getParameter("username");
		String name = req.getParameter("name");
		String sex = req.getParameter("sex");
		String age = req.getParameter("age");
		int gender = 0;
		if ("female".equals(sex)){
			gender = 1;
		}else {
			gender = 0;
		}
		Student student = studentProxy.findByName(username);
		student.setName(name);
		student.setAge(Integer.parseInt(age));
		student.setGender(gender);
		studentProxy.modifyMyInfo(student);
		req.setAttribute("msg","更新个人信息成功！");
		return "successModify";

	}

	public void findStudentsByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 获取记录
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		String pageNoStr = req.getParameter("pageNo");
		String pageSizeStr = req.getParameter("pageSize");
		int pageNo = 0;
		int pageSize = 0;
		if (pageNoStr == null) {
			pageNo = Constant.PAGE_NO;
		} else {
			pageNo = Integer.parseInt(pageNoStr);
		}
		if (pageSizeStr == null) {
			pageSize = Constant.PAGE_SIZE;
		} else {
			pageSize = Integer.parseInt(pageSizeStr);
		}

		PageHelper.startPage(pageNo, pageSize);
		List<Student> students = studentProxy.findStudentByPage();
		PageInfo<Student> pageInfo = new PageInfo<Student>(students);
		resp.setContentType(Constant.CONTENT_TYPE);
		resp.getWriter().print(JSON.toJSON(pageInfo));
	}

	// 显示学生管理页面
	public String toStudentManager(HttpServletRequest req, HttpServletResponse resp) {
		// 获取页面中的下拉列表值
		GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
		CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
		List<Grade> gradeList = gradeProxy.findAll();
		List<Course> courseList = courseProxy.findAll();
		req.setAttribute("gradeList", gradeList);
		req.setAttribute("courseList", courseList);

		// 显示添加学生页面
		return "toStudentManager";
	}

	// 校验用户名是否已经存在
	public void findByUsername(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType(Constant.CONTENT_TYPE);
		String username = req.getParameter("username");
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Student student = studentProxy.findByUsername(username);
			map.put("valid", true);// 设置valid属性，在false时，输出message所对应的值
		} catch (StudentUsernameExistException e) {
			// TODO: handle exception
			map.put("valid", false);
			map.put("message", e.getMessage());
		}
		// 返回2个值：message,是否输出输出该消息：valid
		resp.getWriter().print(JSON.toJSON(map));
	}

	// 新增学员
	public String addStudent(HttpServletRequest req, HttpServletResponse resp) {

		// 获取表单中提交过来的值
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String name = req.getParameter("name");
		int age = Integer.parseInt(req.getParameter("age"));
		int gender = Integer.parseInt(req.getParameter("gender"));
		int gid = Integer.parseInt(req.getParameter("gid"));
		int cid = Integer.parseInt(req.getParameter("cid"));
		// 调用proxy
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		Student student = new Student(null, username, password, name, gender, age, gid, cid);
		studentProxy.addStudent(student);
		req.setAttribute("msg", "添加学生成功");
		// 返回学生管理主页面
		return "toStudentManager";
	}

	// 显示修改学员页面
	public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType(Constant.CONTENT_TYPE);
		// 调用proxy
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		int sid = Integer.parseInt(req.getParameter("sid"));
		Student student = studentProxy.findById(sid);
		resp.getWriter().print(JSON.toJSON(student));

	}

	// 修改学员
	public String modifyStudent(HttpServletRequest req, HttpServletResponse resp) {

		// 获取表单中提交过来的值
		String username = req.getParameter("gusername");
		String name = req.getParameter("name");
		int sid = Integer.parseInt(req.getParameter("sid"));
		int age = Integer.parseInt(req.getParameter("age"));
		int gender = Integer.parseInt(req.getParameter("gender"));
		int gid = Integer.parseInt(req.getParameter("gid"));
		int cid = Integer.parseInt(req.getParameter("cid"));
		// 调用proxy
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		Student student = new Student(sid, username, null, name, gender, age, gid, cid);
		studentProxy.modifyStudent(student);
		req.setAttribute("msg", "修改学生成功");
		// 返回学员列表页面
		return "toStudentManager";
	}

	// 删除学员
	public String deleteStudent(HttpServletRequest req, HttpServletResponse resp) {
		int sid = Integer.parseInt(req.getParameter("sid"));
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		try {
			studentProxy.removeById(sid);
			req.setAttribute("msg", "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("msg", "删除失败");
		}
		// 返回学员列表页面
		return "toStudentManager";
	}

	// 导出excel
	// 不需要返回值
	public void exportExcel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 设置响应类型
		resp.setContentType("application/x-excel");
		// 设置处理方式为附件
		resp.setHeader("content-disposition", "attachment;filename=students1.xls");
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		List<Student> students = studentProxy.findStudentByPage();
		// 将记录输出到对应的xls,通过浏览器下载
		ExcelUtil.exportStudent(students, resp.getOutputStream());

	}

	// 导出数据到数据库
	public String importExcel(HttpServletRequest req, HttpServletResponse resp) {
		String fileName = "student.xls";
		File file = new File("d:/students.xls");
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		try {
			studentProxy.importExcel(fileName, file);
			req.setAttribute("msg", "导入成功");
		} catch (StudentImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("msg", e.getMessage());
		}

		// 返回学员列表页面
		return "toStudentManager";

	}

	// 按条件查询
	public void findByCondition(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 获取记录
		StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
		String pageNoStr = req.getParameter("pageNo");
		String pageSizeStr = req.getParameter("pageSize");
		
		
		int pageNo = 0;
		int pageSize = 0;
		if (pageNoStr == null) {
			pageNo = Constant.PAGE_NO;
		} else {
			pageNo = Integer.parseInt(pageNoStr);
		}
		if (pageSizeStr == null) {
			pageSize = Constant.PAGE_SIZE;
		} else {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		String name = req.getParameter("name");
		String minAge = req.getParameter("minAge");
		String maxAge = req.getParameter("maxAge");
		String gender = req.getParameter("gender");
		String gid = req.getParameter("gid");
		String cid = req.getParameter("cid");
		
		//将上述值组装成VO
		StudentVO  queryVO = new StudentVO();
		if(!"".equals(name)){
			queryVO.setName("%"+name+"%");
		}
		if(!"".equals(minAge)){
			queryVO.setMinAge(Integer.parseInt(minAge));
		}
		if(!"".equals(maxAge)){
			queryVO.setMaxAge(Integer.parseInt(maxAge));
		}
		if(!"all".equals(gender)){
			queryVO.setGender(Integer.parseInt(gender));
		}
		if(!"all".equals(gid)){
			queryVO.setGid(Integer.parseInt(gid));
		}
		if(!"all".equals(cid)){
			queryVO.setCid(Integer.parseInt(cid));
		}
		
		
		
		
		
		

		PageHelper.startPage(pageNo, pageSize);
		List<Student> students=null;
		if("".equals(name)&&"".equals(minAge)&&"".equals(maxAge)&&"all".equals(gender)&&"all".equals(gid)&&"all".equals(cid)){
			students = studentProxy.findStudentByPage();
		}
		else{
			students = studentProxy.findStudentByCondition(queryVO);
		}
		PageInfo<Student> pageInfo = new PageInfo<Student>(students);
		resp.setContentType(Constant.CONTENT_TYPE);
		resp.getWriter().print(JSON.toJSON(pageInfo));
	}

}
