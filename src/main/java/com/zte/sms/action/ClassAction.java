package com.zte.sms.action;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zte.sms.constant.Constant;
import com.zte.sms.entity.Grade;
import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.GradeVO;
import com.zte.sms.entity.vo.PageInfo;
import com.zte.sms.exception.GradeNameExistException;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.GradeService;
import com.zte.sms.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassAction {

    public String toGradeManager(HttpServletRequest req, HttpServletResponse resp) {
        // 获取页面中的下拉列表值
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
//        CourseService courseProxy = (CourseService) ObjectFactory.getObject("courseProxy");
        List<Grade> gradeList = gradeProxy.findAll();
//        List<Course> courseList = courseProxy.findAll();
        req.setAttribute("gradeList", gradeList);
        // 显示添加学生页面
        return "toGraderManager";
    }

    // 校验班级名称是否已经存在
    public void findByGname(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType(Constant.CONTENT_TYPE);
        String gname = req.getParameter("gname");
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Grade grade = gradeProxy.findByGname(gname);
            map.put("valid", true);// 设置valid属性，在false时，输出message所对应的值
        } catch (GradeNameExistException e) {
            // TODO: handle exception
            map.put("valid", false);
            map.put("message", e.getMessage());
        }
        // 返回2个值：message,是否输出输出该消息：valid
        resp.getWriter().print(JSON.toJSON(map));
    }


    public void findGradesByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取记录
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");

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
        List<Grade> grades = gradeProxy.findAll();
        PageInfo<Grade> pageInfo = new PageInfo<>(grades);

//        List<Student> students = studentProxy.findStudentByPage();
//        PageInfo<Student> pageInfo = new PageInfo<Student>(students);
        resp.setContentType(Constant.CONTENT_TYPE);
        resp.getWriter().print(JSON.toJSON(pageInfo));
    }

    public void findByCondition(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取记录
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
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
        String gname = req.getParameter("gname");

        GradeVO gradeVO = new GradeVO();
        if (!"".equals(gname)){
            gradeVO.setGname("%"+gname+"%");
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Grade> grades=null;
        if ("".equals(gname)){
            grades = gradeProxy.findAll();
        }else {
            grades = gradeProxy.findGradeByCondition(gradeVO);
        }
        PageInfo<Grade> pageInfo = new PageInfo<>(grades);
        resp.setContentType(Constant.CONTENT_TYPE);
        resp.getWriter().print(JSON.toJSON(pageInfo));
    }


    // 新增班级
    public String addGrade(HttpServletRequest req, HttpServletResponse resp) {

        // 获取表单中提交过来的值
        String gname = req.getParameter("gname");
        String gdesc = req.getParameter("gdesc");
        // 调用proxy
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
        Grade grade = new Grade(null,gname,gdesc,0);
        gradeProxy.addGrade(grade);
        req.setAttribute("msg", "添加班级成功");
        // 返回班级管理主页面
        return "toGradeManager";
    }


    // 显示修改班级页面
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(Constant.CONTENT_TYPE);
        // 调用proxy
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
        int gid = Integer.parseInt(req.getParameter("gid"));
        Grade grade = gradeProxy.findById(gid);
        resp.getWriter().print(JSON.toJSON(grade));

    }

    // 修改班级
    public String modifyGrade(HttpServletRequest req, HttpServletResponse resp) {

        // 获取表单中提交过来的值
        String gid = req.getParameter("gid");
        String gname = req.getParameter("gname");
        String gdesc = req.getParameter("gdesc");
        String state = req.getParameter("state");
        // 调用proxy
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
        Grade grade = new Grade(Integer.valueOf(gid),gname,gdesc,Integer.valueOf(state));
        gradeProxy.modifyGrade(grade);
        req.setAttribute("msg", "修改班级成功");
        return "toGradeManager";
    }

    // 删除班级
    public String deleteGrade(HttpServletRequest req, HttpServletResponse resp) {
        int gid = Integer.parseInt(req.getParameter("gid"));
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
        try {
            gradeProxy.removeById(gid);
            req.setAttribute("msg", "删除班级成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            req.setAttribute("msg", "删除失败");
        }
        // 返回学员列表页面
        return "toGradeManager";
    }

    // 开启班级
    public String kaiqiGrade(HttpServletRequest req, HttpServletResponse resp) {
        int gid = Integer.parseInt(req.getParameter("gid"));
        GradeService gradeProxy = (GradeService) ObjectFactory.getObject("gradeProxy");
        Grade grade = gradeProxy.findById(gid);
        int state = grade.getState();
        if (state == 1){
            req.setAttribute("msg", "班级已开启，无需重复开启");
        }else if (state == 0){
            grade.setState(1);
            gradeProxy.kaiqiGrade(grade);
            req.setAttribute("msg", "班级开启成功");
        }else {
            req.setAttribute("msg","操作错误！");
        }
        // 返回学员列表页面
        return "toGradeManager";
    }

}
