package com.xxylth.topNews.dao;


import com.xxylth.topNews.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageDao
{

    String TABLE_NAME=" message ";
    String INSERT_FIELDS=" from_id , to_id , content , has_read , conversation_id, created_date ";
    String SELECT_FIELDS=" id, "+INSERT_FIELDS;

    @Insert({" insert into ",TABLE_NAME, "(",INSERT_FIELDS, ") values" +
            " (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);


    //得到与某个人的所有对话
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}"})
    java.util.List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);


    //得到与所有人的最新那一条对话  把id这个字段存与某个人的对话数
    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt group by conversation_id order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select count(id)  from ",TABLE_NAME,
            "where conversation_id=#{conversationId} and to_id=#{userId} and has_read=0 "})
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);


    //更新has_read为已读
    @Update({"update ",TABLE_NAME," set has_read=1 where conversation_id=#{conversationId}"})
    int updateMessageHasRead(@Param("conversationId") String conversationId);
}
