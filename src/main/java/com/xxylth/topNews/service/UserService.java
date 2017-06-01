package com.xxylth.topNews.service;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.xxylth.topNews.dao.UserDao;
import com.xxylth.topNews.model.User;
import com.xxylth.topNews.util.TopNewsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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


    public Map<String,Object> register(String username, String password)
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


        User user=userDao.selectByName(username);
        if(user!=null)
        {
            map.put("msgname", "用户名已经被注册");
            return  map;
        }

        //设置用户信息
        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(TopNewsUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);

        //注册成功 然后 自动登录

        return map;
    }
    public User getUser(int id)
    {
        return userDao.selectById(id);
    }
    public void addUser(User user)
    {
        userDao.addUser(user);
    }
}














