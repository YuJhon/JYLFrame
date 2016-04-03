package com.jyl.frame.shqlutils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jyl.frame.constants.BaseErrorCode;
import com.jyl.frame.excption.enums.ExcpTypeEnum;
import com.jyl.frame.excption.helper.ExcpHelper;
import com.jyl.frame.excption.helper.ExcpHelper.Condition;
import com.jyl.frame.excption.helper.ValiContainer;


/**
 * <p>功能描述</br> hql组装工具类</p>
 * @className HqlFormatterUtil
 * @author jiangyu
 * @date 2016年3月17日 下午6:54:20
 * @version v1.0
 * @since v1.0
 */
public class HqlFormatterUtil
{
    private static Map<String, Restriction> HQL_PARAM_MAP = new ConcurrentHashMap<String, Restriction>();

    private HqlFormatterUtil()
    {}

    public HqlFormatterUtil clear()
    {
        HQL_PARAM_MAP.clear();
        return this;
    }

    private static class FormatterHolder
    {
        private static HqlFormatterUtil INSTANCE = new HqlFormatterUtil();
    }

    public static HqlFormatterUtil getInstance()
    {
        return FormatterHolder.INSTANCE.clear();
    }

    /** 运算符 **/
    public static enum Restriction {
        EQ("=", Integer.valueOf(1)), 
        LE("<=", Integer.valueOf(2)), 
        GE(">=", Integer.valueOf(3)), 
        NE("<>", Integer.valueOf(4)), 
        L("<", Integer.valueOf(5)), 
        G(">", Integer.valueOf(6)), 
        LIKE("like", Integer.valueOf(7)), 
        EQ_OR("OR", Integer.valueOf(8)),
        IN("IN",Integer.valueOf(9));

        private String operator;

        private Integer value;

        private Restriction(String operator, Integer value)
        {
            this.operator = operator;
            this.value = value;
        }

        public String getOperator()
        {
            return operator;
        }

        public Integer getValue()
        {
            return value;
        }
    }

    /**
     * <p> 功能描述：添加属性条件</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:54:36
     * @param propertyName 属性名称
     * @param restriction 约束条件
     * @return HqlFormatterUtil 添加了限制条件的hql工具类
     * @since v1.0
     */
    public HqlFormatterUtil addRestrict(String propertyName, Restriction restriction)
    {
        /** 为空性的校验 **/
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(propertyName, BaseErrorCode.PROPERTYNAME_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(restriction, BaseErrorCode.QUERY_RESTRICTION_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate(ExcpTypeEnum.SERVICE);
        HQL_PARAM_MAP.put(propertyName, restriction);
        return this;
    }

    /**
     * <p> 功能描述：格式化hql语句</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:54:59
     * @return String 组织好的hql语句
     * @since v1.0
     */
    public String format()
    {
        String hqlStr = "";
        for (Map.Entry<String, Restriction> entry : HQL_PARAM_MAP.entrySet())
        {
            String property = entry.getKey();
            String res = entry.getValue().getOperator();
            if (Restriction.LIKE.getOperator().equals(res))
            {
                hqlStr += " and " + property + " " + res + ":" + property;
            }
            else if (Restriction.IN.getOperator().equals(res))
            {
                hqlStr += " and " + property + " " + res + " (:" + property+" )";
            }
            else
            {
                hqlStr += " and " + property + res + ":" + property;
            }
        }
        return hqlStr;
    }
}
