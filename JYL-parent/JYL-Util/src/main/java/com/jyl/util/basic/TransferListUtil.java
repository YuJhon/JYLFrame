package com.jyl.util.basic;


import java.util.ArrayList;
import java.util.List;

import com.jyl.util.empty.EmptyCheckUtil;


/**
 * <p>功能描述:</br> 转换成List的工具类 </p>
 * 
 * @className TransferListUtil
 * @author jiangyu
 * @date 2016年4月3日 下午6:03:50
 * @version v1.0
 */
public class TransferListUtil
{
    /**
     * <p> 功能描述：将数组转换为in字段</p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午6:06:07
     * @param data
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String IntegerArray2InStr(Integer[] data)
    {
        if (data == null)
        {
            return "0";
        }
        String inStr = "";
        for (int i = 0; i < data.length; i++ )
        {
            inStr += data[i] + ",";
        }
        if (inStr.length() > 0)
        {
            inStr = inStr.substring(0, inStr.length() - 1);
        }
        return inStr;
    }

    /**
     * <p> 功能描述： 带逗号分隔的字符串</p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午6:05:52
     * @param data
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String strArray2IntInStr(String[] data)
    {
        if (data == null)
        {
            return "0";
        }
        String inStr = "";
        for (int i = 0; i < data.length; i++ )
        {
            inStr += data[i] + ",";
        }
        if (inStr.length() > 0)
        {
            inStr = inStr.substring(0, inStr.length() - 1);
        }
        return inStr;
    }

    /**
     * <p> 功能描述：将用逗号分隔开的字段串转换成List<Integer></p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午6:05:21
     * @param strSrc
     *            带逗号分隔的字符串
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static List<Integer> StrToIntegerList(String strSrc)
    {
        List<Integer> result = new ArrayList<Integer>();
        if (!EmptyCheckUtil.isEmpty(strSrc))
        {
            String[] strArr = StrSplitUtil.strSplit(strSrc, ",");
            for (String str : strArr)
            {
                result.add(Integer.valueOf(str));
            }
        }
        return result;
    }

    /**
     * <p> 功能描述：str数组转换为List<Integer></p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午6:04:57
     * @param strArr
     *            要转换的字符串数组
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static List<Integer> StrArrToIntegerList(String[] strArr)
    {
        List<Integer> result = new ArrayList<Integer>();
        if (!EmptyCheckUtil.isEmpty(strArr))
        {
            for (String str : strArr)
            {
                result.add(Integer.valueOf(str));
            }
        }
        return result;
    }

    /**
     * <p> 功能描述：将Str数组转换成List<Long></p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午6:04:26
     * @param strArr
     *            要转换的字符串数组
     * @return 转换之后的List<Long>
     * @version v1.0
     * @since V1.0
     */
    public static List<Long> StrArrToLongList(String[] strArr)
    {
        List<Long> result = new ArrayList<Long>();
        if (!EmptyCheckUtil.isEmpty(strArr))
        {
            for (String str : strArr)
            {
                result.add(Long.valueOf(str));
            }
        }
        return result;
    }
}
