package com.xxylth.topNews.util;

import com.alibaba.fastjson.JSON;
import com.xxylth.topNews.controller.IndexController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Service
public class JedisAdapter implements InitializingBean
{
    private JedisPool pool=null;
    private static final Logger LOGGER= LoggerFactory.getLogger(JedisAdapter.class);


    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            LOGGER.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public long lpush(String key, String value) {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e)
        {
             LOGGER.error("发生异常" + e.getMessage());
            return 0;
        }
        finally
        {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public JedisAdapter()
    {
        pool=new JedisPool();
    }

    public void setObject(String key,Object obj)
    {
        set(key, JSON.toJSONString(obj));//把对象序列化成一个json对象
    }
    public <T> T getObject(String key,Class<T> clazz)
    {
        String value=get(key);
        if (value!=null)
        {
            return JSON.parseObject(value,clazz);
        }
        return null;
    }

    /**
     * 往set中添加value(即:往实体添加点赞人ID号)
     *
     * @param key    //news的id
     * @param value  //点赞人的userId
     * @return
     */
    public  long sadd(String key,String value)
    {
        Jedis jedis=null;
        try {
            jedis=getJedis();
            return jedis.sadd(key,value);
        }catch (Exception e)
        {
            LOGGER.error("发生异常"+e.getMessage());
            return 0;
        }
        finally {
            if (jedis!=null)
                jedis.close();
        }
    }
    public  long srem(String key,String value)
    {
        Jedis jedis=null;
        try {
            jedis=getJedis();
            return jedis.srem(key,value);
        }catch (Exception e)
        {
            LOGGER.error("发生异常"+e.getMessage());
            return 0;
        }
        finally {
            if (jedis!=null)
                jedis.close();
        }
    }
    /**
     * 判断userID是否给新闻点赞
     * @param key   //新闻
     * @param value //userId
     * @return
     */
    public boolean sismember(String key,String value)
    {
        Jedis jedis=null;
        try {
            jedis=getJedis();
            return jedis.sismember(key,value);
        }catch (Exception e)
        {
            LOGGER.error("发生异常"+e.getMessage());
            return false;
        }
        finally {
            if (jedis!=null)
                jedis.close();
        }
    }
    /**
     * 返回新闻点赞数
     * @param key   //新闻
     * @return   //返回新闻点赞数
     */
    public long scard(String key)
    {
        Jedis jedis=null;
        try {
            jedis=getJedis();
            return jedis.scard(key);
        }catch (Exception e)
        {
            LOGGER.error("发生异常"+e.getMessage());
            return 0;
        }
        finally {
            if (jedis!=null)
                jedis.close();
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("localhost",6379);
    }

    private Jedis getJedis()
    {
        return pool.getResource();
    }

    public String get(String key)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return getJedis().get(key);
        } catch (Exception e) {
            LOGGER.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void set(String key, String value)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            jedis.set(key, value);
        }
        catch (Exception e)
        {
            LOGGER.error("发生异常" + e.getMessage());
        }
        finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
    }

    /*
     * jedis 基本操作

    private void testJedis()
    {
        Jedis jedis=new Jedis();//默认连接127.0.0.1
        jedis.flushAll();

        jedis.set("name","xxy111");
        print(1,jedis.get("name"));

        jedis.rename("name","newname");
        print(1,jedis.get("newname"));

        jedis.setex("password",15,"111111");

        jedis.set("pv","100");
        jedis.incr("pv");
        print(2,jedis.get("pv"));

        jedis.incrBy("pv",5);
        print(3,jedis.get("pv"));


        //列表操作
        String listName="listA";
        for (int i=0;i<10;++i)
        {
            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(3,jedis.lrange(listName,0,12));
        print(4,jedis.llen(listName));
        print(5,jedis.lpop(listName));
        print(6,jedis.llen(listName));
        print(7,jedis.lindex(listName,3));
        print(8,jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER,"a4","after"));
        print(9,jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE,"a4","before"));
        print(10,jedis.lrange(listName,0,12));



        //hash
        String userKey="user12";
        jedis.hset(userKey,"name","许湘扬");
        jedis.hset(userKey,"age","123");
        jedis.hset(userKey,"phone","18983841231");

        print(12,jedis.hget(userKey,"name"));
        print(13,jedis.hgetAll(userKey));
        jedis.hdel(userKey,"phone");
        print(14,jedis.hgetAll(userKey));
        print(15,jedis.hkeys(userKey));
        print(16,jedis.hvals(userKey));
        print(17,jedis.hexists(userKey,"email"));
        print(18,jedis.hexists(userKey,"age"));
        //school域不存在时,才操作,  弱存在school域则比进行操作
        jedis.hsetnx(userKey,"school","cqupt");
        jedis.hsetnx(userKey,"name","xxy");
        print(19,jedis.hgetAll(userKey));


        //set
        String linkKeys1="newsLike1";
        String linkKeys2="newsLike2";
        for(int i=0;i<10;i++)
        {
            jedis.sadd(linkKeys1,String.valueOf(i));
            jedis.sadd(linkKeys2,String.valueOf(i*2));
        }
        print(20,jedis.smembers(linkKeys1));
        print(21,jedis.smembers(linkKeys2));
        print(22,jedis.sinter(linkKeys1,linkKeys2));
        print(23,jedis.sunion(linkKeys1,linkKeys2));
        print(24,jedis.sdiff(linkKeys1,linkKeys2));
        print(25,jedis.sismember(linkKeys1,"5"));//判断5是否是成员
        jedis.srem(linkKeys1,"5");//删除成员5
        print(26,jedis.smembers(linkKeys1));
        print(27,jedis.scard(linkKeys1));//集合个数
        print(28,jedis.scard(linkKeys2));
        jedis.smove(linkKeys2,linkKeys1,"14");//把元素从一个集合2移到集合1
        print(27,jedis.scard(linkKeys1));//集合个数
        print(28,jedis.scard(linkKeys2));

        //sort set  (优先队列 用来做排名)
        String rankKey="rankKey";
        jedis.zadd(rankKey,65,"jim");
        jedis.zadd(rankKey,50,"ben");
        jedis.zadd(rankKey,100,"jack");
        jedis.zadd(rankKey,80,"Json");
        jedis.zadd(rankKey,90,"jame");
        print(30,jedis.zcard(rankKey));//查看集合个数
        print(31,jedis.zcount(rankKey,50,90));//位于50到90的个数
        print(32,jedis.zscore(rankKey,"ben"));//查看某人分数
        jedis.zincrby(rankKey,2,"ben");//给某个元素加分
        print(33,jedis.zscore(rankKey,"ben"));//查看某人分数
        jedis.zincrby(rankKey,2,"ben1");//给某个元素加分
        print(34,jedis.zcard(rankKey));//查看集合个数
        print(35,jedis.zrange(rankKey,0,2));//查看1到3名的元素(乱序)(默认从小到大)
        print(36,jedis.zrevrange(rankKey,0,2));//查看1到3名(乱序)(从大到小)

        for(Tuple tuple:jedis.zrangeByScoreWithScores(rankKey,0,100))
        {
            print(37,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }

        print(38,jedis.zrank(rankKey,"ben1"));//查看某人排名
        print(39,jedis.zrevrank(rankKey,"ben1"));//查看某人排名


        //类似于连接池
        JedisPool pool=new JedisPool();//默认是八条线程
        for (int i=0;i<100;i++)
        {
            Jedis j=pool.getResource();
            j.get("a");
            System.out.println("POOL"+i);
            j.close();
        }
    }
    private  void print(int index,Object obj)
    {
        System.out.println(String.format("%d,%s",index,obj));
    }
    */

}





















