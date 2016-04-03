package com.jyl.util.encrypt;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * <p>功能描述</br> MD5加密的工具类</p>
 * 
 * @className MD5Util
 * @author jiangyu
 * @date 2016年3月31日 下午9:50:33
 * @version v1.0
 */
public class MD5Util
{

    /** 全局数组 **/
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5Util()
    {}

    /**
     * <p> 功能描述：返回形式为数字跟字符串</p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:51:23
     * @param bByte
     * @return
     * @version v1.0
     * @since V1.0
     */
    private static String byteToArrayString(byte bByte)
    {
        int iRet = bByte;
        if (iRet < 0)
        {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * <p> 功能描述：转换字节数组为16进制字串</p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:51:41
     * @param bByte
     * @return
     * @version v1.0
     * @since V1.0
     */
    private static String byteToString(byte[] bByte)
    {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++ )
        {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * <p> 功能描述：获得MD5密码</p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:51:52
     * @param strObj
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String getMD5Code(String strObj)
    {
        String resultString = null;
        try
        {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteToString(md.digest(strObj.getBytes()));
        }
        catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        return resultString;
    }
}
