package com.xxylth.topNews.dao;

import com.sun.tools.javac.util.List;
import com.xxylth.topNews.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MessageDao
{
    String TABLE_NAME=" message ";
    String INSERT_FIELDS=" from_id , to_id , content , has_read , conversation_id, created_date ";
    String SELECT_FIELDS=" id, "+INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME, "(",INSERT_FIELDS, ") values" +
            " (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset},#{limit}"})
    java.util.List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);


}
