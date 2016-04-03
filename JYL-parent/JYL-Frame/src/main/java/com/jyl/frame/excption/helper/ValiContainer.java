package com.jyl.frame.excption.helper;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.jyl.frame.excption.enums.ExcpTypeEnum;
import com.jyl.frame.excption.helper.ExcpHelper.Condition;
import com.jyl.frame.excption.type.JYLDaoException;
import com.jyl.frame.excption.type.JYLSysException;
import com.jyl.frame.excption.utils.ExcpResultUtil;
import com.jyl.util.empty.EmptyCheckUtil;

/**
 * <p>功能描述</br> 校验容器类</p>
 * @className ValiContainer
 * @author jiangyu
 * @date 2016年3月17日 下午7:00:00
 * @version v1.0
 * @since v1.0
 */
public class ValiContainer
{
    /** 无参构造器  **/
    private ValiContainer()
    {}

    private static class ValiContainerHolder
    {
        private static final ValiContainer INSTANCE = new ValiContainer();
    }

    /**
     * <p> 功能描述：获取校验的容器实例</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:22:23
     * @return ValiContainer 获取校验的容器实例
     * @since V1.0
     */
    public static ValiContainer getInstance()
    {
        return ValiContainerHolder.INSTANCE.clear();
    }

    /**
     * 存储校验对象
     */
    private static Set<ExcpHelper> exceptions = Collections.synchronizedSet(new HashSet<ExcpHelper>());

    /**
     * <p> 功能描述：注册校验的对象</p>
     * @author jiangyu
     * @date 2016年3月17日 下午7:00:13
     * @param excp 异常校验对象
     * @return ValiContainer 校验的容器类
     * @since v1.0
     */
    public synchronized ValiContainer registerExcep(ExcpHelper excp)
    {
        exceptions.add(excp);
        return this;
    }
    /**
     * <p> 功能描述：注册校验对象</p>
     * @author jiangyu
     * @date 2016年3月17日 下午7:00:32
     * @param obj 校验的内容
     * @param errorCode 校验不通过的异常编码
     * @param condition 校验的条件（校验的标准）
     * @return ValiContainer 校验的容器类
     * @since v1.0
     */
    public synchronized ValiContainer registerExcep(Object obj, String errorCode, Condition condition)
    {
        ExcpHelper excp = new ExcpHelper(obj, errorCode, condition);
        exceptions.add(excp);
        return this;
    }
    
    public synchronized ValiContainer registerExcep(Object obj, String errorCode, Condition condition,Object ... objs)
    {
        ExcpHelper excp = new ExcpHelper(obj, errorCode, condition,objs);
        exceptions.add(excp);
        return this;
    }
    
    public synchronized ValiContainer clear()
    {
        exceptions.clear();
        return this;
    }

    /**
     * <p> 功能描述：校验异常对象</p>
     * @author jiangyu
     * @date 2016年3月17日 下午7:00:59
     * @param excpType
     * @since v1.0
     */
    public synchronized void validate(ExcpTypeEnum ...excpType)
    {

        if (exceptions != null && exceptions.size() > 0)
        {
            for (ExcpHelper excp : exceptions)
            {
                boolean flag = false;
                if (Condition.EMPTY.getValue().equals(excp.getCondition().getValue()))
                {
                    if (EmptyCheckUtil.isEmpty(excp.getObj()))
                    {
                        flag = true;
                    }
                }
                else if (Condition.LE_0.getValue().equals(excp.getCondition().getValue()))
                {
                    if ((int)excp.getObj()<=0)
                    {
                        flag = true;
                    }
                }
                else if (Condition.NOT_MATCH.getValue().equals(excp.getCondition().getValue()))
                {
                    if ((boolean)excp.getObj())
                    {
                        flag = true;
                    }
                }
                
                if (flag)
                {
                    validateExcpHandler(excp, excpType);
                }
            }
        }
    }
    /**
     * <p> 功能描述：校验的时候异常信息的出来</p>
     * @author jiangyu
     * @date 2016年3月17日 下午7:01:14
     * @param excp
     * @param excpType
     * @since v1.0
     */
    private void validateExcpHandler(ExcpHelper excp, ExcpTypeEnum... excpType)
    {
        if (excpType!=null&&excpType.length>0)
        {
            ExcpTypeEnum type = excpType[0];
            
            String codeCode = type.getCode();
            /** 错误编码  **/
            if (ExcpTypeEnum.CONTROLLER.getCode().equals(codeCode))
            {
                /**控制器层校验抛出异常 **/
                throw new JYLSysException(excp.getErrorCode(),excp.getParamInfo());
                
            }else if (ExcpTypeEnum.SERVICE.getCode().equals(codeCode))
            {
                /**控制器层校验抛出异常 **/
                throw new JYLSysException(excp.getErrorCode(),excp.getParamInfo());
                
            }else if (ExcpTypeEnum.DAO.getCode().equals(codeCode))
            {
                /**控制器层校验抛出异常 **/
                throw new JYLDaoException(excp.getErrorCode(),excp.getParamInfo());
                
            }else if (ExcpTypeEnum.COMPONENT.getCode().equals(codeCode))
            {
                /**控制器层校验抛出异常 **/
                throw new JYLSysException(excp.getErrorCode(),excp.getParamInfo());
                
            }else {
                
                throw new JYLSysException(excp.getErrorCode(),excp.getParamInfo());
            }
        }else {
            /**默认没有传入校验类型则为dao层校验抛出异常 **/
            throw new JYLDaoException(excp.getErrorCode(),excp.getParamInfo());
        }
    }

    /**
     * <p> 功能描述：抛出dao的异常</p>
     * @author jiangyu
     * @date 2016年3月17日 下午7:01:43
     * @param errorCode
     * @since v1.0
     */
    public void throwDaoExcp(String errorCode)
    {
        throw new JYLDaoException(errorCode);
    }
    
    /**
     * <p> 功能描述：抛出dao的异常</p>
     * @author jiangyu
     * @date 2016年3月17日 下午7:02:09
     * @param errorCode
     * @since v1.0
     */
    public void throwSysExcp(String errorCode)
    {
        throw new JYLSysException(errorCode);
    }
    
    /**
     * <p> 功能描述：为空的判断，为空的话抛出可视化的异常信息</p>
     * @author jiangyu
     * @date 2016年3月17日 下午7:02:33
     * @param obj 校验的对象
     * @param errorCode 对应错误异常的异常编码
     * @since v1.0
     */
    public void validateEmpty(Object obj, String errorCode)
    {
        /** 校验是否存在记录 **/
        if (EmptyCheckUtil.isEmpty(obj))
        {
            /** 任务实例不存在 **/
            ExcpResultUtil.failView(errorCode);
        }
    }
}
