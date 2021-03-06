package com.lzw.controller;

import com.lzw.entity.Blog;
import com.lzw.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class archiveShowController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> map = blogService.archiveBlog();
        model.addAttribute("archiveMap",map);
        model.addAttribute("blogCount",blogService.blogCount());
        return "archives";
    }
}
