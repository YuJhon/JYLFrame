package com.jyl.util.basic;


import org.apache.log4j.Logger;

/**
 * <p>功能描述:</br> TODO</p>
 * @className  ClassUtil
 * @author  jiangyu
 * @date  2016年4月3日 下午5:56:58
 * @version  v1.0
 */
public class ClassUtil
{
    public static final Logger LOG = Logger.getLogger(ClassUtil.class);

    /**
     * @Description: 获取类的实例的class
     * @userName: jiangyu
     * @date: 2015年11月23日 下午6:06:13
     * @return
     */
    public static Class<?> getClazzByName(String fullName)
    {
        try
        {
            return Class.forName(fullName);
        }
        catch (ClassNotFoundException e)
        {
            LOG.error("GeneralTools getClazzByName ClassNotFoundException:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
