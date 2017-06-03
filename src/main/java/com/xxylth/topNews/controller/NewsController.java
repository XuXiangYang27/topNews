package com.xxylth.topNews.controller;

import com.xxylth.topNews.service.NewsService;
import com.xxylth.topNews.util.TopNewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 新闻控制器
 */
@Controller
public class NewsController
{

    @Autowired
    private NewsService newsService;
    private static final Logger LOGGER= LoggerFactory.getLogger(NewsController.class);
    /**
     * 上传图片
     * @param file //上传的文件在file这个变量里
     * @return  图片url:http://127.0.0.1:8080/image?name=ad04b76fa48b4fb59d8071427780eb29.jpg
     */
    @RequestMapping(path={"/uploadImage"},method ={RequestMethod.POST})
    @ResponseBody

    public String uploadImage(@RequestParam("file") MultipartFile file)
    {
        try {
            String fileUrl= newsService.saveImage(file );
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
