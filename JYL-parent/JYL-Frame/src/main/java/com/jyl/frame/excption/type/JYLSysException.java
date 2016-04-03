package com.jyl.frame.excption.type;

/**
 * <p>功能描述:</br> 系统异常 </p>
 * @className  JYLSystemException
 * @author  jiangyu
 * @date  2016年4月3日 下午6:34:00
 * @version  v1.0
 */
public class JYLSysException extends JYLException
{
    private static final long serialVersionUID = 1L;
    /**
     * 
     * <p>strs代表的是可替换的值 例如：异常信息可以定义为：从一个{0}状态调整到{1}状态出现异常 ,
     * strs中按顺序对应的值分别会替换{0}，{1}中的信息
     * </p>
     * @auther jiangyu
     * @param errorCode 异常码
     * @param strs 异常信息中占位符对应的值
     */
    public JYLSysException(String errorCode,Object... strs)
    {
        super(errorCode,strs);
    }
}
