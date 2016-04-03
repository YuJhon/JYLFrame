package com.jyl.util.webservice;


import java.net.SocketTimeoutException;

import javax.xml.stream.XMLStreamException;
import javax.xml.ws.http.HTTPException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>功能描述:</br> Cxf Webservice 工具类，远程调用、等</p>
 * 
 * @className CxfWebServiceUtil
 * @author jiangyu
 * @date 2016年4月3日 下午3:55:36
 * @version v1.0
 */
public class CxfWebServiceUtil
{

    private static final Logger logger = LoggerFactory.getLogger(CxfWebServiceUtil.class);

    /**
     * client工厂线程变量
     */
    private static final ThreadLocal<JaxWsDynamicClientFactory> clientFactory = new ThreadLocal<JaxWsDynamicClientFactory>();

    /**
     * 返回client 工厂类（便于非通用client生成与使用）
     * 
     * @return
     */
    public static JaxWsDynamicClientFactory getFactory()
    {
        if (null == clientFactory.get())
        {
            JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
            clientFactory.set(factory);
        }
        return clientFactory.get();
    }

    /**
     * <p> 功能描述：远程调用调用webService接口</p>
     * @author  jiangyu
     * @date  2016年4月3日 下午3:56:09
     * @param wsdlUrl webService wsdl接口
     * @param methodName 远程调用方法名
     * @param args 远程调用方法参数
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static Object[] excuteWebService(String wsdlUrl, String methodName, Object... args)
    {

        Object[] result = null;
        try
        {

            Client client = CxfWebServiceUtil.getFactory().createClient(wsdlUrl);
            result = client.invoke(methodName, args);
        }
        catch (Exception e)
        {
            logger.warn("远程调用webService接口异常,url:" + wsdlUrl + ",方法名：" + methodName + ",参数：" + args + ",异常：" + e);

            Throwable ta = e.getCause();
            if (ta instanceof SocketTimeoutException)
            {
                logger.info("[Exception]socket timeout :"+ta.getMessage());
            }
            else if (ta instanceof HTTPException)
            {
                logger.info("[Exception]服务端地址无效404 :"+ta.getMessage());
            }
            else if (ta instanceof XMLStreamException)
            {
                logger.info("[Exception]parseXMLStream :"+ta.getMessage());
            }
            else
            {
                logger.info("[Exception]Other :"+ta.getMessage());
            }
        }
        return result;
    }

    /**
     * 远程调用调用webService接口
     * 
     * @param wsdlUrl
     *            webService wsdl接口
     * @param methodName
     *            远程调用方法名
     * @param args
     *            远程调用方法参数
     * @param connTimeOut
     *            设置请求超时
     * @param receTimeOut
     *            设置响应超时
     * @return
     */
    public static Object[] excuteWebService(String wsdlUrl, String methodName, long connTimeOut, long receTimeOut, Object... args)
    {

        Object[] result = null;
        try
        {

            Client client = CxfWebServiceUtil.getFactory().createClient(wsdlUrl);

            // 设置超时单位为毫秒
            HTTPConduit http = (HTTPConduit)client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(3000); // 连接超时
            httpClientPolicy.setAllowChunking(false); // 取消块编码
            httpClientPolicy.setReceiveTimeout(3000); // 响应超时
            http.setClient(httpClientPolicy);

            result = client.invoke(methodName, args);
        }
        catch (Exception e)
        {
            logger.warn("远程调用webService接口异常,url:" + wsdlUrl + ",方法名：" + methodName + ",参数：" + args + ",异常：" + e);
        }

        return result;
    }
}
