package com.xxylth.topNews.dao;


import com.xxylth.topNews.model.News;
import com.xxylth.topNews.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NewsDao
{
    String TABLE_NAME=" news ";
    String INSERT_FIELDS=" title , link , image , like_count , comment_count, created_date , user_id ";
    String SELECT_FIELDS=" id, "+INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,
            "(",INSERT_FIELDS,") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    @Select({"select ",SELECT_FIELDS," from  ",TABLE_NAME,
           " where id=#{newsId}"})
    News getById(int newsId);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);


}










