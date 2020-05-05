package com.zte.sms.dao;

import com.zte.sms.entity.Blog;

import java.util.List;

public interface BlogDAO {

    List<Blog> selectBlogByPage();

    void deleteBlog(int bid);

    Blog selectById(int bid);

    void updateBlog(Blog blog);

    void insertBlog(Blog blog);

    List<Blog> selectBlogBySid(int sid);
}
