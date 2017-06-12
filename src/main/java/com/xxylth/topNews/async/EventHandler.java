package com.xxylth.topNews.async;

import java.util.List;

public interface EventHandler
{
    void doHandle(EventModel model);            //对时间进行处理
    List<EventType> getSupportEventTypes();     //表示 所关注的type



}
