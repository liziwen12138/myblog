package com.lzw.service;

import com.github.pagehelper.PageInfo;
import com.lzw.entity.Blog;
import com.lzw.entity.Tag;

import java.util.List;
import java.util.Map;

public interface BlogService {

    public Blog getBlogById(Long id);

    public List<Blog> getAllBlog();

    public void saveBlog(Blog blog);

    public void update(Blog blog);

    public void delete(Long id);

    public List<Blog> searchBlog(Blog blog);

    public List<Blog> getBlogByTypeId(Long id);
    public List<Blog> getBlogByTagId(Long id);
//    获得前台首页博客信息
    public List<Blog> getIndexBlog();
//    获得前台最新推荐
    public List<Blog> getRecommendBlog();

    public List<Blog> searchIndexBlog(String query);

    public Blog getDetailedBlog(Long id);

    public Map<String,List<Blog>> archiveBlog();

    public int blogCount();
}
