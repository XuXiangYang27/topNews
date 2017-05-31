package com.xxylth.topNews.dao;
import com.xxylth.topNews.model.User;
import org.apache.ibatis.annotations.*;

//mapper代表与数据库一一匹配
@Mapper
public interface UserDao
{
    String TABLE_NAME="user";
    String INSET_FIELDS=" name, password, salt,head_url ";
    String SELECT_FIELDS=" id, name, password, salt,head_url ";

    @Insert({"insert into ",TABLE_NAME,
            "(",INSET_FIELDS,") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user); //#{name},#{password},#{salt},#{headUrl}这几个变量,会去从user里面去寻找

    @Select({"select ", SELECT_FIELDS ," from ",TABLE_NAME," where id=#{id} "})
    User selectById(int id);

    @Update({"update ",TABLE_NAME," set password=#{password} where id=#{id} "})
    void updatePassword(User user);

    @Delete({"delete from ",TABLE_NAME," where id=#{id} "})
    void deleteById(int id);
}















