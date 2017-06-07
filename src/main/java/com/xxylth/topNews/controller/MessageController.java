package com.xxylth.topNews.controller;

import com.xxylth.topNews.dao.MessageDao;
import com.xxylth.topNews.model.HostHolder;
import com.xxylth.topNews.model.Message;
import com.xxylth.topNews.model.User;
import com.xxylth.topNews.model.ViewObject;
import com.xxylth.topNews.service.MessageService;
import com.xxylth.topNews.service.UserService;
import com.xxylth.topNews.util.TopNewsUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Controller
public class MessageController
{
    private static final Logger LOGGER= LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model)
    {
        try
        {
            int hostUserId= hostHolder.getUser().getId();

            List<ViewObject> conservations=new ArrayList<>();



        }catch (Exception e)
        {
            LOGGER.error("获取站内信列表失败"+e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model,
                                     @RequestParam("conversationId") String conversationId)
    {
        try
        {
            System.out.println(conversationId);
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            System.out.println(conversationList.size());


            List<ViewObject> messages = new ArrayList<>();
            for(Message msg:conversationList)
            {
                ViewObject vo=new ViewObject();
                vo.set("message",msg);
                User user=userService.getUser(msg.getFromId());
                vo.set("userId",user.getId());
                vo.set("headUrl",user.getHeadUrl());
                vo.set("user_from",user);
                messages.add(vo);

            }
            model.addAttribute("messages",messages);
        }
        catch (Exception e)
        {
            LOGGER.error("获取失败2!"+e.getMessage());
        }

        return "letterDetail";
    }
    /**
     * 增加一个消息
     * @param fromId
     * @param toId
     * @param content
     * @return
     */
    @RequestMapping(path= {"/msg/addMessage"},method ={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                          @RequestParam("toId") int toId,
                          @RequestParam("content") String content)
    {
        try
        {
            Message message=new Message();
            message.setContent(content);
            message.setCreatedDate(new Date());
            message.setFromId(fromId);
            message.setToId(toId);
            message.setConversationId(
                    fromId<toId?String.format("%d_%d",fromId,toId):String.format("%d_%d",toId,fromId));
            messageService.addMessage(message);
            return TopNewsUtil.getJSONString(message.getId());
        }
        catch (Exception e)
        {
            LOGGER.error("添加站内信失败!"+e.getMessage());
            return TopNewsUtil.getJSONString(1,"添加站内信失败!");
        }

    }
}
