package com.xxylth.topNews.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.xxylth.topNews.dao.UserDao;
import com.xxylth.topNews.model.*;
import com.xxylth.topNews.service.CommentService;
import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.service.QiniuService;
import com.xxylth.topNews.service.UserService;
import com.xxylth.topNews.util.TopNewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 新闻控制器
 */
@Controller
public class NewsController
{
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    private static final Logger LOGGER= LoggerFactory.getLogger(NewsController.class);


    /**
     * 添加一条评论
     *
     * @param newsId  //新闻ID
     * @param content //评论内容
     * @return
     */
    @RequestMapping(path={"/addComment"},method ={RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                            @RequestParam("content") String content)
    {
        Comment comment=new Comment();

        try
        {
            comment.setCreatedDate(new Date());
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setUserId(hostHolder.getUser().getId());
            comment.setStatus(0);
            comment.setContent(content);

            commentService.addComment(comment);
            newsService.updateCommentCount(commentService.getCommentCount(newsId,EntityType.ENTITY_NEWS),newsId);

        }
        catch (Exception e)
        {
            LOGGER.error("添加评论失败!"+e.getMessage());
        }

        return "redirect:/news/"+newsId;
    }
    /**
     * 获取新闻详情
     *
     * @param newsId //新闻ID号
     * @param model  //交互参数
     * @return //详情页
     */
    @RequestMapping(path={"/news/{newsId}"},method ={RequestMethod.GET})
    public String newDetial(@PathVariable("newsId") int newsId,
                          Model model)
    {
        News news= null;
        news=newsService.getById(newsId);

        if (news!=null)
        {
            //添加评论信息
            List<Comment> list=commentService.selectByEntity(newsId, EntityType.ENTITY_NEWS);
            List<ViewObject> commenVOs=new ArrayList<>();
            for (Comment coment:list) {
                ViewObject vo = new ViewObject();
                vo.set("comment", coment);
                vo.set("user", userService.getUser(coment.getUserId()));
                commenVOs.add(vo);

            }
            model.addAttribute("comments",commenVOs);
            //添加新闻信息,发布人信息
            User owner=userService.getUser(news.getUserId());
            model.addAttribute("news", news);
            model.addAttribute("owner",owner);

        }
        return "detail";
    }
    /**
     * 发布一条新闻
     *
     * @param image //图片链接地址
     * @param title //新闻标题
     * @param link  //新闻链接
     * @return
     */
    @RequestMapping(path={"/user/addNews/"},method ={RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link)
    {
        try
        {
            News news=new News();
            User user=hostHolder.getUser();
            if (user!=null)
                news.setUserId(user.getId());
            else
                news.setUserId(-1);//-1代表匿名用户
            news.setImage(image);
            news.setCreatedDate(new Date());
            news.setTitle(title);
            news.setLink(link);
            newsService.addNews(news);
            return TopNewsUtil.getJSONString(0);
        }
        catch (Exception e)
        {
            LOGGER.error("添加咨询失败"+e.getMessage());
            return TopNewsUtil.getJSONString(1,"发布失败");
        }
    }
    /**
     * 加载存储在本地的图片
     *
     * @param imageName //图片名称
     * @param response
     */
    @RequestMapping(path={"/image"},method ={RequestMethod.GET})
    @ResponseBody
    public  void getImage(@RequestParam("name") String imageName,
                          HttpServletResponse response)
    {
        try
        {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(TopNewsUtil.IMAGE_DIR+imageName),response.getOutputStream());

        }
        catch (Exception e)
        {
            LOGGER.error("图片加载失败!"+e.getMessage());
        }
    }

    /**
     * 上传图片
     * @param file //上传的文件在file这个变量里
     * @return  图片url:http://127.0.0.1:8080/image?name=ad04b76fa48b4fb59d8071427780eb29.jpg
     */
    @RequestMapping(path={"/uploadImage"},method ={RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file)
    {
        try
        {
            //上传到本地
            // String fileUrl= newsService.saveImage(file );
            //上传到七牛云存储
            String fileUrl=qiniuService.saveImage(file);
            if (fileUrl==null)
                return TopNewsUtil.getJSONString(1,"上传图片失败");
            return TopNewsUtil.getJSONString(0,fileUrl);
        }
        catch (Exception e)
        {
            LOGGER.error("上传图片失败"+e.getMessage());
            return TopNewsUtil.getJSONString(1,"上传失败");
        }
    }
}
