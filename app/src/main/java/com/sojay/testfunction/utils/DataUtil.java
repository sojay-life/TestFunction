package com.sojay.testfunction.utils;

import java.text.DecimalFormat;

public class DataUtil {
    /**
     * 保留小数点后2位
     *
     * @return
     */
    public static String keepTwo(double b) {
        DecimalFormat format = new DecimalFormat("#0.00");
        String str = format.format(b);
        return str;
    }
}
