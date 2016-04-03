package com.jyl.util.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>功能描述:</br> 常用的正则表达式 </p>
 * @className  RegexUtil
 * @author  jiangyu
 * @date  2016年4月3日 下午6:10:54
 * @version  v1.0
 */
public class RegexUtil
{
    /**
     * @Description: 判断是否是合法的IP地址
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:46:54
     * @param ip
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIP(String ip)
    {
        if (null == ip ||"".equals(ip))
        {
            return false;
        }
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ip.matches(regex);
    }

    /**
     * @Description: 判断是否是合法的邮箱地址
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:48:14
     * @param email
     * @return boolean true,通过，false，没通过
     */
    public static boolean isEmail(String email)
    {
        if (null == email || "".equals(email))
            return false;
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }

    /**
     * @Description: 判断是否含有中文，仅适合中国汉字，不包括标点
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:49:10
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChinese(String text)
    {
        if (null == text || "".equals(text))
            return false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(text);
        return m.find();
    }

    /**
     * @Description: 判断是否非负数
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:50:05
     * @return boolean true,通过，false，没通过
     */
    public static boolean isNumber(String number)
    {
        if (null == number || "".equals(number))
            return false;
        String regex = "[0-9]*";
        return number.matches(regex);
    }

    /**
     * @Description: 判断几位小数(正数)
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:51:39
     * @param decimal
     *            数字
     * @param count
     *            小数位
     * @return boolean true,通过，false，没通过
     */
    public static boolean isDecimal(String decimal, int count)
    {
        if (null == decimal || "".equals(decimal))
            return false;
        String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count
                + "})?$";
        return decimal.matches(regex);
    }

    /**
     * @Description: 判断是否是手机号码
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:52:51
     * @param phone
     *            手机号码
     * @return boolean true,通过，false，没通过
     */
    public static boolean isPhone(String phone)
    {
        if (null == phone || "".equals(phone))
            return false;
        String regex = "^1[3|4|5|8][0-9]\\d{8}$";
        return phone.matches(regex);
    }

    /**
     * @Description: 判断是否包含特殊字符
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:53:49
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean hasSpecialChar(String text)
    {
        if (null == text || "".equals(text))
            return false;
        if (text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
            // 如果不包含特殊字符
            return true;
        }
        return false;
    }

    /**
     * @Description: 适应CJK（中日韩）字符集，部分中日韩的字是一样的
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:54:53
     * @param strName
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChineseCJK(String strName)
    {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description: 判断单个字符是否是中文
     * @userName: jiangyu
     * @date: 2016年2月18日 上午11:56:06
     * @param c
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChinese(char c)
    {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}
