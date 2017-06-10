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
     * @param entityType  //
     * @param entityId
     * @return
     */
    public long like(int userId,int entityType,int entityId )
    {
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);

        //给指定实体添加一个喜欢的
        jedisAdapter.sadd(likeKey,String.valueOf(userId));

        //取消用户对实体的dislike
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }
}

















