package com.xxylth.topNews;

import com.xxylth.topNews.dao.NewsDao;
import com.xxylth.topNews.dao.UserDao;
import com.xxylth.topNews.model.News;
import com.xxylth.topNews.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TopNewsApplication.class)

@Sql("/init-schema.sql")
public class InitDatabaseTests
{

	@Autowired
	UserDao userDao;

	@Autowired
	NewsDao newsDao;
	@Test
	public void initData()
	{
		Random random=new Random();
		for(int i=0;i<11;i++)
		{
			User user=new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",
					random.nextInt(1000)));
			user.setName(String.format("USER%d",i));
			user.setPassword("");
			user.setSalt("");
			userDao.addUser(user);

			user.setPassword("newpassword");
			userDao.updatePassword(user);

			Date date=new Date();
			News news=new News();
			news.setCommentCount(i);
			date.setTime(date.getTime()+1000*3600*5*i);
			news.setCreatedDate(date);
			news.setImage(String.format("http://images.nowcoder.com/head/%dm.png",
					random.nextInt(1000)));
			news.setLikeCount(i+1);
			news.setUserId(i+1);
			news.setTitle(String.format("TITLE{%d}",i));
			news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
			newsDao.addNews(news);
		}

		Assert.assertEquals("newpassword",userDao.selectById(1).getPassword());
		userDao.deleteById(1);
		Assert.assertNull(userDao.selectById(1));
	}
}






















