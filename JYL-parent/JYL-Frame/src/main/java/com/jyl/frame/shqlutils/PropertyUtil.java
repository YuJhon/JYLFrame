package com.jyl.frame.shqlutils;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.jyl.frame.shqlutils.HqlFormatterUtil.Restriction;
import com.jyl.frame.vo.PropertyHelper;
import com.jyl.util.empty.EmptyCheckUtil;
/**
 * <p>功能描述</br> 属性工具类</p>
 * @className  PropertyUtil
 * @author  jiangyu
 * @date  2016年3月17日 下午11:12:11
 * @version  v1.0
 * @since v1.0
 */
public class PropertyUtil
{
    private static Set<PropertyHelper> PROPERTIES = Collections.synchronizedSet(new HashSet<PropertyHelper>());

    /**
     * <p> 功能描述：不支持排序</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:55:17
     * @param propertyName 属性名称
     * @param value 属性值
     * @param restriction 限制条件
     * @return PropertyUtil 注册了属性和约束条件的属性工具类，方便链式调用实现
     * @since v1.0
     */
    public PropertyUtil addProperty(String propertyName, Object value, Restriction restriction)
    {
        if (EmptyCheckUtil.isEmpty(propertyName) || EmptyCheckUtil.isEmpty(value))
        {
            return this;
        }
        PROPERTIES.add(new PropertyHelper(propertyName, value, restriction));
        return this;
    }
    
    /**
     * <p> 功能描述：条件字段需要排序</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:56:10
     * @param propertyName 属性名称
     * @param value 属性值
     * @param restriction 限制条件
     * @param auotAsc 是否升序
     * @return PropertyUtil 注册了属性和约束条件的属性工具类，方便链式调用实现
     * @since v1.0
     */
    public PropertyUtil addProperty(String propertyName, Object value, Restriction restriction,Boolean auotAsc)
    {
        if (EmptyCheckUtil.isEmpty(propertyName) || EmptyCheckUtil.isEmpty(value))
        {
            return this;
        }
        PROPERTIES.add(new PropertyHelper(propertyName, value, restriction,auotAsc));
        return this;
    }
    /**
     * <p> 功能描述：单纯的排序字段</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:56:58
     * @param propertyName
     * @param auotAsc
     * @return  PropertyUtil 注册了属性和约束条件的属性工具类，方便链式调用实现
     * @since v1.0
     */
    public PropertyUtil addProperty(String propertyName,Boolean auotAsc)
    {
        if (EmptyCheckUtil.isEmpty(propertyName) || EmptyCheckUtil.isEmpty(auotAsc))
        {
            return this;
        }
        PROPERTIES.add(new PropertyHelper(propertyName, null, null,auotAsc));
        return this;
    }

    public PropertyUtil clear()
    {
        PROPERTIES.clear();
        return this;
    }

    public Set<PropertyHelper> getPropertyHelps()
    {
        return PROPERTIES;
    }

    private PropertyUtil()
    {}

    private static class PropertyHold
    {
        private static PropertyUtil INSTANCE = new PropertyUtil();
    }

    public static PropertyUtil getInstance()
    {
        return PropertyHold.INSTANCE.clear();
    }
}
