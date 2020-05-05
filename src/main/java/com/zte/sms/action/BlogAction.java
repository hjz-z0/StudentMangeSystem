package com.zte.sms.action;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zte.sms.constant.Constant;
import com.zte.sms.entity.Blog;
import com.zte.sms.entity.Student;
import com.zte.sms.entity.vo.PageInfo;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.BlogService;
import com.zte.sms.service.GradeService;
import com.zte.sms.service.StudentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BlogAction {
    public String toBlogManager(HttpServletRequest req, HttpServletResponse resp) {
        // 显示添加学生页面
        return "toBlogManager";
    }
    public String toMyBlog(HttpServletRequest req, HttpServletResponse resp) {
        // 显示添加学生页面
        return "myBlog";
    }
    public String toFindMyBlog(HttpServletRequest req, HttpServletResponse resp) {
        // 显示添加学生页面
        return "myStudentBlog";
    }
    public String toNewBlog(HttpServletRequest req, HttpServletResponse resp) {
        // 显示添加学生页面
        return "newBlog";
    }

    public String toModifyBlog(HttpServletRequest req, HttpServletResponse rep){
        int bid = Integer.parseInt(req.getParameter("bid"));
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd");
        String createDate =  dateFormat.format(new Date());
        BlogService blogProxy = (BlogService)ObjectFactory.getObject("blogProxy");
        Blog blog = blogProxy.findById(bid);
        if (title.trim() !=""|| content.trim() != "" ){
            blog.setTitle(title);
            blog.setContent(content);
            blog.setCreateDate(createDate);
            blogProxy.modifyBlog(blog);
            req.setAttribute("msg","日志更新成功！");
            return "modifyBlog";
        }else {
            req.setAttribute("msg","标题或者正文不能为空");
            return "fail";
        }




    }
    public String toBlogDetail(HttpServletRequest req, HttpServletResponse resp) {
        int bid = Integer.parseInt(req.getParameter("bid"));
        BlogService blogProxy = (BlogService)ObjectFactory.getObject("blogProxy");
        Blog blog = blogProxy.findById(bid);
        req.setAttribute("blog",blog);
        return "toBlogDetail";
    }

    public String toStudentBlogDetail(HttpServletRequest req, HttpServletResponse resp) {
        int bid = Integer.parseInt(req.getParameter("bid"));
        BlogService blogProxy = (BlogService)ObjectFactory.getObject("blogProxy");
        Blog blog = blogProxy.findById(bid);
        req.setAttribute("blog",blog);
        return "toStudentBlogDetail";
    }

    public String addStudentBlog(HttpServletRequest req, HttpServletResponse resp){
        String username = req.getParameter("username");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd");
        String createDate =  dateFormat.format(new Date());
        StudentService studentProxy = (StudentService)ObjectFactory.getObject("studentProxy");
        Student student = studentProxy.findByName(username);
        int sid = student.getSid();
        Blog blog = new Blog(null,title,content,createDate,sid);
        BlogService blogProxy = (BlogService)ObjectFactory.getObject("blogProxy");
        blogProxy.addBlog(blog);
        req.setAttribute("msg","发布日志成功");
        return "successAdd";
    }

    public void findBlogByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取记录
        BlogService blogProxy = (BlogService)ObjectFactory.getObject("blogProxy");
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
        List<Blog> blogList = blogProxy.findBlogByPage();
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogList);
        resp.setContentType(Constant.CONTENT_TYPE);
        resp.getWriter().print(JSON.toJSON(pageInfo));
    }
    public void findBlogByPageByName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 获取记录
        BlogService blogProxy = (BlogService)ObjectFactory.getObject("blogProxy");
        StudentService studentProxy = (StudentService)ObjectFactory.getObject("studentProxy");
        String username = req.getParameter("username");
        String pageNoStr = req.getParameter("pageNo");
        String pageSizeStr = req.getParameter("pageSize");
        Student student = studentProxy.findByName(username);
        int sid = student.getSid();

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
        List<Blog> blogList = blogProxy.findBlogBySid(sid);
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(blogList);
        resp.setContentType(Constant.CONTENT_TYPE);
        resp.getWriter().print(JSON.toJSON(pageInfo));
    }
    // 删除日志
    public String deleteBlog(HttpServletRequest req, HttpServletResponse resp) {
        int bid = Integer.parseInt(req.getParameter("bid"));
        BlogService blogProxy = (BlogService)ObjectFactory.getObject("blogProxy");
        try {
            blogProxy.removeById(bid);
            req.setAttribute("msg", "删除日志成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            req.setAttribute("msg", "删除失败");
        }
        return "toBlogManager";
    }

    // 删除日志
    public String deleteStudentBlog(HttpServletRequest req, HttpServletResponse resp) {
        int bid = Integer.parseInt(req.getParameter("bid"));
        BlogService blogProxy = (BlogService)ObjectFactory.getObject("blogProxy");
        try {
            blogProxy.removeById(bid);
            req.setAttribute("msg", "删除日志成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            req.setAttribute("msg", "删除失败");
        }
        return "toDeleteBlogManager";
    }
}
