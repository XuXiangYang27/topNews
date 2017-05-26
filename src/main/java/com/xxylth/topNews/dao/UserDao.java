package com.xxylth.topNews.dao;


import com.xxylth.topNews.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao
{
    String TABLE_NAME="user";
    String INSET_FIELDS=" name, password, salt,head_url";
    @Insert({"insert into user(name,password,slat,head_url) values()"})
    int addUser(User user);
}
