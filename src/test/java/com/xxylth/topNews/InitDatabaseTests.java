package com.xxylth.topNews;

import com.xxylth.topNews.dao.*;
import com.xxylth.topNews.model.*;
import org.apache.velocity.runtime.directive.Foreach;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TopNewsApplication.class)

@Sql("/test.sql")
public class InitDatabaseTests
{

	@Autowired
	UserDao userDao;
	@Autowired
	NewsDao newsDao;
	@Autowired
	LoginTicketDao loginTicketDao;
	@Autowired
	CommentDao commentDao;
	@Autowired
	MessageDao messageDao;
	@Test
	public void initData()
	{
//		Random random=new Random();
//		for(int i=0;i<11;i++)
//		{
//			User user=new User();
//			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",
//					random.nextInt(1000)));
//			user.setName(String.format("USER%d",i));
//			user.setPassword("pwdTest");
//			user.setSalt("");
//			userDao.addUser(user);
//
//
//			Date date=new Date();
//			News news=new News();
//			news.setCommentCount(i);
//			date.setTime(date.getTime()+1000*3600*5*i);
//			news.setCreatedDate(date);
//			news.setImage(String.format("http://images.nowcoder.com/head/%dm.png",
//					random.nextInt(1000)));
//			news.setLikeCount(i+1);
//			news.setUserId(i+1);
//			news.setTitle(String.format("TITLE{%d}",i));
//			news.setLink(String.format("http://www.nowcoder.com/%d.html",i));
//			newsDao.addNews(news);
//
//			LoginTicket ticket=new LoginTicket();
//			ticket.setStatus(0);
//			ticket.setUserId(i+1);
//			ticket.setExpired(date);
//			ticket.setTicket(String.format("TICKET%d",i+1));
//			loginTicketDao.addTicket(ticket);
//
//
//			Comment comment=new Comment();
//			comment.setUserId(12);
//			comment.setCreatedDate(new Date());
//			comment.setContent("test");
//			comment.setStatus(0);
//			comment.setEntityId(1);
//			comment.setEntityType(1);
//			commentDao.addComment(comment);
//		}
//		LoginTicket ticket;
//		ticket=loginTicketDao.selectByTicket("TICKET2");
//		loginTicketDao.updateStatus(ticket.getTicket(),2);
//
//
//		User user=userDao.selectById(1);
//		user.setPassword("newpassword");
//		userDao.updatePassword(user);
//		Assert.assertEquals("newpassword",userDao.selectById(1).getPassword());
//		userDao.deleteById(1);
//		Assert.assertNull(userDao.selectById(1));
//
//
//
//		List<Comment> list=commentDao.selectByEntity(1,1);
//		System.out.println(list.size());
//		System.out.println(commentDao.getCommentCount(1,1));

		for (int i=1;i<=10;i++) {
			Message message = new Message();
			message.setContent("content"+i);
			message.setCreatedDate(new Date());
			message.setConversationId(i+":"+(i+1));
			message.setHasRead(0);
			message.setToId(i+1);
			message.setFromId(i);

			messageDao.addMessage(message);
		}

	}
}






















