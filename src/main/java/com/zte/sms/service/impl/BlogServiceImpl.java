package com.zte.sms.service.impl;

import com.zte.sms.dao.BlogDAO;
import com.zte.sms.dao.CourseDAO;
import com.zte.sms.entity.Blog;
import com.zte.sms.factory.ObjectFactory;
import com.zte.sms.service.BlogService;

import java.util.List;

public class BlogServiceImpl implements BlogService {
    @Override
    public List<Blog> findBlogByPage() {
        BlogDAO blogDao  = (BlogDAO) ObjectFactory.getObject("blogDAO");
        List<Blog> blogList=blogDao.selectBlogByPage();
        return blogList;
    }

    @Override
    public void removeById(int bid) {
        BlogDAO blogDao  = (BlogDAO) ObjectFactory.getObject("blogDAO");
        blogDao.deleteBlog(bid);
    }

    @Override
    public Blog findById(int bid) {
        BlogDAO blogDao  = (BlogDAO) ObjectFactory.getObject("blogDAO");
        Blog blog = blogDao.selectById(bid);
        return blog;
    }

    @Override
    public void modifyBlog(Blog blog) {
        BlogDAO blogDao  = (BlogDAO) ObjectFactory.getObject("blogDAO");
        blogDao.updateBlog(blog);
    }

    @Override
    public void addBlog(Blog blog) {
        BlogDAO blogDao  = (BlogDAO) ObjectFactory.getObject("blogDAO");
        blogDao.insertBlog(blog);
    }

    @Override
    public List<Blog> findBlogBySid(int sid) {
        BlogDAO blogDao  = (BlogDAO) ObjectFactory.getObject("blogDAO");
        List<Blog> blogList=blogDao.selectBlogBySid(sid);
        return blogList;
    }
}
