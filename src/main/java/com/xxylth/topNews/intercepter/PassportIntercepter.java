package com.xxylth.topNews.intercepter;

import com.xxylth.topNews.dao.LoginTicketDao;
import com.xxylth.topNews.dao.UserDao;
import com.xxylth.topNews.model.HostHolder;
import com.xxylth.topNews.model.LoginTicket;
import com.xxylth.topNews.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 登录状态拦截器
 */
@Component
public class PassportIntercepter implements HandlerInterceptor
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;
    @Autowired
    private HostHolder hostHolder;
    //请求之前 进行处理
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception
    {

        System.out.println("-------拦截器---------");
        //一 判断客户端是否有名为ticket的cookie
        //1获取所有的cookie
        String ticket=null;
        if(httpServletRequest.getCookies()!=null)
        {
            //2遍历cookies 寻找名为ticket的cookie
            for (Cookie cookie:httpServletRequest.getCookies())
            {
                //3\得到ticket的值(字符串)
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        //二 判断cookie里的ticket是否存在于数据库中,是否过期
        if(ticket!=null)
        {
            LoginTicket loginTicket=loginTicketDao.selectByTicket(ticket);
            if(loginTicket==null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus()!=0)
               return  true;

            //三\保存用户
            User user=userDao.selectById(loginTicket.getUserId());
            hostHolder.setUsers(user);
        }
        return true;
    }


    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
     * 一般用来往model里传数据
     * 即:后端代码与前台交互的地方
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //把user对象传入页面中
        if(modelAndView!=null && hostHolder.getUser()!=null)
            modelAndView.addObject("user",hostHolder.getUser());
    }


    /**
     * 这个方法是请求执行完毕后执行,一般做一些释放资源的收尾工作
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        //释放当前线程的user对象
        hostHolder.clear();
    }
}
