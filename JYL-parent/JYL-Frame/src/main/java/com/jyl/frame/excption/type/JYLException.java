package com.jyl.frame.excption.type;

/**
 * <p>功能描述:</br> TODO</p>
 * @className  JYLException
 * @author  jiangyu
 * @date  2016年4月3日 下午6:32:19
 * @version  v1.0
 */
public class JYLException extends RuntimeException
{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4763294338542809396L;

    private String errorCode = "999999";

    private String desc;

    
    private Object[] replaceVal;

    
    public JYLException()
    {

    }

    public JYLException(String errorCode)
    {
        this.errorCode = errorCode;
    }

    
    public JYLException(String errorCode, Object... replaceVal)
    {
        this.errorCode = errorCode;
        this.replaceVal = replaceVal;
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public Object[] getReplaceVal()
    {
        return replaceVal;
    }
}