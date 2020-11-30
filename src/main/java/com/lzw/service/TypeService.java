package com.lzw.service;

import com.lzw.entity.Blog;
import com.lzw.entity.Tag;
import com.lzw.entity.Type;

import java.util.List;

public interface TypeService {
    public List<Type> getAllType();
    public Type getTypeById(Long id);
    public Type getTypeByName(String name);
    public void saveType(Type type);
    public void updateType(Type type);
    public void deleteType(Long id);
//    改进sql语句后台实现首页右侧排序
    public List<Type> getTopType();

}
