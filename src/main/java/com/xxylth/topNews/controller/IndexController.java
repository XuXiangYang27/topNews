package com.xxylth.topNews.controller;

import com.sun.deploy.net.HttpResponse;
import com.xxylth.topNews.model.User;
import com.xxylth.topNews.service.TopNewsService;
import com.xxylth.topNews.aspect.LogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
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
public class IndexController
{

    private static final Logger LOGGER= LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private TopNewsService topNewsService;
    @RequestMapping(path = {"/","/index"})//设置访问路径
    @ResponseBody //返回的body部分
    public String index(HttpSession session)
    {
        LOGGER.info("Visit index");
        return "hello topNews"+"<br>"+session.getAttribute("msg")
                +topNewsService.say()+"<br>";
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})//方括号里是参数
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,//路径参数
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type",defaultValue = "1") int type,//请求参数
                          @RequestParam(value="key",defaultValue = "nowcoder") String key)
    {
        //演示获取访问路径里的 路径与请求参数
        return String.format("GID{%s},UID{%d},Type{%d},Key{%s}",groupId,userId,type,key);
    }
    /**
     * 使用模板
     * @param model1
     * @param name
     * @return
     */
    @RequestMapping(value = "/vm")
    public String news(Model model1,//Model对象用于给模板传数据
                       @RequestParam(value = "name",defaultValue = "xxy") String name)
    {
        model1.addAttribute("value1","vv1");
        List<String> colors= Arrays.asList(new String[]{"RED","GREEN","BLUE"});


        Map<String,String> map=new HashMap<>();
        for (int i=0;i<4;i++)
        {
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model1.addAttribute("map",map);
        model1.addAttribute("colors",colors);
        model1.addAttribute("user",new User("许湘扬"));
        model1.addAttribute("name",name);
        return "news";
    }

    /**
     * 演示操作request对象
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(path ={"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session)
    {
        StringBuilder sb=new StringBuilder();
        Enumeration<String > headerNames=request.getHeaderNames();
        while (headerNames.hasMoreElements())
        {
            String name=headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br/>");
        }
        return sb.toString();
    }

    /**
     * 演示操作response对象(请求头设置与cookie设置)
     * @param nowcoderID
     * @param key
     * @param value
     * @param response
     * @return
     */
    @RequestMapping(path ={"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderID",defaultValue = "001") String nowcoderID,
                           @RequestParam(value = "key" ,defaultValue = "key") String key,
                           @RequestParam(value = "value" ,defaultValue = "value") String value,
                           HttpServletResponse response)
    {
        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);
        return "NowCoderId From Cookie:"+nowcoderID;
    }

    @RequestMapping(path = "/redirect/{code}")
    public String redirect(@PathVariable("code") int code,
                           HttpSession session)
    {
        /*
        RedirectView red=new RedirectView("/",true);
        if (code==301)
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return red;*/
        session.setAttribute("msg","jump from redirect");
        return "redirect:/";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key",required = false) String key)
    {
        if("admin".equals(key))
            return "hello admin";
        throw  new IllegalArgumentException("Key 错误");
    }

    /**
     * 统一处理页面错误
     * @param e
     * @return
     */
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e)
    {
        return "error : "+e.getMessage();
    }
}
