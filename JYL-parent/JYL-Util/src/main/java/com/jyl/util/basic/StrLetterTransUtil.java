package com.jyl.util.basic;

import com.jyl.util.empty.EmptyCheckUtil;

/**
 * <p>功能描述:</br>  字符串字母转换工具类</p>
 * @className  StrLetterTransUtil
 * @author  jiangyu
 * @date  2016年4月3日 下午6:07:32
 * @version  v1.0
 */
public class StrLetterTransUtil
{
    /**
     * <p> 功能描述：初始化首字母</p>
     * @author  jiangyu
     * @date  2016年4月3日 下午6:07:45
     * @param src 目的的字符串
     * @return 转换后的字符串
     * @version v1.0
     * @since V1.0
     */
    public static String initCap(String src)
    {
        StringBuffer dest = new StringBuffer("");
        if (!EmptyCheckUtil.isEmpty(src))
        {
            dest.append(src.substring(0, 1).toUpperCase()).append(src.substring(1).toLowerCase());
        }
        return dest.toString();
    }
    
    
    /**
     * <p> 功能描述：数据库带分隔线的字段名称转换成实体的字段名</p>
     * @author  jiangyu
     * @date  2016年4月3日 下午6:08:18
     * @param fieldName 要转换的字段名称
     * @param isTableName
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String dbField2BeanField(String fieldName,boolean isTableName){
        while(fieldName.contains("_")){
            int i = fieldName.indexOf("_");
            if(i+1<fieldName.length()){
                char c = fieldName.charAt(i+1);
                String temp = (c+"").toUpperCase();
                fieldName = fieldName.replace("_"+c, temp);
            }
        }
        /** 如果是表名转换为实体名的话就需要将首字母大写 **/
        if(isTableName){
            fieldName = fieldName.substring(0,1).toUpperCase().concat(fieldName.substring(1));
        }
        return fieldName;
    }
}
