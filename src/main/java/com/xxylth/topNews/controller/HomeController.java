package com.xxylth.topNews.controller;
import com.xxylth.topNews.dao.UserDao;
import com.xxylth.topNews.model.News;
import com.xxylth.topNews.model.ViewObject;
import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET,RequestMethod.POST})//设置访问路径
    public String index(Model model)
    {
        System.out.println("222222222222222");
        List<News> newsList=newsService.getLastedNews(0,0,10);
        System.out.println("11111111111111");
        List<ViewObject> vos=new ArrayList<>();
        for(News news:newsList)
        {
            ViewObject vo=new ViewObject();
            vo.set("news",news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "home";
    }

}












