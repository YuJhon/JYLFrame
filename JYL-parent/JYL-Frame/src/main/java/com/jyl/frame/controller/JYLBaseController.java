package com.jyl.frame.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jyl.frame.constants.BaseErrorCode;
import com.jyl.frame.controller.dto.JYLResponse;
import com.jyl.frame.excption.type.JYLSysException;
import com.jyl.frame.excption.type.JYLViewException;
import com.jyl.frame.excption.utils.ErrorCodeUtil;
import com.jyl.frame.resp.ResponseUtil;

/**
 * <p>功能描述:</br> 基础控制器
 * 提供顶层的异常处理能力，封装了异常情况下的返回值</p>
 * @className  JYLBaseController
 * @author  jiangyu
 * @date  2016年4月3日 下午7:08:25
 * @version  v1.0
 */
public class JYLBaseController
{
    private static final Logger LOG = Logger.getLogger(JYLBaseController.class);

    /**
     * <p> 功能描述：对所有经过Controller的异常进行了统一处理
     * 如果抛出的是JYLViewException，向前端返回错误码和错误信息
     * 如果抛出的是JYLSysException，向前端返回系统异常，具体异常信息不可见</p>
     * @author  jiangyu
     * @date  2016年4月3日 下午7:13:13
     * @param request
     * @param response
     * @param ex
     * @return JYLResponse 通用返回值
     * @see JYLResponse
     * @version v1.0
     * @since V1.0
     */
    @ExceptionHandler
    @ResponseBody
    public JYLResponse resolveException(HttpServletRequest request,
                                           HttpServletResponse response, Exception ex)
    {

        JYLResponse responseValue = null;
        if (null != ex && ex instanceof JYLViewException)
        {
            JYLViewException viewEx = (JYLViewException)ex;
            String errCode = viewEx.getErrorCode();
            Object[] replaceVal = viewEx.getReplaceVal();
            LOG.error("errorCode:" + errCode + " errorMsg:" + ErrorCodeUtil.getErrorDesc(errCode,replaceVal));
            responseValue = ResponseUtil.error(errCode,replaceVal);
        }
        else if (null != ex && ex instanceof JYLSysException)
        {
            JYLSysException sysex = (JYLSysException)ex;
            String errCode = sysex.getErrorCode();
            Object[] replaceVal = sysex.getReplaceVal();
            LOG.error("errorCode:" + errCode + " errorMsg:" + ErrorCodeUtil.getErrorDesc(errCode,replaceVal));
            responseValue = ResponseUtil.error(ErrorCodeUtil.SYS_ERROR);
        }
        else
        {
            if (ex != null)
            {   /** get请求方式的时候，如果参数少了要提示异常信息  added by jiangyu **/
                if (ex instanceof MissingServletRequestParameterException)
                {
                    /** eg:Required String parameter 'ip' is not present **/
                    MissingServletRequestParameterException excp = (MissingServletRequestParameterException)ex;
                    String name = excp.getParameterName();
                    String type = excp.getParameterType();
                    Object[] replaceValue = new Object[2];
                    replaceValue[0] = name;
                    replaceValue[1] = type;
                    responseValue = ResponseUtil.error(BaseErrorCode.METHOD_GET_WAY_PARAMETER_EMPTY,replaceValue);
                }else {
                    responseValue = ResponseUtil.error(ErrorCodeUtil.SYS_ERROR);
                }
            }
        }
        LOG.error(ex.getStackTrace(), ex);
        return responseValue;
    }
}
