package com.jyl.frame.controller.dto;

/**
 * <p>功能描述:</br> 统一的返回对象</p>
 * 
 * @className JYLResponse
 * @author jiangyu
 * @date 2016年4月3日 下午7:06:27
 * @version v1.0
 */
public class JYLResponse
{
    public JYLResponse()
    {}

    public JYLResponse(String retCode, String retMsg)
    {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    private String retCode = "0";

    private String retMsg = "";

    private Object retObj;

    public String getRetCode()
    {
        return retCode;
    }

    public void setRetCode(String retCode)
    {
        this.retCode = retCode;
    }

    public String getRetMsg()
    {
        return retMsg;
    }

    public void setRetMsg(String retMsg)
    {
        this.retMsg = retMsg;
    }

    public Object getRetObj()
    {
        return retObj;
    }

    public void setRetObj(Object retObj)
    {
        this.retObj = retObj;
    }
}
