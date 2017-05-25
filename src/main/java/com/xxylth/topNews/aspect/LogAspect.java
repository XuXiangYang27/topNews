package com.xxylth.topNews.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail log切面
 */

//表名这个类是面向切面的一个东西
@Aspect
@Component
public class LogAspect
{
    //用日志 演示切面编程
    private static final Logger LOGGER= LoggerFactory.getLogger(LogAspect.class);

    //*:返回值   类名.* 所有的方法
    @Before("execution(* com.xxylth.topNews.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint)//切面交互 包装成了一个 Joinpoint对象
    {
        StringBuilder sb=new StringBuilder(); //获得所有请求的参数
        for (Object arg:joinPoint.getArgs())
        {
            sb.append("arg:"+arg.toString()+"|");
        }
        LOGGER.info("before method "+sb.toString());
    }
    @After("execution(* com.xxylth.topNews.controller.IndexController.*(..))")
    public void afterMethod(JoinPoint joinPoint)
    {
        LOGGER.info("after method");
    }
}
