package com.jyl.frame.dao;

import java.util.List;

/**
 * <p>功能描述</br> sql分页的回调接口</p>
 * @className PageFormatCallable
 * @author jiangyu
 * @date 2016年3月17日 下午6:48:58
 * @version v1.0
 * @since v1.0
 * @param <X> 自定义类型
 */
public interface PageFormatCallable<X>
{   
    /**
     * <p> 功能描述：将查询出来的分页数据转换成X类型格式的数据</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:49:11
     * @param list 分页结果对象
     * @return List<X> 转换后的结果对象
     * @version  v1.0
     * @since v1.0
     */
    List<X> format(List<Object> list);
}

