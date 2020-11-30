package com.lzw.dao;

import com.lzw.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface BlogMapper {

    public Blog getBlogById(@Param("id")Long id);

    public void saveBlog(Blog blog);

    public List<Blog> getAllBlog();

    public void update(Blog blog);

    public void delete(Long id);

    public List<Blog> searchBlog(Blog blog);

    public List<Blog> getBlogByTypeId(@Param("typeId")Long typeId);
    public List<Blog> getBlogByTagId(@Param("tagId")Long tagId);
    public List<Blog> getIndexBlog();

    public List<Blog> getRecommendBlog();

    public List<Blog> searchIndexBlog(String query);

    public Blog getDetailedBlog(@Param("id")Long id);

    public void updateView(Long id);

    public List<String> getGroupYears();

    public List<Blog> getBlogByYears(String year);
}
