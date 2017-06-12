package com.xxylth.topNews;

import com.xxylth.topNews.async.EventModel;
import com.xxylth.topNews.model.User;
import com.xxylth.topNews.util.JedisAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
public class JedisTest
{
    @Autowired
    JedisAdapter jedisAdapter;


    @Test
    public void testObject()
    {
        User user = new User();
        user.setHeadUrl("http://images.nowcoder.com/head/100t.png");
        user.setName("user1");
        user.setPassword("abc");
        user.setSalt("def");

        jedisAdapter=new JedisAdapter();
        jedisAdapter.setObject("user1", user);

        User u = jedisAdapter.getObject("user1", User.class);
        System.out.print(ToStringBuilder.reflectionToString(u));



    }


}
































