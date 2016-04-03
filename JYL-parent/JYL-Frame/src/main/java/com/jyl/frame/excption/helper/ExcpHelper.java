package com.jyl.frame.excption.helper;

/**
 * <p>功能描述</br> 异常抛出的辅助类</p>
 * @className ExcpHelper
 * @author jiangyu
 * @date 2016年3月17日 下午6:59:45
 * @version v1.0
 * @since v1.0
 */
public class ExcpHelper
{
    /**
     * 待校验的对象
     */
    private Object obj;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 校验条件
     */
    private Condition condition;
    /**
     * 参数信息
     */
    private Object[] paramInfo;

    public static enum Condition {
        /** 为空或者为"" **/
        EMPTY(101),
        /** 小于等于0 **/
        LE_0(102),
        /** 预编译参数个数和参数预编译值个数不匹配 **/
        NOT_MATCH(103);
        
        private Integer value;
        
        Condition(Integer value){
            this.value = value;
        }

        public Integer getValue()
        {
            return value;
        }
    }

    public ExcpHelper(Object obj, String errorCode, Condition condition)
    {
        this.obj = obj;
        this.errorCode = errorCode;
        this.condition = condition;
    }
    
    public ExcpHelper(Object obj, String errorCode, Condition condition,Object ... objs)
    {
        this.obj = obj;
        this.errorCode = errorCode;
        this.condition = condition;
        this.paramInfo = objs;
    }

    public Object getObj()
    {
        return obj;
    }

    public void setObj(Object obj)
    {
        this.obj = obj;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public Condition getCondition()
    {
        return condition;
    }

    public void setCondition(Condition condition)
    {
        this.condition = condition;
    }

    public Object[] getParamInfo()
    {
        return paramInfo;
    }

    public void setParamInfo(Object[] paramInfo)
    {
        this.paramInfo = paramInfo;
    }
}
