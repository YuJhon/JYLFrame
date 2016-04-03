package com.jyl.datasource.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jyl.datasource.DSConstants;


/**
 * <p>功能描述:</br> 数据源的上下文</p>
 * 
 * @className JYLDBContextHolder
 * @author jiangyu
 * @date 2016年4月3日 下午7:19:25
 * @version v1.0
 */
public class JYLDBContextHolder
{
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setGlobalDataSource()
    {
        contextHolder.set(DSConstants.DATASOURCE_GLOBAL_FLAG);
    }

    public static void setDynamicDataSource()
    {
        ServletRequestAttributes requests = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (null == requests || null == requests.getRequest())
        {
            return;
        }
        HttpServletRequest request = requests.getRequest();
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(DSConstants.DATASOURCE_IDENTITY_FLAG);
        if (obj == null)
        {
            contextHolder.set(DSConstants.DATASOURCE_GLOBAL_FLAG);
        }
        else
        {
            /** 增加一个接口 **/
            String brandId = String.valueOf(obj);
            contextHolder.set(brandId);
        }
    }

    public static void setDynamicDataSource(int shopId)
    {
        contextHolder.set(String.valueOf(shopId));
    }

    public static String getDataSourceIndex()
    {
        return contextHolder.get();
    }

    public static void clear()
    {
        contextHolder.remove();
    }
}
