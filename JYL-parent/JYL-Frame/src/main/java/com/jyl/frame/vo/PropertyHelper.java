package com.jyl.frame.vo;


import com.jyl.frame.shqlutils.HqlFormatterUtil.Restriction;

/**
 * <p>功能描述</br> 属性辅助类</p>
 * @className PropertyHelper
 * @author jiangyu
 * @date 2016年3月17日 下午10:28:12
 * @version v1.0
 * @since v1.0
 */
public class PropertyHelper
{
    /** 属性名称 **/
    private String propertyName;

    /** 属性值 **/
    private Object value;

    /** 属性对应的约束条件 **/
    private Restriction res;

    /**
     * 是否升序排序
     */
    private Boolean autoOrderAsc = null;

    /**
     * <p>通过构造参数注入</p>
     * @param propertyName 属性名称
     * @param value 属性值
     * @param res 属性的约束条件
     */
    public PropertyHelper(String propertyName, Object value, Restriction res)
    {
        this.propertyName = propertyName;
        this.value = value;
        this.res = res;
    }

    /**
     * <p>构造函数重载</p>
     * @param propertyName 属性名称
     * @param value 属性值
     * @param res 属性的约束条件
     * @param autoOrderAsc  是否支持升序 默认false
     */
    public PropertyHelper(String propertyName, Object value, Restriction res, Boolean autoOrderAsc)
    {
        this.propertyName = propertyName;
        this.value = value;
        this.res = res;
        this.autoOrderAsc = autoOrderAsc;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public Object getValue()
    {
        return value;
    }

    public Restriction getRes()
    {
        return res;
    }

    public Boolean getAutoOrderAsc()
    {
        return autoOrderAsc;
    }

    public void setAutoOrderAsc(Boolean autoOrderAsc)
    {
        this.autoOrderAsc = autoOrderAsc;
    }
}
