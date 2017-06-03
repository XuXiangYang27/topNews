package com.xxylth.topNews.dao;
import com.xxylth.topNews.model.LoginTicket;
import com.xxylth.topNews.model.User;
import org.apache.ibatis.annotations.*;

//mapper代表与数据库一一匹配
@Mapper
public interface LoginTicketDao
{
    String TABLE_NAME="login_ticket";
    String INSERT_FIELDS=" user_id, expired, status,ticket ";
    String SELECT_FIELDS=" id, "+INSERT_FIELDS;


    /**
     * 向表中插入一个ticket
     * @param ticket
     * @return
     */
    @Insert({"insert into ",TABLE_NAME,
            "(",INSERT_FIELDS,") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    /**
     * 从表中获取指定的ticket
     * @param ticket
     * @return
     */
    @Select({"select ", SELECT_FIELDS ," from ",TABLE_NAME," where ticket=#{ticket} "})
    LoginTicket selectByTicket(String ticket);


    @Update({"update ",TABLE_NAME," set status=#{status} where ticket=#{ticket} "})
    void updateStatus(@Param("ticket") String ticket,
                      @Param("status") int  status);

}















