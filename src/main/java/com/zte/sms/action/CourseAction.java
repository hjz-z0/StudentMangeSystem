package com.zte.sms.action;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zte.sms.constant.Constant;
import com.zte.sms.entity.Course;
import com.zte.sms.entity.Grade;
import com.zte.sms.entity.vo.PageInfo;
import com.zte.sms.exception.CourseNameExistException;
import com.zte.sms.exception.GradeNameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.CourseService;
import com.zte.sms.service.GradeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseAction {
    public String toCourseManager(HttpServletRequest req, HttpServletResponse resp) {
        // 获取页面中的下拉列表值
        CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
        List<Course> courseList = courseProxy.findAll();
        req.setAttribute("courseList", courseList);
        // 显示添加学生页面
        return "toCourseManager";
    }

    public void findCoursesByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取记录
        CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
//        StudentService studentProxy = (StudentService) ObjectFactory.getObject("studentProxy");
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
        List<Course> courses = courseProxy.findAll();
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
//        List<Student> students = studentProxy.findStudentByPage();
//        PageInfo<Student> pageInfo = new PageInfo<Student>(students);
        resp.setContentType(Constant.CONTENT_TYPE);
        resp.getWriter().print(JSON.toJSON(pageInfo));
    }

    // 校验课程名称是否已经存在
    public void findByCname(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType(Constant.CONTENT_TYPE);
        String cname = req.getParameter("cname");
        CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Course course = courseProxy.findByCname(cname);
            map.put("valid", true);// 设置valid属性，在false时，输出message所对应的值
        } catch (CourseNameExistException e) {
            // TODO: handle exception
            map.put("valid", false);
            map.put("message", e.getMessage());
        }
        // 返回2个值：message,是否输出输出该消息：valid
        resp.getWriter().print(JSON.toJSON(map));
    }

    // 新增课程
    public String addCourse(HttpServletRequest req, HttpServletResponse resp) {

        // 获取表单中提交过来的值
        String cname = req.getParameter("cname");
        String cdesc = req.getParameter("cdesc");
        // 调用proxy
        CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
        Course course = new Course(null,cname,cdesc,1);
        courseProxy.addCourse(course);
        req.setAttribute("msg", "添加课程成功");
        // 返回班级管理主页面
        return "toCourseManager";
    }

    // 显示修改课程页面
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(Constant.CONTENT_TYPE);
        // 调用proxy
        CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
        int cid = Integer.parseInt(req.getParameter("cid"));
        Course course = courseProxy.findById(cid);
        resp.getWriter().print(JSON.toJSON(course));

    }

    // 修改课程
    public String modifyCourse(HttpServletRequest req, HttpServletResponse resp) {

        // 获取表单中提交过来的值
        String cid = req.getParameter("cid");
        String cname = req.getParameter("cname");
        String cdesc = req.getParameter("cdesc");
        String state = req.getParameter("state");
        // 调用proxy
        CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
        Course course = new Course(Integer.parseInt(cid),cname,cdesc,Integer.parseInt(state));
        courseProxy.modifyCourse(course);
        req.setAttribute("msg", "修改课程成功");
        return "toCourseManager";
    }

    // 删除课程
    public String deleteCourse(HttpServletRequest req, HttpServletResponse resp) {
        int cid = Integer.parseInt(req.getParameter("cid"));
        CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
        try {
            courseProxy.removeById(cid);
            req.setAttribute("msg", "删除课程成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            req.setAttribute("msg", "删除失败");
        }
        // 返回学员列表页面
        return "toCourseManager";
    }
}
