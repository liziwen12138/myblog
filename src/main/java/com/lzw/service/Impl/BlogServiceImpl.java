package com.lzw.service.Impl;

import com.lzw.dao.BlogMapper;
import com.lzw.entity.Blog;
import com.lzw.entity.Tag;
import com.lzw.service.BlogService;
import com.lzw.service.TagService;
import com.lzw.util.MarkdownUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TagService tagService;

    @Override
    public Blog getBlogById(Long id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogMapper.getAllBlog();
    }

    @Override
    public void saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        blogMapper.saveBlog(blog);
    }

    @Override
    public void update(Blog blog) {
        blog.setUpdateTime(new Date());
        blogMapper.update(blog);
    }

    @Override
    public void delete(Long id) {
        blogMapper.delete(id);
    }

    @Override
    public List<Blog> searchBlog(Blog blog) {
        return blogMapper.searchBlog(blog);
    }

    @Override
    public List<Blog> getBlogByTypeId(Long id) {
        return blogMapper.getBlogByTypeId(id);
    }

    @Override
    public List<Blog> getBlogByTagId(Long id) {
        return blogMapper.getBlogByTagId(id);
    }

    @Override
    public List<Blog> getIndexBlog() {
        return blogMapper.getIndexBlog();
    }

    @Override
    public List<Blog> getRecommendBlog() {
        return blogMapper.getRecommendBlog();
    }

    @Override
    public List<Blog> searchIndexBlog(String query) {
        return blogMapper.searchIndexBlog(query);
    }

    @Transactional
    @Override
    public Blog getDetailedBlog(Long id) {
        Blog blog= blogMapper.getDetailedBlog(id);
        if(blog==null){
            try {
                throw new NotFoundException("该博客不存在");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        List tags=new ArrayList();
        if(blog.getTagIds()==""||blog.getTagIds().equals("")){
        }else {
            String[] tagIds=blog.getTagIds().split(",");
            for(String tagId:tagIds){
                Long tagid=Long.parseLong(tagId);
                tags.add(tagService.getTagById(tagid));
            }
            blog.setTags(tags);
        }
        String context=blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(context));

        blogMapper.updateView(id);
        return blog;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years=blogMapper.getGroupYears();
        Map<String,List<Blog>> map=new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for(String year:years){
            List<Blog> blogs=blogMapper.getBlogByYears(year);
            map.put(year,blogs);
        }

        return map;
    }

    @Override
    public int blogCount() {
        List<Blog> allBlog = blogMapper.getAllBlog();
        return allBlog.size();
    }
}
