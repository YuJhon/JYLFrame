package com.jyl.util.time;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jyl.util.empty.EmptyCheckUtil;


/**
 * <p> 功能描述:</br> 时间处理的工具类 </p>
 * 
 * @className TimeUtil
 * @author jiangyu
 * @date 2016年3月31日 下午9:52:57
 * @version v1.0
 */
public class TimeUtil
{
    /** 转换的日期格式 **/
    public static String PATTERN_DATE_1 = "yyyy-MM-dd";

    public static String PATTERN_DATE_2 = "yyyyMMdd";

    public static String PATTERN_DATE_3 = "yyyyMMddhhMMss";

    public static String PATTERN_DATE_4 = "yyyy-MM-dd HH:mm:ss:SSS";

    public static String PATTERN_DATE_5 = "yyyy年MM月dd日";

    public static String PATTERN_DATE_6 = "yyyy-MM-dd HH:mm";

    public static String PATTERN_DATE_7 = "yyyy-MM-dd HH:mm:ss";

    public static String PATTERN_DATE_8 = "yyyy-MM-dd HH";

    public static String PATTERN_DATE_9 = "MMdd";

    public static String PATTERN_TIME_1 = "HH:mm";

    public static String PATTERN_YEAR_1 = "yyyy";

    public static String STARTTIME_SUFFIX = " 00:00:00";

    public static String ENDTIME_SUFFIX = " 23:59:59";

    /**
     * <p> 功能描述：将字符串时间转换为date类型的时间 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:53:16
     * @param dateTime
     * @param pattern
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static Date parseDate(String dateTime, String pattern)
    {
        Date date = null;
        if (!EmptyCheckUtil.isEmpty(dateTime))
        {
            try
            {
                date = new SimpleDateFormat(pattern).parse(dateTime);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        return date;
    }

    /**
     * <p> 功能描述：获取默认的当前时间 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:53:29
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String getDefaultCurrentDateTime()
    {
        return getTimeStr(new Date(), TimeUtil.PATTERN_DATE_7);
    }

    /**
     * <p> 功能描述：将时间转为指定格式的字符串 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:53:40
     * @param date
     * @param pattern
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String getTimeStr(Date date, String pattern)
    {
        if (null == date)
        {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * <p> 功能描述：获取特定格式的当前时间 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:53:54
     * @param pattern
     *            时间格式
     * @return 转换格式后的日期时间字符串
     * @version v1.0
     * @since V1.0
     */
    public static String getTimeStr(String pattern)
    {
        return getTimeStr(new Date(), pattern);
    }

    /**
     * <p> 功能描述：将日期转换为字符串 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:54:22
     * @param date
     *            要转换的日期
     * @param patten
     *            时间格式
     * @return 转换格式后的日期时间字符串
     * @version v1.0
     * @since V1.0
     */
    public static String Date2Sring(Date date, String patten)
    {
        if (EmptyCheckUtil.isEmpty(date))
        {
            date = new Date();
        }
        if (EmptyCheckUtil.isEmpty(patten))
        {
            patten = PATTERN_DATE_1;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        return sdf.format(date);
    }

    /**
     * <p> 功能描述：获得两个日期之间相差的天数 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:54:52
     * @param time1
     *            开始时间
     * @param time2
     *            结束时间
     * @param patten
     *            两个日期之间相差的天数
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static long getQuot(String time1, String time2, String patten)
    {
        long quot = 0;
        SimpleDateFormat ft = new SimpleDateFormat(patten);
        try
        {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);

            quot = date2.getTime() - date1.getTime();
            quot = quot / 1000 / 60 / 60 / 24;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return quot;
    }

    /**
     * <p> 功能描述：将Timestamp时间转为指定格式的字符串 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:55:29
     * @param timestamp
     * @param pattern
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String getTimestampStr(Timestamp timestamp, String pattern)
    {
        if (null == timestamp)
        {
            return "";
        }

        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(timestamp.getTime());
        return new SimpleDateFormat(pattern).format(c.getTime());
    }

    /**
     * <p> 功能描述：字符串转换时间 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:55:40
     * @param string
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static Date getTimestampDateFromString(String string)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE_4);
        try
        {
            Date date = dateFormat.parse(string);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        }
        catch (ParseException e)
        {}
        return new Date();
    }

    /**
     * <p> 功能描述：按照特定格式将字符串转换为日期 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:55:50
     * @param string
     * @param format
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static Date getTimestampDateFromString(String string, String format)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try
        {
            Date date = dateFormat.parse(string);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp;
        }
        catch (ParseException e)
        {}
        return new Date();
    }

    /**
     * <p> 功能描述：获取某年某月最大天数 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:55:59
     * @param year
     *            年
     * @param month
     *            月
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static int getMaxDay(String year, String month)
    {
        // 计算某一月份的最大天数
        Calendar time = Calendar.getInstance();
        time.clear(); // 若不clear，很多信息会继承自系统当前时间
        time.set(Calendar.YEAR, Integer.valueOf(year));
        time.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH); // 本月总天数
        return day;
    }

    public static Timestamp getTimeStamp(String timeStr)
    {
        Date date = getTimestampDateFromString(timeStr);
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }

    public static Timestamp getTimeStamp(Date date)
    {
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }

    /**
     * <p> 功能描述：计算传入时间与当前时间的相隔天数 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:56:23
     * @param timestamp
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static long getDays(Timestamp timestamp)
    {
        long currentTime = System.currentTimeMillis();
        long temp = timestamp.getTime();
        long dayslong = currentTime - temp;
        long days = (dayslong >> 10) / 84375;
        long l_days = (long)days;
        return l_days;
    }

    public static Timestamp getTimeStamp(String timeStr, String pattern)
    {
        DateFormat format = new SimpleDateFormat(pattern);
        format.setLenient(false);
        try
        {
            Timestamp ts = new Timestamp(format.parse(timeStr).getTime());
            return ts;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromString(String date, String fmt)
        throws Exception
    {
        if (date == null || date.trim().length() == 0) return null;
        DateFormat df = new SimpleDateFormat(fmt);
        return df.parse(date);
    }

    /**
     * <p> 功能描述：将字符串转换为时间 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:56:41
     * @param str
     * @param pattern
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static Date getDateFormatString(String str, String pattern)
    {
        if (str != null && !str.trim().equals(""))
        {
            try
            {
                return new SimpleDateFormat(pattern).parse(str);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                return null;
            }
        }
        else
        {
            return null;
        }

    }

    /**
     * <p> 功能描述：获取两个时间差(格式:XX天XX小时XX分XX秒) </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:56:52
     * @param startDate
     * @param endDate
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String getTwoDateDiff(Date startDate, Date endDate)
    {
        long l = endDate.getTime() - startDate.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sed = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return "" + day + "天" + hour + "小时" + min + "分" + sed + "秒";

    }

    /**
     * <p> 功能描述：获取两个时间的间隔天数 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:57:04
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return Long 时间间隔的天数
     * @version v1.0
     * @since V1.0
     */
    public static Long getTwoDay(Date startDate, Date endDate)
    {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String endTempDate = myFormatter.format(endDate.clone());
        String startTempDate = myFormatter.format(startDate.clone());
        try
        {
            return ((myFormatter.parse(endTempDate).getTime()) - (myFormatter.parse(startTempDate).getTime())) / (24 * 60 * 60 * 1000);
        }
        catch (ParseException e)
        {
            return -1L;
        }
    }

    /**
     * <p> 功能描述：获取当前日期的开始时间可结束时间 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:58:07
     * @return List<Date> 当前日期的开始时间可结束时间
     * @version v1.0
     * @since V1.0
     */
    public static List<Date> getBeginAndEndDate()
    {
        List<Date> preDates = new ArrayList<Date>();
        preDates.add(getTodayBeginTime());
        preDates.add(getTodayEndTime());
        return preDates;
    }

    /**
     * <p> 功能描述：获取今天的开始时间 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:58:17
     * @return Date 今天的开始时间
     * @version v1.0
     * @since V1.0
     */
    public static Date getTodayBeginTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE_7);
        Date begin = null;
        try
        {
            String baseTimeStr = getBaseDateStr();
            String beginTime = baseTimeStr + STARTTIME_SUFFIX;
            begin = sdf.parse(beginTime);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return begin;
    }

    /**
     * <p> 功能描述：获取今天的结束时间 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:58:28
     * @return Date 获取今天的结束时间
     * @version v1.0
     * @since V1.0
     */
    public static Date getTodayEndTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE_7);
        Date end = null;
        try
        {
            String baseTimeStr = getBaseDateStr();
            String endTime = baseTimeStr + ENDTIME_SUFFIX;
            end = sdf.parse(endTime);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return end;
    }

    /**
     * <p> 功能描述：获取当前日期 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:58:38
     * @return String 默认日期格式格式化后的日期时间字符串
     * @version v1.0
     * @since V1.0
     */
    public static String getBaseDateStr()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE_1);
        return sdf.format(new Date());
    }

    public static String getBaseDateStr(String patten)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        return sdf.format(new Date());
    }

    /**
     * <p> 功能描述：获取日期的星期几 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:58:52
     * @param date 日期
     * @return Integer 日期的星期几
     * @version v1.0
     * @since V1.0
     */
    public static String getDateOfWeek(Date date)
    {
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;
        return weekDays[w];
    }

    /**
     * <p> 功能描述：日期的天 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:59:03
     * @param date 日期
     * @return Integer 日期的天
     * @version v1.0
     * @since V1.0
     */
    public static Integer getDateOfDay(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * <p> 功能描述：日期的月 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:59:17
     * @param date 日期
     * @return Integer 日期的月
     * @version v1.0
     * @since V1.0
     */
    public static Integer getDateOfMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * <p> 功能描述：日期中天的尾数 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:59:27
     * @param date 日期
     * @return Integer 日期中天的尾数 
     * @version v1.0
     * @since V1.0
     */
    public static Integer getDayOfSuffix(Date date)
    {
        int day = getDateOfDay(date);
        return day % 10;
    }

    /**
     * <p> 功能描述：获取当前日期是星期几 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:59:38
     * @return
     * @version v1.0
     * @since V1.0
     */
    public static String getCurrentDateOfWeek()
    {
        Date date = new Date();
        return getDateOfWeek(date);
    }

    /**
     * <p> 功能描述：获取当前日期的天 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午9:59:49
     * @return Integer 当前日期的天
     * @version v1.0
     * @since V1.0
     */
    public static Integer getCurrentDateOfDay()
    {
        Date date = new Date();
        return getDateOfDay(date);
    }

    /**
     * <p> 功能描述： 获取当前日期的月 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午10:00:07
     * @return Integer 当前日期的月
     * @version v1.0
     * @since V1.0
     */
    public static Integer getCurrentDateOfMonth()
    {
        Date date = new Date();
        return getDateOfMonth(date);
    }

    /**
     * <p> 功能描述：获取当前日期中天的尾数 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午10:00:20
     * @return Integer 当前日期中天的尾数
     * @version v1.0
     * @since V1.0
     */
    public static Integer getCurrentDayOfSuffix()
    {
        Date date = new Date();
        return getDayOfSuffix(date);
    }

    /**
     * <p> 功能描述：获取当前的年份 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午10:00:31
     * @return String 当前的年份
     * @version v1.0
     * @since V1.0
     */
    public static String getCurrentYear()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year + "";
    }

    /**
     * <p> 功能描述：校验时间是否过期 </p>
     * 
     * @author jiangyu
     * @date 2016年3月31日 下午10:00:44
     * @param compareDate   需要对比的时间
     * @return boolean true-过期； false-没有过期
     * @version v1.0
     * @since V1.0
     */
    public static boolean validateTimeIsExpired(Date compareDate)
    {
        if (compareDate != null)
        {
            return compareDate.getTime() < System.currentTimeMillis();
        }
        else
        {
            return false;
        }
    }

}
