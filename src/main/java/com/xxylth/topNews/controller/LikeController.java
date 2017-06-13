package com.xxylth.topNews.controller;

import com.xxylth.topNews.async.EventModel;
import com.xxylth.topNews.async.EventProducer;
import com.xxylth.topNews.async.EventType;
import com.xxylth.topNews.model.EntityType;
import com.xxylth.topNews.model.HostHolder;
import com.xxylth.topNews.model.News;
import com.xxylth.topNews.service.LikeService;
import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.util.JedisAdapter;
import com.xxylth.topNews.util.RedisKeyUtil;
import com.xxylth.topNews.util.TopNewsUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Controller
public class LikeController
{
    private static final Logger logger= LoggerFactory.getLogger(LikeController.class);

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private LikeService likeService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private EventProducer eventProducer;


    @RequestMapping(path={"/like"},method ={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String like(
                     @RequestParam("newsId") int  newId
                     )
    {

        int userId=hostHolder.getUser().getId();
        long likeCount=likeService.like(userId, EntityType.ENTITY_NEWS,newId);
        newsService.updateLikeCount((int)likeCount,newId);

        News news=newsService.getById(newId);

        //加入事件队列
        eventProducer.firstEvent(new EventModel().setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.ENTITY_NEWS).setEntityId(newId).setEntityOwnerId(news.getUserId()));

        return TopNewsUtil.getJSONString(0,String.valueOf(likeCount));

    }
    @RequestMapping(path={"/dislike"},method ={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String dislike(
            @RequestParam("newsId") int  newId
    )
    {

        int userId=hostHolder.getUser().getId();
        long likeCount=likeService.disLike(userId, EntityType.ENTITY_NEWS,newId);
        newsService.updateLikeCount((int)likeCount,newId);
        return TopNewsUtil.getJSONString(0,String.valueOf(likeCount));

    }
}






