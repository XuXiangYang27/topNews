package com.xxylth.topNews.model;

import org.springframework.stereotype.Component;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 代表当前用户是谁
 */
@Component
public class HostHolder
{
    private static ThreadLocal<User> users=new ThreadLocal<>();

    public User getUser()
    {
        return users.get();
    }

    public void setUsers(User user)
    {
        users.set(user);
    }

    public void clear()
    {
        users.remove();
    }
}
