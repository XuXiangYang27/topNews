package com.xxylth.topNews.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 异步队列model
 */
public class EventModel
{
    private EventType type;  //事件类型
    private int actorId;     //事件触发者

    private  int entityType; //合起来表示出发对象
    private  int entityId;
    private int entityOwnerId; //对象所属者

    private Map<String,String>exts=new HashMap<>();//事件现场的参数

    public String getExt(String key)
    {
        return exts.get(key);
    }
    public EventModel setExt(String key,String value)
    {
        exts.put(key,value);
        return this;
    }


    public  EventModel(EventType type)
    {
        this.type=type;
    }
    public  EventModel()
    {
    }




    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return  this;
    }
}
















