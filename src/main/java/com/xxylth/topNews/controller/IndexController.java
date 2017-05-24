package com.xxylth.topNews.controller;

import com.xxylth.topNews.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author XXY
 * @e-mail 547139255@qq.com
 * @detail
 */
@Controller
public class IndexController
{
    @RequestMapping(path = {"/","/index"})//设置访问路径
    @ResponseBody //返回的body部分
    public String index()
    {
        return "hello topNews";
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

    @RequestMapping(value = "/vm")
    public String news(Model model1,
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

}
