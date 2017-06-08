package com.xxylth.topNews.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.xxylth.topNews.model.User;
import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.service.TopNewsService;
import com.xxylth.topNews.service.UserService;
import com.xxylth.topNews.util.TopNewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author XXY
 * @e-mail 547139255@qq.com
 * @detail
 */
@Controller
public class LoginController
{
    @Autowired
    UserService userService;
    @Autowired
    NewsService newsService;

    private static final Logger LOGGER= LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(path = {"/reg/"})//设置访问路径
    @ResponseBody
    public String reg(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value="rember",defaultValue = "0") int rember,
                        HttpServletResponse response
                        )
    {
        try
        {
            Map<String,Object> map=userService.register(username,password,rember);
            if (map.containsKey("ticket"))
            {
                System.out.println("ticket");
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());

                cookie.setPath("/");//设置cookie全站有效
                if(rember>0)//如果勾选记住我,cookie设置为5天
                    cookie.setMaxAge(3600*24*5);
                response.addCookie(cookie);
                return TopNewsUtil.getJSONString(0, "注册成功");
            }
            else
                return  TopNewsUtil.getJSONString(1,map);
        }
        catch (Exception e)
        {
            LOGGER.error("注册异常"+e.getMessage());
            return TopNewsUtil.getJSONString(1,"注册异常");
        }
    }
    @RequestMapping(path = {"/login"})//设置访问路径
    @ResponseBody
    public String login(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value="rember",defaultValue = "0") int rember,
                        HttpServletResponse response
    )
    {
        try
        {

            Map<String,Object> map=userService.login(username,password, rember);

            //账号密码正确,就会有ticket
            if (map.containsKey("ticket"))
            {

                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());

                cookie.setPath("/");//设置cookie全站有效
                if(rember>0)//如果勾选记住我,cookie设置为5天
                    cookie.setMaxAge(3600*24*5);
                response.addCookie(cookie);
                return TopNewsUtil.getJSONString(0, "登录成功");
            }
            else
                return  TopNewsUtil.getJSONString(1,map);
        }
        catch (Exception e)
        {
            LOGGER.error("登录异常"+e.getMessage());
            return TopNewsUtil.getJSONString(1,"登录异常");
        }
    }

    @RequestMapping(path = {"/logout/"})//设置访问路径
    public String logout(@CookieValue("ticket") String ticket)
    {
        userService.logout(ticket);

        return "redirect:/";//退出登录 跳转到首页
    }
}























