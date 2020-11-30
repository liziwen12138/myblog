package com.lzw.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lzw.entity.Blog;
import com.lzw.entity.Tag;
import com.lzw.entity.Type;
import com.lzw.entity.User;
import com.lzw.service.BlogService;
import com.lzw.service.TagService;
import com.lzw.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("tags",tagService.getAllTag());
    }

    @GetMapping("blogs")
    public String blogs(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum, Model model){
        model.addAttribute("types",typeService.getAllType());
        PageHelper.startPage(pageNum,2);
        List<Blog> blogs=blogService.getAllBlog();
        PageInfo pageInfo=new PageInfo(blogs);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs";
    }
    @PostMapping("/blogs/search")
    public String search(@RequestParam(value = "pageNum",defaultValue = "1",required = false)Integer pageNum,Blog blog,Model model){
        PageHelper.startPage(pageNum,5);
        List<Blog> blogs=blogService.searchBlog(blog);
        PageInfo pageInfo=new PageInfo(blogs);
        setTypeAndTag(model);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("message", "查询成功");
        return "admin/blogs";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUserId(((User) session.getAttribute("user")).getId());
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTypeId(blog.getType().getId());
        if (blog.getId() == null) {
            //新增
            blogService.saveBlog(blog);
            attributes.addFlashAttribute("message", "操作成功");
        } else {
            //更新
            blogService.update(blog);
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/blogs";
    }
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable("id")Long id, Model model){
        setTypeAndTag(model);
        Blog blog=blogService.getBlogById(id);
        blog.setType(typeService.getTypeById(blog.getTypeId()));
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        blogService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }
}
