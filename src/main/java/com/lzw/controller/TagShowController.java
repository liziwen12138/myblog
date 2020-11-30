package com.lzw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzw.entity.Blog;
import com.lzw.entity.Tag;
import com.lzw.service.BlogService;
import com.lzw.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum, @PathVariable("id") Long id, Model model){
        List<Tag> tags = tagService.getAllTag();
        if(id==-1){
            id=tags.get(0).getId();
        }
        PageHelper.startPage(pageNum,5);
        List<Blog> blogs = blogService.getBlogByTagId(id);
        PageInfo pageInfo=new PageInfo<>(blogs);
        for(Tag tag:tags){
            tag.setBlogs(blogService.getBlogByTagId(tag.getId()));
        }
        Collections.sort(tags, new Comparator<Tag>() {
            @Override
            public int compare(Tag o1, Tag o2) {
                int len1=o1.getBlogs().size();
                int len2=o2.getBlogs().size();
                return len2-len1;
            }
        });
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("tags",tags);
        model.addAttribute("activeId",id);
        return "tags";
    }
}
