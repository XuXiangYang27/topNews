package com.xxylth.topNews.controller;

import com.xxylth.topNews.model.EntityType;
import com.xxylth.topNews.model.HostHolder;
import com.xxylth.topNews.service.LikeService;
import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.util.TopNewsUtil;
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


    @RequestMapping(path={"/like"},method ={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String like(Model model,
                     @RequestParam("newsId") int  newId
                     )
    {
        int userId=hostHolder.getUser().getId();
        long likeCount=likeService.like(userId, EntityType.ENTITY_NEWS,newId);

        newsService.updateLikeCount((int)likeCount,newId);
        return TopNewsUtil.getJSONString(0,String.valueOf(likeCount));

    }
}






