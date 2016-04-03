package com.jyl.util.http;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.jyl.util.empty.EmptyCheckUtil;


/**
 * <p>功能描述:</br> url编码</p>
 * 
 * @className UrlEncoderUtil
 * @author jiangyu
 * @date 2016年3月31日 下午10:01:05
 * @version v1.0
 */
public class UrlCoderUtil
{
    private static final Logger LOG = Logger.getLogger(UrlCoderUtil.class);

    /**
     * <p> 功能描述：UTF-8编码</p>
     * 
     * @author jiangyu
     * @date 2016年4月1日 下午5:37:39
     * @param params
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String UTF8Encode(String params)
    {
        String res = "";
        try
        {
            if (EmptyCheckUtil.isEmpty(params))
            {
                return params;
            }
            res = URLEncoder.encode(params, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            LOG.info("encode failed ,reason is :" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    /**
     * <p> 功能描述：UTF-8url解码</p>
     * 
     * @author jiangyu
     * @date 2016年4月1日 下午5:37:44
     * @param params
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String UTF8Decode(String params)
    {
        String res = "";
        try
        {
            res = URLDecoder.decode(params, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            LOG.info("decode failed ,reason is :" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }
}
