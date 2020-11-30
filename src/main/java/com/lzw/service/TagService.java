package com.lzw.service;

import com.lzw.entity.Blog;
import com.lzw.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagService {
    public List<Tag> getAllTag();
    public Tag getTagById(Long id);
    public Tag getTagByName(String name);
    public void saveTag(Tag tag);
    public void updateTag(Tag tag);
    public void deleteTag(Long id);
    //    改进sql语句后台实现首页右侧排序
    public List<Tag> getTopTag();
}
