package com.xxylth.topNews.async;

import com.alibaba.fastjson.JSONObject;
import com.xxylth.topNews.util.JedisAdapter;
import com.xxylth.topNews.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Service
public class EventProducer
{
    @Autowired//用jedis储存队列
    JedisAdapter  jedisAdapter;

    /**
     * 把事件放入队列里面
     *
     * @param model
     * @return
     */
    public boolean firstEvent(EventModel model)
    {
        try
        {
            String json= JSONObject.toJSONString(model);//先序列化model
            String key= RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,json);
            return false;
        }
        catch (Exception e)
        {

            return false;
        }
    }
}













