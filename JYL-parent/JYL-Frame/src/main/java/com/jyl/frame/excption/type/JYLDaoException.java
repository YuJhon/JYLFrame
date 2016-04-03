package com.jyl.frame.excption.type;

/**
 * <p>功能描述:</br> Dao层出现的异常 </p>
 * @className  JYLDaoException
 * @author  jiangyu
 * @date  2016年4月3日 下午6:35:42
 * @version  v1.0
 */
public class JYLDaoException extends JYLException
{

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private static final long serialVersionUID = -2501816878714603565L;
    
    /**
     * <p>strs代表的是可替换的值 例如：异常信息可以定义为：从一个{0}状态调整到{1}状态出现异常 ,
     * strs中按顺序对应的值分别会替换{0}，{1}中的信息
     * </p>
     * @auther jiangyu
     * @param errorCode 异常码
     * @param strs 异常信息中占位符对应的值
     */
    public JYLDaoException(String errorCode, Object... strs)
    {
        super(errorCode, strs);
    }

}
