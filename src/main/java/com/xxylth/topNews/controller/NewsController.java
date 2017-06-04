package com.xxylth.topNews.controller;

import com.xxylth.topNews.model.HostHolder;
import com.xxylth.topNews.model.News;
import com.xxylth.topNews.model.User;
import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.service.QiniuService;
import com.xxylth.topNews.util.TopNewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.Date;

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

    private static final Logger LOGGER= LoggerFactory.getLogger(NewsController.class);


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
