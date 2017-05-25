package com.xxylth.topNews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 演示AOP新加的类
 */
@Controller
public class SettingController
{
    @RequestMapping("/setting")
    @ResponseBody
    public String setting()
    {
        return "Setting:OK";
    }
}
