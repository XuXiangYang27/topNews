package com.xxylth.topNews.service;

import com.xxylth.topNews.dao.UserDao;
import com.xxylth.topNews.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User getUser(int id)
    {
        return userDao.selectById(id);
    }
}














