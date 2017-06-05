package com.xxylth.topNews.dao;

import com.xxylth.topNews.model.Comment;
import com.xxylth.topNews.model.LoginTicket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 评论中心 dao层
 */
@Mapper
public interface CommentDao
{
    String TABLE_NAME="comment";
    String INSERT_FIELD=" content,user_id,entity_id,entity_type,created_date,status ";
    String SELECT_FIELD=" id, "+INSERT_FIELD;


    /**
     * 增加一条评论
     *
     * @param comment //评论bean
     * @return
     */
    @Insert({"insert into ",TABLE_NAME,"( ",INSERT_FIELD," )",
            " values( #{content},#{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
    int addComment(Comment comment);


    /**
     * 得到指定实体 的所有评论记录
     *
     * @param entityId     //实体ID号
     * @param entityType   //实体类别
     * @return  指定实体的所有评论
     */
    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME,"where entity_id=#{entityId} and entity_type=#{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId,
                                 @Param("entityType") int entityType);


    /**
     * 得到指定实体的评论数(news中存有新闻评论数)
     * @param entityId   //两个参数可确定实体
     * @param entityType
     * @return
     */
    @Select({"select count(id) from ",TABLE_NAME," where entity_id=#{entityId} and entity_type=#{entityType} "})
    int getCommentCount(@Param("entityId") int entityId,
                                 @Param("entityType") int entityType);
}











