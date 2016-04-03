package com.jyl.util.poi.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>功能描述:</br> excel 导出是用于标记id的</p>
 * 
 * @className ExcelTarget
 * @author jiangyu
 * @date 2016年4月3日 下午5:17:27
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface ExcelTarget {
    /**
     * 定义excel导出ID 来限定导出字段
     */
    public String id();
}
