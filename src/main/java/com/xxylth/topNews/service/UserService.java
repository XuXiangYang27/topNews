package com.xxylth.topNews.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.xxylth.topNews.dao.LoginTicketDao;
import com.xxylth.topNews.dao.UserDao;
import com.xxylth.topNews.model.LoginTicket;
import com.xxylth.topNews.model.User;
import com.xxylth.topNews.util.TopNewsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Service
public class UserService
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;

    /**
     * 注册功能
     * @param username
     * @param password
     * @return
     */
    public Map<String,Object> register(String username, String password,int rember)
    {
        //map存储注册错误信息
        Map<String,Object> map=new HashMap<String,Object>();

        if(StringUtils.isBlank(username))
        {
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {

            map.put("msgpwd","密码不能为空");
            return map;
        }
        else {
            if(password.trim().length()<6) {
                map.put("msgpwd", "密码长度不能小于6");
                return map;
            }
        }
        User user=userDao.selectByName(username);
        if(user!=null)
        {
            map.put("msgname", "用户名已经被注册!!!");
            return  map;
        }
        //设置用户信息
        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(TopNewsUtil.MD5(password+user.getSalt()));

        //向数据库插入用户信息
        userDao.addUser(user);

        //注册成功 然后 自动登录
        //登录
        //addLoginTicket(user.getId());是为指定用户添加一个ticket到数据库中
        String ticket=addLoginTicket(user.getId(),rember);
        map.put("ticket",ticket);
        return map;
    }


    public Map<String,Object> login(String username, String password,int rember)
    {
        //map存储注册错误信息
        Map<String,Object> map=new HashMap<String,Object>();

        if(StringUtils.isBlank(username))
        {
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {

            map.put("msgpwd","密码不能为空");
            return map;
        }
        User user=null;
                user=userDao.selectByName(username);
        if(user==null)
        {
            map.put("msgname", "用户名不存在");
            return  map;
        }
        if(!TopNewsUtil.MD5(password+user.getSalt()).equals(user.getPassword()))
        {
            map.put("msgpwd", "密码错误");
            return  map;
        }
        //账号密码正确  下发ticket

        //登录
        //addLoginTicket(user.getId());是为指定用户添加一个ticket到数据库中
        String ticket=addLoginTicket(user.getId(),rember);
        map.put("ticket",ticket);

        return map;
    }

    /**
     * 添加ticket
     * @param userId
     * @return 返回ticket字符串
     */
    private String addLoginTicket(int userId,int rember)
    {
        LoginTicket ticket=new LoginTicket();
        ticket.setUserId(userId);
        Date date=new Date();
        if (rember!=1)
            date.setTime(date.getTime()+1000*3600*24);
        else
            date.setTime(date.getTime()+1000*3600*24*5);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));

        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public User getUser(int id)
    {
        return userDao.selectById(id);
    }
    public void addUser(User user)
    {
        userDao.addUser(user);
    }

    public void logout(String ticket)
    {
        loginTicketDao.updateStatus(ticket,1);
    }
}














