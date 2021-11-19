package com.jg.pochi.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author 杨德石
 */
public final class DateUtils {

    private DateUtils() {
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取指定天数之前的日期
     *
     * @param index 天数
     * @return 日期
     */
    public static String getday(int index) {
        // 获取日历类
        Calendar calendar = Calendar.getInstance();
        // 获取指定天数前的日期
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - index);
        Date today = calendar.getTime();
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(today);
        return day;
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static long nowUnix() {
        return System.currentTimeMillis();
    }

    /**
     * 默认日期格式
     *
     * @return
     */
    public static String newDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 没有-的日期
     *
     * @return
     */
    public static String newDateTimeNoChar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 年月日
     *
     * @return
     */
    public static String newDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String newMonth() {
        return new SimpleDateFormat("yyyy-MM").format(new Date());
    }

    /**
     * 计算日期相差天数 整数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static String diffTime(String startDate, String endDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long between = sdf.parse(endDate).getTime() - sdf.parse(startDate).getTime();
        // 转换为秒
        between /= 1000;
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minute = between % 3600 / 60;
        long second = between % 60;

        // 天转成小时
        hour += day * 24;

        String result = "";
        if (hour != 0) {
            result += hour + "小时";
        }
        if (minute != 0) {
            result += minute + "分";
        }
        if (second != 0) {
            result += second + "秒";
        }

        return result;
    }

    /**
     * 计算日期时间相差天数 浮点数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static double diffDoubleTime(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long a = 0;
        try {
            a = sdf.parse(endDate).getTime() - sdf.parse(startDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return a / 1000.0 / 60 / 60 / 24;
    }

    /**
     * 计算日期相差天数 浮点数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static double diffDoubleDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long a = 0;
        try {
            a = sdf.parse(endDate).getTime() - sdf.parse(startDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return a / 1000.0 / 60 / 60 / 24;
    }

    /**
     * 指定日期字符串加上 天数
     *
     * @param date
     * @param day
     * @return
     * @throws ParseException
     */
    public static String addDate(String date, int day) {
        long time = 0;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long second = day * 24 * 60 * 60 * 1000;
        time += second;
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

    /**
     * 判断是否比当前时间大
     *
     * @return
     */
    public static boolean bigThanNow(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(date);
            Date dt2 = df.parse(newDateTime());
            return dt1.getTime() > dt2.getTime();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * 比较日期大小
     *
     * @param left
     * @param right
     * @return
     */
    public static Integer compareDate(String left, String right) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return df.parse(left).compareTo(df.parse(right));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取到第二天凌晨的毫秒
     *
     * @return
     */
    public static long getMilliSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        // 改成这样就好了
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() - System.currentTimeMillis();
    }

    /**
     * 获取当前时间（不带符号）
     */
    public static String getNowDateTimeNoChar() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 获取指定月份的整月日期列表
     *
     * @param date
     * @return {@link {@link List< String>}}
     * @throws
     * @author 杨德石
     * @date 2020/7/29 19:20
     */
    public static List<String> getDayByMonth(String date) {
        List<String> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.getActualMaximum(Calendar.DATE);
        String nowDay = newDate();
        for (int i = 1; i <= day; i++) {
            String aDate = null;
            if (month < 10 && i < 10) {
                aDate = year + "-0" + month + "-0" + i;
            }
            if (month < 10 && i >= 10) {
                aDate = year + "-0" + month + "-" + i;
            }
            if (month >= 10 && i < 10) {
                aDate = year + "-" + month + "-0" + i;
            }
            if (month >= 10 && i >= 10) {
                aDate = year + "-" + month + "-" + i;
            }
            list.add(aDate);
            if (nowDay.equals(aDate)) {
                break;
            }
        }
        return list;
    }

    /**
     * 根据所给的时间区间，获取指定月份的所有天数
     * 如给定月份2020-07，startDate：2020-06-15 endDate：2020-07-26
     * 该方法会获取2020-07-01~2020-07-26的日期
     *
     * @param month     月份
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return {@link {@link List< String>}}
     * @throws
     * @author 杨德石
     * @date 2020/7/30 17:16
     */
    public static List<String> getDaySectionByMonth(String month, String startDate, String endDate) {
        List<String> monthList = getDayByMonth(month);
        String monthStart = monthList.get(0);
        String monthEnd = monthList.get(monthList.size() - 1);
        if (diffDoubleDate(monthStart, startDate) < 0) {
            startDate = monthStart;
        }
        if (diffDoubleDate(monthEnd, endDate) > 0) {
            endDate = monthEnd;
        }
        return getDayBySection(startDate, endDate);
    }

    /**
     * 返回指定的时间段内的所有日期
     *
     * @param startDate 开始时间,例:2016-02-25
     * @param endDate   结束时间,例:2016-03-05
     * @return
     * @throws
     * @author 杨德石
     * @date 2020/7/30 16:55
     */
    public static List<String> getDayBySection(String startDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newstartDate = null;
        Date newendDate = null;
        try {
            newstartDate = simpleDateFormat.parse(startDate);
            newendDate = simpleDateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> dateList = new ArrayList<>();
        long spi = newendDate.getTime() - newstartDate.getTime();
        long step = spi / (24 * 60 * 60 * 1000);
        List<Date> newdateList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        newdateList.add(newstartDate);
        dateList.add(startDate);
        for (int i = 1; i <= step; i++) {
            dateList.add(formatter.format(new Date(newdateList.get(i - 1).getTime() + (24 * 60 * 60 * 1000))));
            newdateList.add(new Date(newdateList.get(i - 1).getTime() + (24 * 60 * 60 * 1000)));
        }
        return dateList;
    }

}
