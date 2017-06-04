package com.xxylth.topNews.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.velocity.runtime.directive.Foreach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;

/**
 * @author 许湘扬
 * @e-mail 547139255@qq.com
 * @detail 本项目的工具
 */
public class TopNewsUtil
{
    private static final Logger logger = LoggerFactory.getLogger(TopNewsUtil.class);

    //允许的图片后缀类型
    public static String[] IMAGE_FILE_EXT=new String[]{"png","bmp","jpg","jpeg"};
    //项目域名
    public static final String TOPNEWS_DOMAIN="http://127.0.0.1:8080/";

    public static final String IMAGE_DIR="/Users/XXY/TopNews_image/";

    public static final String QINIU_DOMAIN_prefix="http://oqyxae05r.bkt.clouddn.com/";

     /**
     * 判断文件后缀名是否合法
     *
     * @param fileExt
     * @return
     */
    public static  boolean isFileAllowed(String fileExt)
    {
        for(String ext:IMAGE_FILE_EXT)
        {
            if (fileExt.equals(fileExt))
                return true;
        }
        return false;
    }


    //生成json串
    public static String getJSONString(int code)
    {

        JSONObject json=new JSONObject();
        json.put("code",code);
        return json.toJSONString();
    }

    //生成json串
    public static String getJSONString(int code, String msg)
    {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    //生成json串
    public static String getJSONString(int code, Map<String,Object> map)
    {

        JSONObject json=new JSONObject();
        json.put("code",code);

        for (Map.Entry<String,Object> entry:map.entrySet())
        {
            json.put(entry.getKey(),entry.getValue());
        }
        return json.toJSONString();
    }

    /**
     * MD5加密
     *
     * @param key //需要加密的字符串
     * @return //MD5加密后字符串
     */
    public static String MD5(String key)
    {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象


            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e)
        {
            logger.error("生成MD5失败", e);
            return null;
        }
    }
}
