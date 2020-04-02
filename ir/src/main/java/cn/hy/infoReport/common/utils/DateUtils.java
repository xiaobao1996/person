package cn.hy.infoReport.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {

    /**
     * 获取当前日期 '/' 级联格式的路径（例如：/2018/03/12）
     * @return
     */
    public static String getDateDirPath() {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("/yyyy/MM/dd");
        return dateFormat.format(now);
    }

    /**
     * 在当前年月日的基础上加一个月  (例如:2018-06   -> 2018-07)
     * @param date
     * @return
     */
    public static Date addOneMonth(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date parse = null;
        try {
            Date oldDate = simpleDateFormat.parse(date);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(oldDate);
            rightNow.add(Calendar.MONTH, 1);
            String newDate = simpleDateFormat.format(rightNow.getTime());
            parse = simpleDateFormat.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    /**
     * 获取今天开始时间
     * @return
     */
    public static Date getBefore14DayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -13);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startTime = calendar.getTime();
        return startTime;
    }


    /**
     * 获取今天开始时间
     * @return
     */
    public static Date getTodayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startTime = calendar.getTime();
        return startTime;
    }

    /**
     * 获取今天开始时间
     * @return
     */
    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期结束时间
     * @return
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    /**
     * 获取今天开始时间
     * @return
     */
    public static Date getTodayEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endTime = calendar.getTime();
        return endTime;
    }


    /**
     * 判断是上午还是下午, 0上午 1下午
     * @return
     */
    public static int amOrPm(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int am = 0, pm = 1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            return am;
        } else {
            return pm;
        }
    }

    /**
     * 是上午
     * @return
     */
    public static boolean isAm(Date date) {
        return amOrPm(date) == 0;
    }

    /**
     * 是上午
     * @return
     */
    public static boolean isPm(Date date) {
        return amOrPm(date) == 1;
    }

}
