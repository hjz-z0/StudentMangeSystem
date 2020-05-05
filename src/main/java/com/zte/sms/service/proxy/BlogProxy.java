package com.zte.sms.service.proxy;

import com.zte.sms.entity.Blog;
import com.zte.sms.entity.Student;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.BlogService;
import com.zte.sms.service.CourseService;
import com.zte.sms.service.StudentService;
import com.zte.sms.transaction.TransactionManager;

import java.util.List;

public class BlogProxy implements BlogService {
    @Override
    public List<Blog> findBlogByPage() {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("transaction");
        BlogService blogService = (BlogService)ObjectFactory.getObject("blogService");
        List<Blog> blogList=null;
        try {
            tran.beginTransaction();
            blogList=blogService.findBlogByPage();
            tran.commit();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            tran.rollback();
        }
        return blogList;
    }

    @Override
    public void removeById(int bid) {
        TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
        BlogService blogService = (BlogService)ObjectFactory.getObject("blogService");
        try {
            tran.beginTransaction();
            blogService.removeById(bid);
            tran.commit();
        } catch (Exception e) {
//			e.printStackTrace();
            tran.rollback();
            //需要向外部抛出异常，将异常交给action层处理
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public Blog findById(int bid) {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("transaction");
        BlogService blogService = (BlogService)ObjectFactory.getObject("blogService");
       Blog blog=null;
        try {
            tran.beginTransaction();
            blog=blogService.findById(bid);
            tran.commit();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            tran.rollback();
        }
        return blog;
    }

    @Override
    public void modifyBlog(Blog blog) {
        TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
        BlogService blogService = (BlogService)ObjectFactory.getObject("blogService");
        try {
            tran.beginTransaction();
            blogService.modifyBlog(blog);
            tran.commit();
        } catch (Exception e) {
			e.printStackTrace();
            tran.rollback();
        }
    }

    @Override
    public void addBlog(Blog blog) {
        TransactionManager tran = (TransactionManager)ObjectFactory.getObject("transaction");
        BlogService blogService = (BlogService)ObjectFactory.getObject("blogService");
        try {
            tran.beginTransaction();
            blogService.addBlog(blog);
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tran.rollback();
        }
    }

    @Override
    public List<Blog> findBlogBySid(int sid) {
        TransactionManager tran = (TransactionManager) ObjectFactory.getObject("transaction");
        BlogService blogService = (BlogService)ObjectFactory.getObject("blogService");
        List<Blog> blogList=null;
        try {
            tran.beginTransaction();
            blogList=blogService.findBlogBySid(sid);
            tran.commit();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            tran.rollback();
        }
        return blogList;
    }
}
