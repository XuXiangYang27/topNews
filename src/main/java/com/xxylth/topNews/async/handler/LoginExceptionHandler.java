package com.xxylth.topNews.async.handler;
import com.xxylth.topNews.async.EventHandler;
import com.xxylth.topNews.async.EventModel;
import com.xxylth.topNews.async.EventType;
import com.xxylth.topNews.model.Message;
import com.xxylth.topNews.service.MessageService;
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
public class LoginExceptionHandler implements EventHandler
{
    @Autowired
    MessageService messageService;



    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("你上次的登陆IP异常");
        // SYSTEM ACCOUNT
        message.setFromId(1);
        message.setCreatedDate(new Date());
        message.setConversationId(1+"_"+model.getActorId());
        messageService.addMessage(message);


    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}























