package com.jyl.util.poi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>功能描述:</br> 标记为excel 创建实体忽略,放置死循环的造成</p>
 * @className  ExcelIgnore
 * @author  jiangyu
 * @date  2016年4月3日 下午5:17:10
 * @version  v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelIgnore {

}
