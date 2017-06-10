package com.xxylth.topNews.util;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 根据自定义的规则生成redis的key
 */
public class RedisKeyUtil
{
    private static String SPLIT=":";
    private static String BIZ_LIKE="LIKE";
    private static String BIZ_DISLIKE="DISLIKE";

    /**
     * 生成like的key
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getLikeKey(int entityType,int entityId)
    {
        return BIZ_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    /**
     * 生成dislike的key
     * @param entityType
     * @param entityId
     * @return
     */
    public static String getDisLikeKey(int entityType,int entityId)
    {
        return BIZ_DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
}






















