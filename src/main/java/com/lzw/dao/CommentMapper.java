package com.lzw.dao;

import com.lzw.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {

    public Comment getCommentByIdAndBlogId(@Param("id") Long id,@Param("blogId") Long blogId);

    public void saveComment(Comment comment);

    public List<Comment> getCommentByTopCommentAndBlogId(@Param("topCommentId")Long topCommentId,@Param("blogId")Long blogId);

    public List<Comment> getCommentByParentCommentIdAndBlogId(@Param("parentCommentId")Long parentCommentId,@Param("blogId")Long blogId);
}
