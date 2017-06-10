package com.xxylth.topNews.service;

import com.xxylth.topNews.dao.MessageDao;
import com.xxylth.topNews.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Service
public class MessageService
{
    @Autowired
    private MessageDao messageDao;


    /**
     * 获取每个用户的最新对话 与 对话总数
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<Message> getConversationList(int userId, int offset,int limit)
    {
        return messageDao.getConversationList(userId,offset,limit);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        // conversation的总条数存在id里
        return messageDao.getConversationDetail(conversationId, offset, limit);
    }

    /**
     * 新增一条消息
     * @param message //消息bean
     * @return
     */
    public int addMessage(Message message)
    {
        return messageDao.addMessage(message);
    }


    /**
     * 得到当前用户与某一个人的
     * @param userId
     * @param conversationId
     * @return
     */
    public int getConversationUnreadCount(int userId, String conversationId)
    {
        return messageDao.getConversationUnreadCount(userId,conversationId);
    }


}
