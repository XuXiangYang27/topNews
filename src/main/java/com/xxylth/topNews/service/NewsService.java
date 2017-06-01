package com.xxylth.topNews.service;

import com.xxylth.topNews.dao.NewsDao;
import com.xxylth.topNews.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Service
public class NewsService
{
    @Autowired
    NewsDao newsDao;


    public List<News> getLastedNews(int userId,int offset,int limit)
    {
        return newsDao.selectByUserIdAndOffset(userId,offset,limit);
    }
}
