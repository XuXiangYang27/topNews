package com.xxylth.topNews.service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.xxylth.topNews.util.TopNewsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail
 */
@Service
public class QiniuService
{
    private static final Logger logger= LoggerFactory.getLogger(QiniuService.class);

    //设置账号的access key和secret key
    String ACCESS_KEY = "xTTcDvLlySbYvEGc9nA_3H1oHOaTYqZJuQnz_aZS";
    String SECRET_KEY = "EsjXSS8OYG51wE2CcAzQJ6KG_0viwEvuj7H5oaIL";
    //设置上传空间
    String bucketname = "topnews";
    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    Configuration cfg=new Configuration(Zone.zone2());
    UploadManager uploadManager =new UploadManager(cfg) ;



    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {

            //验证是否有后缀名
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            //检验后缀名是否合法
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!TopNewsUtil.isFileAllowed(fileExt)) {
                return null;
            }


            //filename 为 xxx.yyy
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传

            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());

            //打印返回的信息
            if (res.isOK() && res.isJson())
            {
                //key为七牛云里的文件名
                String key=JSONObject.parseObject(res.bodyString()).get("key").toString();
                return TopNewsUtil.QINIU_DOMAIN_prefix+fileName;
            }
            else
            {
                logger.error("七牛异常:"+res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            logger.error("七牛异常2:" + e.getMessage());
            return null;
        }
    }


}
