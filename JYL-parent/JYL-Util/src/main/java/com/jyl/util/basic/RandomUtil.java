package com.jyl.util.basic;

/**
 * <p>功能描述:</br> 获取随机数</p>
 * 
 * @className RandomUtil
 * @author jiangyu
 * @date 2016年4月3日 下午5:59:50
 * @version v1.0
 */
public class RandomUtil
{
    /**
     * @Description: 随机生成10位邀请码【10位数字或大小写字母】
     * @userName: jiangyu
     * @date: 2015年12月2日 下午5:00:18
     * @return
     */
    public static String randomStr(int length)
    {
        String s = "";
        for (int i = 0; i < length; i++ )
        {
            if (i % 2 == 0 || i % 3 == 0)
            {
                s += getRandomInt(9, 0);
            }
            else
            {
                s += getRandomChar();
            }
        }
        return s;
    }

    /**
     * @Description: 获取随机的字母-包含大小写
     * @userName: jiangyu
     * @date: 2015年12月2日 下午5:07:11
     * @return
     */
    public static char getRandomChar()
    {
        int i = getRandomInt(122, 65);
        if (i > 90 && i < 97)
        {
            return getRandomChar();
        }
        else
        {
            return (char)i;
        }
    }

    /**
     * @Description: 获取随机数
     * @userName: jiangyu
     * @date: 2015年12月2日 下午5:07:37
     * @param max
     *            最大值
     * @param min
     *            最小值
     * @return 返回最大值和最小值之间的随机整数
     */
    public static int getRandomInt(int max, int min)
    {
        return (int)(Math.random() * (max + 1 - min) + min);
    }
}
