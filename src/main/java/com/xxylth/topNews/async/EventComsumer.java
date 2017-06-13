package com.xxylth.topNews.async;

import com.alibaba.fastjson.JSON;
import com.xxylth.topNews.util.JedisAdapter;
import com.xxylth.topNews.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail EventComsumer类
 */
@Service
public class EventComsumer implements InitializingBean,ApplicationContextAware
{

    private  static  final Logger logger= LoggerFactory.getLogger(EventComsumer.class);
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;


    //保存 事件类型-处理类 映射表(一个事件类型有一个到多个处理类)
    private Map<EventType,List<EventHandler>> config=new HashMap<>();


    //在初始化阶段就 配置好 事件-对应支持类 映射表
    @Override
    public void afterPropertiesSet() throws Exception
    {
        //把这个application中,所有实现EventHandler接口的类找出来
        Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);


        //把所有实现EventHandler接口的类,映射到config中
        if (beans!=null)
        {
            for(Map.Entry<String,EventHandler> entry :beans.entrySet())
            {
                System.out.println("1111111111111111111111111111"+entry.getValue().getClass().getName());
                List<EventType > eventTypes=entry.getValue().getSupportEventTypes();
                for (EventType type: eventTypes)
                {
                    if (!config.containsKey(type))
                        config.put(type, new ArrayList<EventHandler>());
                    config.get(type).add(entry.getValue());
                }

            }
        }

        //开一个线程,一直死循环取出事件队列里的事件给handler类
        Thread thread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    String key= RedisKeyUtil.getEventQueueKey();

                    //阻塞式取值  取出list
                    List<String>  events=jedisAdapter.brpop(0,key);//0代表一直等待
                    //遍历list种的model
                    for (String message:events)
                    {
                        //第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值
                        //参考自:  http://www.runoob.com/redis/lists-brpop.html
                        if (message.equals(key))
                            continue;

                        //反序列化得到model
                        EventModel eventModel= JSON.parseObject(message,EventModel.class);

                        if (!config.containsKey(eventModel.getType()))
                        {

                            logger.error("不能识别的事件");
                            System.out.println(eventModel.getType());
                            continue;
                        }

                        //调用每一个 支持的handler类
                        for (EventHandler handler: config.get(eventModel.getType()))
                        {
                            handler.doHandle(eventModel);
                        }
                    }

                }
            }
        });
        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}




