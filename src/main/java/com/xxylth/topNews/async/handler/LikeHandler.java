package com.xxylth.topNews.async.handler;

import com.sun.tools.corba.se.idl.StringGen;
import com.xxylth.topNews.async.EventHandler;
import com.xxylth.topNews.async.EventModel;
import com.xxylth.topNews.async.EventType;
import com.xxylth.topNews.model.Message;
import com.xxylth.topNews.model.News;
import com.xxylth.topNews.model.User;
import com.xxylth.topNews.service.MessageService;
import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Component

public class LikeHandler implements EventHandler
{

    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    NewsService newsService;
    /**
     * 发送一条消息给被赞的用户,告诉他谁赞了他
     * @param model
     */
    @Override
    public void doHandle(EventModel model)
    {
        Message message=new Message();
        User userfrom=userService.getUser(model.getActorId());
        News news=newsService.getById(model.getEntityId());
        int fromId=model.getActorId();
        int toId=model.getEntityOwnerId();

        message.setContent("用户 "+userfrom.getName()+" 点赞了您的" +
                "新闻:"+news.getTitle());
        message.setToId(model.getEntityOwnerId());
        message.setFromId(model.getActorId());
        String conversationId=fromId<toId ? fromId+"_"+toId: toId+"_"+fromId;
        message.setConversationId(conversationId);
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.LIKE);
    }
}









