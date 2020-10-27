package com.sojay.testfunction.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final String DATE_FORMAT_PATTERN_YMD = "yyyy-MM-dd";
    private static final String DATE_FORMAT_PATTERN_YMD_HM = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转字符串
     *
     * @param timestamp     时间戳
     * @param isPreciseTime 是否包含时分秒
     * @return 格式化的日期字符串
     */
    public static String long2Str(long timestamp, boolean isPreciseTime) {
        return long2Str(timestamp, getFormatPattern(isPreciseTime));
    }

    private static String long2Str(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 字符串转时间戳
     *
     * @param dateStr       日期字符串
     * @param isPreciseTime 是否包含时分秒
     * @return 时间戳
     */
    public static long str2Long(String dateStr, boolean isPreciseTime) {
        return str2Long(dateStr, getFormatPattern(isPreciseTime));
    }

    private static long str2Long(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.CHINA).parse(dateStr).getTime();
        } catch (Throwable ignored) {
        }
        return 0;
    }

    private static String getFormatPattern(boolean showSpecificTime) {
        if (showSpecificTime) {
            return DATE_FORMAT_PATTERN_YMD_HM;
        } else {
            return DATE_FORMAT_PATTERN_YMD;
        }
    }

    /**
     * 计算年龄差，与当前时间做比较
     * @param beginDateStr  出生日期
     * @return  格式：*岁*个月*天
     */
    public static String DifferAgeDate(String beginDateStr) {
        return DifferDate(beginDateStr, long2Str(System.currentTimeMillis(), false), "岁");
    }

    /**
     * 计算时间差，距离当前时间
     * @param beginDateStr  开始日期字符串
     * @param endDateStr    结束日期字符串
     * @param yearUnit      返回时年的单位：年/岁
     * @return
     */
    private static String DifferDate(String beginDateStr, String endDateStr, String yearUnit) {

        if (TextUtils.isEmpty(beginDateStr))
            return "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN_YMD, Locale.getDefault());
            Calendar beginDateCal = Calendar.getInstance();
            beginDateCal.setTime(sdf.parse(beginDateStr));

            Calendar nowDateCal = Calendar.getInstance();
            nowDateCal.setTime(sdf.parse(endDateStr));

            int beginYear = beginDateCal.get(Calendar.YEAR);
            int endYear = nowDateCal.get((Calendar.YEAR));

            //当前年份小于开始年份  返回：“”【时间错误】
            if (endYear < beginYear)
                return "";

            int beginMonth = beginDateCal.get(Calendar.MONTH);
            int endMonth = nowDateCal.get((Calendar.MONTH));

            int beginMaxDay = beginDateCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int beginDay = beginDateCal.get(Calendar.DAY_OF_MONTH);
            int endDay = nowDateCal.get((Calendar.DAY_OF_MONTH));

            // 年份相同
            if (endYear == beginYear) {
                // 月份相同
                //   日期相同 返回：0年0月0天
                //   当前日期大于开始日期  返回：0年0月 天数差
                //   当前日期小于开始日期  返回：“”【时间错误】
                if (endMonth == beginMonth) {
                    if (endDay < beginDay)
                        return "";
                    return "0" + yearUnit + "0个月" + (endDay - beginDay) + "天";
                }

                // 当前月份大开始月份
                //   日期相同 返回：0年 月数差 0天
                //   当前日期大于  返回：0年 月数差 天数差
                //   当前日期小于  返回：0年 月数差-1 开始月天数-开始天数+当前天数
                if (endMonth > beginMonth) {
                    if (endDay < beginDay)
                        return "0" + yearUnit + (endMonth - beginMonth - 1) + "个月" + (beginMaxDay - beginDay + endDay) + "天";
                    return "0" + yearUnit + (endMonth - beginMonth) + "个月" + (endDay - beginDay) + "天";
                }

                //   当前月份小于开始月份  返回：“"【时间错误】
                return "";
            }

            // 当前年份大于开始年份
            // 上面年份情况已经判断，只剩下这一种情况，所以不用写年份判断
            // 月份相同
            //   日期相同  返回：年数差 0月0天
            //   当前日期大于开始日期  返回：年数差 0月 天数差
            //   当前日期小于开始日期  返回：年数差-1 11月 开始月天数-开始天数+当前天数
            if (endMonth == beginMonth) {
                if (endDay < beginDay)
                    return (endYear - beginYear - 1) + yearUnit + "11个月" + (beginMaxDay - beginDay + endDay) + "天";
                return (endYear - beginYear) + yearUnit + "0个月" + (endDay - beginDay) + "天";
            }

            // 当前月份大于开始月份
            //   日期相同  返回：年数差 月数差 0天
            //   当前日期大于开始日期  返回：年数差 月数差 天数差
            //   当前日期小于开始开始日期  返回：年数差 月数差-1 开始月天数-开始天数+当前天数
            if (endMonth > beginMonth) {
                if (endDay < beginDay)
                    return (endYear - beginYear) + yearUnit + (endMonth - beginMonth - 1) + "个月" + (beginMaxDay - beginDay + endDay) + "天";
                return (endYear - beginYear) + yearUnit + (endMonth - beginMonth) + "个月" + (endDay - beginDay) + "天";
            }

            // 当前月份小于开始月份
            //   日期相同  返回：年数差-1 12-开始月份+当前月份 0天
            //   当前日期大于开始日期  返回：年数差-1 12-开始月份+当前月份 天数差
            //   当前日期小于开始日期  返回：年数差-1 12-开始月份+当前月份-1 开始月天数-开始天数+当前天数
            // 上面月份情况已经判断，只剩下这一种情况，所以不用写月份判断
            if (endDay < beginDay)
                return (endYear - beginYear) + yearUnit + (12 - beginMonth + endMonth -1) + "个月" + (beginMaxDay - beginDay + endDay) + "天";
            return (endYear - beginYear) + yearUnit + (12 - beginMonth + endMonth) + "个月" + (endDay - beginDay) + "天";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
