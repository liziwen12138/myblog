package com.lzw.service;

import com.lzw.entity.Comment;

import java.util.List;

public interface CommentService {

    public List<Comment> getCommentByBlogId(Long blogId);

    public void saveComment(Comment comment);
}
