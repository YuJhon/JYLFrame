package com.jyl.frame.resp;

import com.jyl.frame.controller.dto.JYLResponse;
import com.jyl.frame.excption.utils.ErrorCodeUtil;

/**
 * <p>功能描述:</br> 返回值工具类 </p>
 * @className  ResponseUtil
 * @author  jiangyu
 * @date  2016年4月3日 下午7:10:00
 * @version  v1.0
 */
public final class ResponseUtil
{
    /**
     * @Description: modified by jiangyu
     * @date: 2015年11月25日 下午4:38:39
     * @param errorCode
     *            错误异常编码
     * @param replaceVal
     *            错误信息中的占位符替换的值
     * @return
     */
    public static JYLResponse error(String errorCode, Object... replaceVal)
    {
        JYLResponse response = new JYLResponse();
        response.setRetCode(errorCode);
        response.setRetMsg(ErrorCodeUtil.getErrorDesc(errorCode, replaceVal).replaceAll("\\d", "*"));

        return response;
    }

    /**
     * @Description: modified by jiangyu
     * @date: 2015年11月25日 下午4:38:31
     * @param errorCode
     *            错误异常编码
     * @param o
     *            存放的对象
     * @param replaceVal
     *            错误信息中的占位符替换的值
     * @return
     */
    public static JYLResponse error(String errorCode, Object o, Object... replaceVal)
    {
        JYLResponse response = new JYLResponse();
        response.setRetCode(errorCode);
        response.setRetMsg(ErrorCodeUtil.getErrorDesc(errorCode,replaceVal).replaceAll("\\d", "*"));
        response.setRetObj(o);
        return response;
    }

    public static JYLResponse ok(Object o)
    {
        JYLResponse response = new JYLResponse();
        response.setRetObj(o);

        return response;
    }

    public static JYLResponse ok(String msg, Object o)
    {
        JYLResponse response = new JYLResponse();
        response.setRetMsg(msg);
        response.setRetObj(o);

        return response;
    }
}
