package com.lzw.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzw.entity.Blog;
import com.lzw.entity.Type;
import com.lzw.service.BlogService;
import com.lzw.service.TypeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum, @PathVariable("id")Long id, Model model){

        List<Type> types = typeService.getAllType();
        if(id==-1){
            id=types.get(0).getId();
        }
        PageHelper.startPage(pageNum,5);
        List<Blog> blogs = blogService.getBlogByTypeId(id);
        PageInfo pageInfo=new PageInfo<>(blogs);

        for(Type type:types){
            type.setBlogs(blogService.getBlogByTypeId(type.getId()));
        }
        Collections.sort(types, new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                int len1=o1.getBlogs().size();
                int len2=o2.getBlogs().size();
                return len2-len1;
            }
        });
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("types",types);
        model.addAttribute("activeId",id);
        return "types";
    }
}
