package com.xxylth.topNews.service;

import com.xxylth.topNews.dao.CommentDao;
import com.xxylth.topNews.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Service
public class CommentService
{
    @Autowired
    private CommentDao commentDao;


    /**
     * 返回指定实体的记录
     * @param entityId    //实体ID
     * @param entityType  //实体类型
     * @return //评论list集合
     */
    public List<Comment> selectByEntity(int entityId,int entityType)
    {
        return commentDao.selectByEntity(entityId,entityType);
    }

    /**
     * 添加一条评论
     * @param comment
     */
    public int  addComment(Comment comment)
    {
        return commentDao.addComment(comment);
    }

    /**
     * 获得指定实体的评论数
     * @param entityId
     * @param entityType
     * @return  //评论数
     */
    public int getCommentCount(int entityId,int entityType)
    {
        return commentDao.getCommentCount(entityId,entityType);
    }

}
