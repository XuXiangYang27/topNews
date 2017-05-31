package com.xxylth.topNews.controller;

import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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


    @RequestMapping(path = {"/","/index"})//设置访问路径
    public String index(HttpSession session)
    {
        return "home";
    }

}
