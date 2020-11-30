package com.lzw.service.Impl;

import com.lzw.entity.Tag;
import com.lzw.entity.Type;
import com.lzw.dao.TypeMapper;
import com.lzw.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public List<Type> getAllType() {
        return typeMapper.getAllType();
    }

    @Transactional
    @Override
    public Type getTypeById(Long id) {
        return typeMapper.getTypeById(id);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Transactional
    @Override
    public void saveType(Type type) {
        typeMapper.saveType(type);
    }

    @Override
    public void updateType(Type type) {
        typeMapper.updateType(type);
    }

    @Override
    public void deleteType(Long id) {
        typeMapper.deleteType(id);
    }

    @Override
    public List<Type> getTopType() {
        return typeMapper.getTopType();
    }
}
