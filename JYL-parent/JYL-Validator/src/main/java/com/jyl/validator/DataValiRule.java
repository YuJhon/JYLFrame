package com.jyl.validator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jyl.validator.enums.ValiRegexType;


/**
 * <p>功能描述:</br> 数据校验</p>
 * 
 * @className DataValiRule
 * @author jiangyu
 * @date 2016年4月3日 下午6:15:05
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DataValiRule {
    /**
     * @Description: 是否可以为空
     * @userName: jiangyu
     * @date: 2016年2月18日 下午12:51:28
     * @return
     */
    boolean nullable() default false;

    /**
     * @Description: 最大长度
     * @userName: jiangyu
     * @date: 2016年2月18日 下午12:54:10
     * @return
     */
    int maxLength() default 0;

    /**
     * @Description: 最小长度
     * @userName: jiangyu
     * @date: 2016年2月18日 下午12:54:15
     * @return
     */
    int minLength() default 0;

    /**
     * @Description: 正则校验的类型
     * @userName: jiangyu
     * @date: 2016年2月18日 下午12:54:21
     * @return
     */
    ValiRegexType valiRegexType() default ValiRegexType.NONE;

    /**
     * @Description: 校验的正则
     * @userName: jiangyu
     * @date: 2016年2月18日 下午12:54:24
     * @return
     */
    String valiRegexExpression() default "";

    /**
     * @Description: 参数或者字段描述,这样能够显示友好的异常信息
     * @userName: jiangyu
     * @date: 2016年2月18日 下午12:54:28
     * @return
     */
    String description() default "";
}
