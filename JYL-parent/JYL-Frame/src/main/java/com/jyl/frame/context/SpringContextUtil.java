package com.jyl.frame.context;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * <p>功能描述:</br> spring上下文获取工具类 </p>
 * @className  SpringContextUtil
 * @author  jiangyu
 * @date  2016年4月3日 下午7:37:58
 * @version  v1.0
 */
public class SpringContextUtil implements ApplicationContextAware
{

    private SpringContextUtil()
    {

    }

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        // SpringContextUtil.applicationContext = applicationContext;
        setContext(applicationContext);
    }

    private static void setContext(ApplicationContext applicationContext)
    {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    /**
     * <p> 功能描述：获取bean对象</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:59:24
     * @param componentClass 要加载的bean类
     * @return T 自定义的类型
     * @since v1.0
     */
    public static <T> T getComponent(Class<T> componentClass)
    {
        return SpringContextUtil.getApplicationContext().getBean(componentClass);
    }
}
