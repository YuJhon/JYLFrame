package com.jyl.util.basic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * <p>功能描述:</br> 拷贝的工具类</p>
 * @className  CopyUtil
 * @author  jiangyu
 * @date  2016年4月3日 下午5:58:24
 * @version  v1.0
 */
public class CopyUtil
{
    public static final Logger LOG = Logger.getLogger(CopyUtil.class);

    /**
     * <p> 功能描述：List集合的深复制</p>
     * @author  jiangyu
     * @date  2016年4月3日 下午5:58:36
     * @param src 需要被复制的List集合
     * @return
     * @version v1.0
     * @since V1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> deepCopy(List<T> src)
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out;
        try
        {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            List<T> dest = (List<T>)in.readObject();
            return dest;
        }
        catch (IOException e)
        {
            LOG.error("deep copy List data IO Exception:" + e.getMessage());
            e.printStackTrace();
        }
        catch (ClassNotFoundException e2)
        {
            LOG.error("deep copy List data ClassNotFoundException:" + e2.getMessage());
            e2.printStackTrace();
        }
        return new ArrayList<T>();
    }
}
