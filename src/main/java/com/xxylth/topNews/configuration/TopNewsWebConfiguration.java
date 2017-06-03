package com.xxylth.topNews.configuration;

import com.xxylth.topNews.intercepter.LoginRequiredInterceptor;
import com.xxylth.topNews.intercepter.PassportIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 注册拦截器
 */
@Component
public class TopNewsWebConfiguration extends WebMvcConfigurerAdapter
{
    @Autowired
    private PassportIntercepter passportIntercepter;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(passportIntercepter);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/setting*");
        super.addInterceptors(registry);
    }
}
