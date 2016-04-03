package com.jyl.util.basic;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * <p>功能描述:</br> 上下文工具类</p>
 * 
 * @className ContextHolderUtils
 * @author jiangyu
 * @date 2016年4月3日 下午5:31:21
 * @version v1.0
 */
public class ContextHolderUtils
{
    /**
     * SpringMvc下获取request
     * 
     * @return
     */
    public static HttpServletRequest getRequest()
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request;

    }

    /**
     * SpringMvc下获取session
     * 
     * @return
     */
    public static HttpSession getSession()
    {
        HttpSession session = getRequest().getSession();
        return session;

    }

}
