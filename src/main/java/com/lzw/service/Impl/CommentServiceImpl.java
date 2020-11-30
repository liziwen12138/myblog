package com.lzw.service.Impl;

import com.lzw.dao.CommentMapper;
import com.lzw.entity.Blog;
import com.lzw.entity.Comment;
import com.lzw.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {
//        获取当前博客下的最高级评论
        List<Comment> comments=commentMapper.getCommentByParentCommentIdAndBlogId((long) -1,blogId);
        for(Comment comment:comments){
            Long topId=comment.getId();
            comment.setChildComments(commentMapper.getCommentByTopCommentAndBlogId(topId,blogId));
        }
        return comments;
    }

    @Transactional
    @Override
    public void saveComment(Comment comment) {
        Long parentCommentId = comment.getParentCommentId();
        if(parentCommentId==-1){
            comment.setParentCommentId(parentCommentId);
            comment.setTopCommentId((long) -1);
        }else {
            comment.setParentCommentId(parentCommentId);
            Comment comment1 = commentMapper.getCommentByIdAndBlogId(parentCommentId, comment.getBlogId());
            if(comment1.getTopCommentId()== -1){
                comment.setTopCommentId(comment1.getId());
            }else {
                comment.setTopCommentId(comment1.getTopCommentId());
            }
        }
        comment.setCreateTime(new Date());
        commentMapper.saveComment(comment);
    }
}
