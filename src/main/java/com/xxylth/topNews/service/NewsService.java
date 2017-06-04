package com.xxylth.topNews.service;

import com.xxylth.topNews.dao.NewsDao;
import com.xxylth.topNews.model.News;
import com.xxylth.topNews.util.TopNewsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

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

    //保存上传图片的本地目录
    public static String IMAGE_DIR="/Users/XXY/TopNews_image/";


    /**
     * 添加一条新的新闻
     *
     * @param news //新闻bean
     * @return
     */
    public int addNews(News news) {
        newsDao.addNews(news);
        return news.getId();
    }
    /**
     * 查询新闻
     * @param userId  //用户ID,为0时代表所有用户,其他值是为单个用户
     * @param offset  //记录起始点
     * @param limit   //记录终点
     * @return  //从数据库选择符合要求的新闻
     */
    public List<News> getLastedNews(int userId,int offset,int limit)
    {
        return newsDao.selectByUserIdAndOffset(userId,offset,limit);
    }

    /**
     * 保存上传的图片到本地
     * @param file //用户上传的图片
     * @return //本地保存的路径
     * @throws IOException
     */
    public String saveImage(MultipartFile file) throws IOException
    {
        //xxx.yyy=abc.jpg
        int dotPos=file.getOriginalFilename().lastIndexOf(".");
        if (dotPos<0)
        {
            return null;
        }
        //截取后缀名
        String fileExt=file.getOriginalFilename().substring(dotPos+1).toLowerCase();
        //如果图片后缀名不满足
        if (!TopNewsUtil.isFileAllowed(fileExt))
            return null;
        //赋予文件一个唯一的名字
        String fileName= UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;

       Files.copy(file.getInputStream(),new File(TopNewsUtil.IMAGE_DIR+fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);


        return  TopNewsUtil.TOPNEWS_DOMAIN+"image?name="+fileName;
    }
}
