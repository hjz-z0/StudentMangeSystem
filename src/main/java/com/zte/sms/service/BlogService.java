package com.zte.sms.service;

import com.zte.sms.entity.Blog;

import java.util.List;

public interface BlogService {

     List<Blog> findBlogByPage();

    void removeById(int bid);

    Blog findById(int bid);

    void modifyBlog(Blog blog);

    void addBlog(Blog blog);

    List<Blog> findBlogBySid(int sid);
}
