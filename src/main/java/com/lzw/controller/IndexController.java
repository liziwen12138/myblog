package com.lzw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzw.entity.Blog;
import com.lzw.entity.Tag;
import com.lzw.entity.Type;
import com.lzw.service.BlogService;
import com.lzw.service.TagService;
import com.lzw.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum, Model model){
        PageHelper.startPage(pageNum,6);
//        得到所有分类
        List<Type> types=typeService.getTopType();
        for(Type type:types){
            type.setBlogs(blogService.getBlogByTypeId(type.getId()));
        }

        PageHelper.startPage(pageNum,6);
//        得到所有标签
        List<Tag> tags=tagService.getTopTag();
        for(Tag tag:tags){
            tag.setBlogs(blogService.getBlogByTagId(tag.getId()));
        }

        PageHelper.startPage(pageNum,6);
//      获得前台博客信息
        List<Blog> blogs=blogService.getIndexBlog();
        PageInfo<Blog> pageInfo=new PageInfo<>(blogs);
//        获得最新推荐
        PageHelper.startPage(pageNum,6);
        List<Blog> recommendBlogs=blogService.getRecommendBlog();

//        将数据通过model传给前端
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("recommendBlogs",recommendBlogs);

        return "index";
    }
    @PostMapping("/search")
    public String search(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,@RequestParam String query, Model model){
        PageHelper.startPage(pageNum,6);
        List<Blog> blogs = blogService.searchIndexBlog(query);
        PageInfo<Blog> pageInfo=new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id")Long id, Model model){
        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog",blog);
        return "blog";
    }
}
