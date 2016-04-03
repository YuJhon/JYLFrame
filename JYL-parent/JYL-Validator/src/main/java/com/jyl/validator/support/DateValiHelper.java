package com.jyl.validator.support;

import java.lang.reflect.Field;

import com.jyl.util.basic.RegexUtil;
import com.jyl.util.empty.EmptyCheckUtil;
import com.jyl.validator.DataValiRule;
import com.jyl.validator.enums.ValiRegexType;
import com.jyl.frame.constants.BaseErrorCode;
import com.jyl.frame.excption.utils.ExcpResultUtil;
/**
 * <p>功能描述:</br> 数据校验辅助类</p>
 * @className  DateValiHelper
 * @author  jiangyu
 * @date  2016年4月3日 下午7:04:06
 * @version  v1.0
 */
public class DateValiHelper
{
    private static DataValiRule dataValiRule;

    private DateValiHelper()
    {}

    private static class DVHolder
    {
        private static final DateValiHelper INSTANCE = new DateValiHelper();
    }

    public static DateValiHelper getInstance()
    {
        return DVHolder.INSTANCE;
    }

    /**
     * @Description: 解析的入口
     * @userName: jiangyu
     * @date: 2016年2月18日 下午1:30:10
     * @param object
     *            要校验的对象
     */
    public void valid(Object object)
    {
        /** 1.获取Object的类型 **/
        Class<? extends Object> clazz = object.getClass();
        /** 2.获取该类型声明的成员 **/
        Field[] fields = clazz.getDeclaredFields();
        /** 3.遍历属性 **/
        for (Field field : fields)
        {
            /** 对于private私有化的成员变量,通过setAccessible来修改访问权限 **/
            field.setAccessible(true);

            /** 校验的核心处理 **/
            validateCoreProcess(field, object);

            /** 重新设置回私有权限 **/
            field.setAccessible(false);
        }
    }

    /**
     * @Description: 校验核心
     * @userName: jiangyu
     * @date: 2016年2月18日 下午1:40:56
     * @param field
     * @param obj
     */
    private void validateCoreProcess(Field field, Object obj)
    {
        /**
         * 描述
         */
        String description;
        /**
         * 值
         */
        Object value;

        dataValiRule = field.getAnnotation(DataValiRule.class);
        try
        {
            value = field.get(obj);
            /**
             * 如果没有启用数据校验的注解就不需要进行校验
             */
            if (dataValiRule == null)
            {
                return;
            }
            /**
             * 如果没有填写描述，就默认是字段的名称
             */
            description = dataValiRule.description().equals("") ? field.getName() : dataValiRule.description();

            /** 注解解析开始 **/
            /** 1.非空的校验 **/
            if (!dataValiRule.nullable())
            {
                if (value == null)
                {
                    ExcpResultUtil.validateExcpFail(BaseErrorCode.VALIDATE_NULLABLE,description);
                }
                if (EmptyCheckUtil.isEmpty(value.toString()))
                {
                    ExcpResultUtil.validateExcpFail(BaseErrorCode.VALIDATE_NULLABLE,description);
                }
            }
            /** 2.最大长度的限制 **/
            if (value.toString().length() > dataValiRule.maxLength() && dataValiRule.maxLength() != 0)
            {
                ExcpResultUtil.validateExcpFail(BaseErrorCode.VALIDATE_MAX_LENGTH,description, dataValiRule.maxLength());
            }
            /** 3.最小长度的限制 **/
            if (value.toString().length() < dataValiRule.minLength()&& dataValiRule.minLength() != 0)
            {
                ExcpResultUtil.validateExcpFail(BaseErrorCode.VALIDATE_MIN_LENGTH,description, dataValiRule.minLength());
            }
            /** 4.常用正则类型校验 **/
            if (dataValiRule.valiRegexType() != ValiRegexType.NONE)
            {
                switch (dataValiRule.valiRegexType())
                {
                    case IP:
                        if (!RegexUtil.isIP(value.toString()))
                        {
                            ExcpResultUtil.validateExcpFail(BaseErrorCode.ADDRESS_FORMAT_IS_INVALID, description);
                        }
                        break;
                    case TEL:
                        
                        break;
                    case NONE:

                        break;
                    case EMAIL:
                        if (!RegexUtil.isEmail(value.toString()))
                        {
                            ExcpResultUtil.validateExcpFail(BaseErrorCode.ADDRESS_FORMAT_IS_INVALID, description);
                        }
                        break;
                    case PHONE:
                        if (!RegexUtil.isPhone(value.toString()))
                        {
                            ExcpResultUtil.validateExcpFail(BaseErrorCode.IS_NOT_VALIDATE_PHONE_NUM, description);
                        }
                        break;
                    case IDCARD:

                        break;
                    case NUMBER:
                        if (!RegexUtil.isNumber(value.toString()))
                        {
                            ExcpResultUtil.validateExcpFail(BaseErrorCode.IS_NOT_VALID_NUMBER,description);
                        }
                        break;
                    case CHINESE:
                        if (RegexUtil.isChineseCJK(value.toString()))
                        {
                            ExcpResultUtil.validateExcpFail(BaseErrorCode.CAN_NOT_CONTAINS_CHINESE_CHARACTER, description);
                        }
                        break;
                    case SPECIALCHAR:
                        if (RegexUtil.hasSpecialChar(value.toString()))
                        {
                            ExcpResultUtil.validateExcpFail(BaseErrorCode.CAN_NOT_CONTAINS_SPECIAL_CHARACTER, description);
                        }
                        break;
                    default:
                        ExcpResultUtil.validateExcpFail(BaseErrorCode.DO_NOT_SUPPORT_THIS_TYPE_VALIDATE, description);
                        break;
                }
            }

            /** 5.自定义正则校验  如果有正则表达式,则进行正则的校验 **/
            if (!dataValiRule.valiRegexExpression().equals(""))
            {
                if (value.toString().matches(dataValiRule.valiRegexExpression()))
                {
                    ExcpResultUtil.validateExcpFail(BaseErrorCode.VALUE_FORMAT_IS_INVALID,description);
                }
            }
        }
        catch (IllegalArgumentException e)
        {
            /** 反射异常: **/
            ExcpResultUtil.failSys(BaseErrorCode.REFLECTION_INVOKE_METHOD_WITH_WRONG_PARAMS_ERROR);
        }
        catch (IllegalAccessException e)
        {
            /** 反射异常: **/
            ExcpResultUtil.failSys(BaseErrorCode.REFLECTION_INVOKE_PRIVATE_METHOD_ERROR);
        }
    }
}
