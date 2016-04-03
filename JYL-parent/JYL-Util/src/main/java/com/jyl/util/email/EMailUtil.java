package com.jyl.util.email;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;


/**
 * <p>功能描述:</br> 邮件工具类</p>
 * 【说明：未完待补充】
 * @className EMailUtil
 * @author jiangyu
 * @date 2016年4月3日 下午4:32:03
 * @version v1.0
 */
public class EMailUtil
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EMailUtil.class);

    private static final String from = "zhangdh@163.com";

    private static final String fromName = "测试公司";

    private static final String charSet = "utf-8";

    private static final String username = "zhangdh@163.com";

    private static final String password = "123456";

    private static Map<String, String> hostMap = new HashMap<String, String>();

    /** 初始化邮件类型 **/
    static
    {
        /** 基本信息的初始化可以放在此处 (配置文件中获取) **/

        hostMap.put("smtp.126", "smtp.126.com");
        hostMap.put("smtp.qq", "smtp.qq.com");
        hostMap.put("smtp.163", "smtp.163.com");
        hostMap.put("smtp.sina", "smtp.sina.com.cn");
        hostMap.put("smtp.tom", "smtp.tom.com");
        hostMap.put("smtp.263", "smtp.263.net");
        hostMap.put("smtp.yahoo", "smtp.mail.yahoo.com");
        hostMap.put("smtp.hotmail", "smtp.live.com");
        hostMap.put("smtp.gmail", "smtp.gmail.com");
        hostMap.put("smtp.port.gmail", "465");
    }

    /**
     * <p> 功能描述：获取邮件服务器</p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午4:10:02
     * @param email
     * @return
     * @throws Exception
     * @version v1.0
     * @since V1.0
     */
    public static String getHost(String email)
    {
        Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(email);
        String key = "unSupportEmail";
        if (matcher.find())
        {
            key = "smtp." + matcher.group(1);
        }
        if (hostMap.containsKey(key))
        {
            return hostMap.get(key);
        }
        else
        {
            throw new RuntimeException("unSupportEmail");
        }
    }

    /**
     * <p> 功能描述：获取端口号</p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午4:11:44
     * @param email
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static int getSmtpPort(String email)
    {
        Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(email);
        String key = "unSupportEmail";
        if (matcher.find())
        {
            key = "smtp.port." + matcher.group(1);
        }
        if (hostMap.containsKey(key))
        {
            return Integer.parseInt(hostMap.get(key));
        }
        else
        {
            /** 默认是25 **/
            return 25;
        }
    }

    /**
     * <p> 功能描述：发送模板邮件</p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午4:19:07
     * @param toMailAddr
     *            收信人地址
     * @param subject
     *            email主题
     * @param templatePath
     *            模板地址
     * @param map
     *            模板map
     * @version v1.0
     * @since V1.0
     */
    public static void sendFtlMail(String toMailAddr, String subject, String templatePath, Map<String, Object> map)
    {
        Template template = null;
        Configuration freeMarkerConfig = null;
        HtmlEmail hemail = new HtmlEmail();
        hemail.setHostName(getHost(from));
        hemail.setSmtpPort(getSmtpPort(from));
        hemail.setCharset(charSet);
        try
        {
            hemail.addTo(toMailAddr);
            hemail.setFrom(from, fromName);
            hemail.setAuthentication(username, password);
            hemail.setSubject(subject);
            freeMarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            freeMarkerConfig.setDirectoryForTemplateLoading(new File(getFilePath()));

            /** 获取模板 **/
            template = freeMarkerConfig.getTemplate(getFileName(templatePath), new Locale("Zh_cn"), "UTF-8");
            /** 模板内容转换为string **/
            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            hemail.setMsg(htmlText);
            hemail.send();
        }
        catch (EmailException e)
        {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
        }
        catch (TemplateException e)
        {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
        }
        catch (TemplateNotFoundException e)
        {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
        }
        catch (MalformedTemplateNameException e)
        {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * <p> 功能描述：发送普通邮件</p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午4:30:08
     * @param toMailAddr
     *            收信人地址
     * @param subject
     *            email主题
     * @param message
     *            发送email信息
     * @version v1.0
     * @since V1.0
     */
    public static void sendCommonMail(String toMailAddr, String subject, String message)
    {
        HtmlEmail hemail = new HtmlEmail();
        try
        {
            hemail.setHostName(getHost(from));
            hemail.setSmtpPort(getSmtpPort(from));
            hemail.setCharset(charSet);
            hemail.addTo(toMailAddr);
            hemail.setFrom(from, fromName);
            hemail.setAuthentication(username, password);
            hemail.setSubject(subject);
            hemail.setMsg(message);
            hemail.send();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static String getHtmlText(String templatePath, Map<String, Object> map)
    {
        Template template = null;
        String htmlText = "";
        try
        {
            Configuration freeMarkerConfig = null;
            freeMarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            freeMarkerConfig.setDirectoryForTemplateLoading(new File(getFilePath()));
            // 获取模板
            template = freeMarkerConfig.getTemplate(getFileName(templatePath), new Locale("Zh_cn"), "UTF-8");
            // 模板内容转换为string
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return htmlText;
    }

    private static String getFilePath()
    {
        String path = getAppPath(EMailUtil.class);
        path = path + File.separator + "mailtemplate" + File.separator;
        path = path.replace("\\", "/");
        return path;
    }

    private static String getFileName(String path)
    {
        path = path.replace("\\", "/");
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * <p> 功能描述：获取应用的路径</p>
     * 
     * @author jiangyu
     * @date 2016年4月3日 下午4:36:56
     * @param cls
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String getAppPath(Class<?> cls)
    {
        // 检查用户传入的参数是否为空
        if (cls == null) throw new java.lang.IllegalArgumentException("参数不能为空！");
        ClassLoader loader = cls.getClassLoader();
        // 获得类的全名，包括包名
        String clsName = cls.getName() + ".class";
        // 获得传入参数所在的包
        Package pack = cls.getPackage();
        String path = "";
        // 如果不是匿名包，将包名转化为路径
        if (pack != null)
        {
            String packName = pack.getName();
            // 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
            if (packName.startsWith("java.") || packName.startsWith("javax.")) throw new java.lang.IllegalArgumentException("不要传送系统类！");
            // 在类的名称中，去掉包名的部分，获得类的文件名
            clsName = clsName.substring(packName.length() + 1);
            // 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
            if (packName.indexOf(".") < 0)
                path = packName + "/";
            else
            {// 否则按照包名的组成部分，将包名转换为路径
                int start = 0, end = 0;
                end = packName.indexOf(".");
                while (end != -1)
                {
                    path = path + packName.substring(start, end) + "/";
                    start = end + 1;
                    end = packName.indexOf(".", start);
                }
                path = path + packName.substring(start) + "/";
            }
        }
        // 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
        java.net.URL url = loader.getResource(path + clsName);
        // 从URL对象中获取路径信息
        String realPath = url.getPath();
        // 去掉路径信息中的协议名"file:"
        int pos = realPath.indexOf("file:");
        if (pos > -1) realPath = realPath.substring(pos + 5);
        // 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
        pos = realPath.indexOf(path + clsName);
        realPath = realPath.substring(0, pos - 1);
        // 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
        if (realPath.endsWith("!")) realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        /*------------------------------------------------------------ 
         ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径 
          中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要 
          的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的 
          中文及空格路径 
        -------------------------------------------------------------*/
        try
        {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return realPath;
    }

    public static void main(String[] args)
    {
        System.out.println(EMailUtil.getAppPath(EMailUtil.class));
    }

}