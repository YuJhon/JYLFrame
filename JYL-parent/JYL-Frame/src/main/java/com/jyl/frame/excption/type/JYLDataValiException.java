package com.jyl.frame.excption.type;

/**
 * <p>功能描述:</br> 数据校验的异常 </p>
 * 
 * @className JYLDataValiException
 * @author jiangyu
 * @date 2016年4月3日 下午6:37:28
 * @version v1.0
 */
public class JYLDataValiException extends JYLException
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = 4651893168817028609L;

    /**
     * <p>strs代表的是可替换的值 例如：异常信息可以定义为：从一个{0}状态调整到{1}状态出现异常 ,strs中按顺序对应的值分别会替换{0}，{1}中的信息</p>
     * 
     * @author jiangyu
     * @param errorCode
     * @param str
     */
    public JYLDataValiException(String errorCode, Object... str)
    {
        super(errorCode, str);
    }

}
