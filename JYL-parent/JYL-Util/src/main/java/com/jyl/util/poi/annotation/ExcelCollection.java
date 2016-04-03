package com.jyl.util.poi.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>功能描述:</br> 导出的集合</p>
 * @className  ExcelCollection
 * @author  jiangyu
 * @date  2016年4月3日 下午5:15:41
 * @version  v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCollection {
    /**
     * 导出时，对应数据库的字段 主要是用户区分每个字段， 不能有annocation重名的 导出时的列名 导出排序跟定义了annotation的字段的顺序有关
     * 可以使用a_id,b_id来确实是否使用
     */
    public String exportName();

    /**
     * 展示到第几个同样可以使用a_id,b_id
     */
    public String orderNum() default "0";

    /**
     * 创建时创建的类型 默认值是 arrayList
     */
    public String type() default "java.util.ArrayList";
}
