package com.xxylth.topNews.service;

import com.xxylth.topNews.util.JedisAdapter;
import com.xxylth.topNews.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Service
public class LikeService
{
    @Autowired
    private JedisAdapter jedisAdapter;



    /**
     * 如果喜欢返回1,如果不喜欢返回-1,否则返回0
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    public int getLikeStatus(int userId,int entityType,int entityId )
    {
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);
        //先看 是否喜欢 喜欢就返回1
        if(jedisAdapter.sismember(likeKey,String.valueOf(userId)))
            return 1;

        //再看看是不是 不喜欢 ,不喜欢返回-1, 都不是返回0
        return jedisAdapter.sismember(RedisKeyUtil.getDisLikeKey(entityType,entityId),String.valueOf(userId))
                ==true? -1:0;
    }

    /**
     * 给某个实体添加一个赞
     * @param userId      //点赞人ID号
     * @param entityType  //实体类型
     * @param entityId    //实体id号
     * @return   //返回实体的点赞数
     */
    public long like(int userId,int entityType,int entityId )
    {
        //给指定实体添加一个喜欢的
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);
        System.out.println(jedisAdapter.sadd(likeKey,String.valueOf(userId)));
        System.out.println(jedisAdapter.scard(likeKey));
        //取消用户对实体的dislike
        String disliksKey=RedisKeyUtil.getDisLikeKey(entityType,entityId);
        jedisAdapter.srem(disliksKey,String.valueOf(userId));
        //返回实体点赞总数
        return jedisAdapter.scard(likeKey);
    }


    /**
     * 对 实体 取消赞
     * @param userId
     * @param entityType
     * @param entityId
     * @return //返回实体的最新点赞数
     */
    public long disLike(int userId,int entityType,int entityId )
    {
        //添加用户对实体的dislike
        String disliksKey=RedisKeyUtil.getDisLikeKey(entityType,entityId);
        jedisAdapter.sadd(disliksKey,String.valueOf(userId));

        //取消用户对实体的like
        String liksKey=RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.srem(liksKey,String.valueOf(userId));

        //更新数据库的

        //返回实体点踩总数
        return jedisAdapter.scard(liksKey);

    }
}

















