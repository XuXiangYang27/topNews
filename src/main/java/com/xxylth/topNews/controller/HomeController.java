package com.xxylth.topNews.controller;
import com.xxylth.topNews.dao.UserDao;
import com.xxylth.topNews.model.HostHolder;
import com.xxylth.topNews.model.News;
import com.xxylth.topNews.model.ViewObject;
import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */

@Controller
public class HomeController
{
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;


    private List<ViewObject> getNews(int userId,int offset,int limit)
    {
        List<News> newsList=
                newsService.getLastedNews(userId,offset,limit);
        List<ViewObject> vos=new ArrayList<>();
        for(News news:newsList)
        {
            ViewObject vo=new ViewObject();
            vo.set("news",news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET,RequestMethod.POST})//设置访问路径
    public String index(Model model)
    {

        model.addAttribute("vos",getNews(0,0,10 ));
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.GET,RequestMethod.POST})//设置访问路径
    public String userIndex(Model model,
                            @PathVariable("userId") int userId,
                            @RequestParam(value = "pop",defaultValue = "0") int pop)
    {

        model.addAttribute("vos",getNews(userId,0,10 ));
        model.addAttribute("pop", pop);
        return "home";
    }
}












