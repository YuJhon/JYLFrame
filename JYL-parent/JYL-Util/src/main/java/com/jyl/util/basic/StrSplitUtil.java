package com.jyl.util.basic;


import java.util.StringTokenizer;
import java.util.Vector;

import com.jyl.util.empty.EmptyCheckUtil;


/**
 * <p>功能描述:</br> 字符串分隔工具 </p>
 * 
 * @className StrSplitUtil
 * @author jiangyu
 * @date 2016年4月3日 下午6:01:13
 * @version v1.0
 */
public class StrSplitUtil
{
    public static String SPLIT_PATTERN = ",|;|，|；|(\\n)";

    /**
     * <p> 功能描述：按char型分隔符分割字符,将所给分隔符变成拆分成多个char,然后分别分割</p>
     * @author  jiangyu
     * @date  2016年4月3日 下午6:02:36
     * @param str 原始字符
     * @param spec 分隔 
     * @return 分割后的字符数组
     * @version v1.0
     * @since V1.0
     */
    public static String[] strSplit(String str, String spec)
    {
        return StrSplitUtil.strSplit(str, spec, false);
    }

    /**
     * <p> 功能描述：按char型分隔符分割字符,将所给分隔符变成拆分成多个char,然后分别分割</p>
     * @author  jiangyu
     * @date  2016年4月3日 下午6:02:02
     * @param str 原始字符 
     * @param spec 分隔
     * @param withNull 是否统计空字符串
     * @return 分割后的字符数组
     * @version v1.0
     * @since V1.0
     */
    public static String[] strSplit(String str, String spec, boolean withNull)
    {
        if (EmptyCheckUtil.isEmpty(str))
        {
            return new String[0];
        }
        StringTokenizer token = new StringTokenizer(str, spec);
        int count = token.countTokens();
        Vector<String> vt = new Vector<String>();
        for (int i = 0; i < count; i++ )
        {
            String tmp = token.nextToken();
            if (withNull || !EmptyCheckUtil.isEmpty(tmp))
            {
                vt.addElement(tmp.trim());
            }
        }
        return vt.toArray(new String[0]);
    }
}
