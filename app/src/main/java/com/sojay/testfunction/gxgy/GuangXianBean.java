package com.sojay.testfunction.gxgy;

public class GuangXianBean {

    private float max;
    private float min;
    private float pingjun;
    private String date;

    public GuangXianBean(float max, float min, float pingjun, String date) {
        this.max = max;
        this.min = min;
        this.pingjun = pingjun;
        this.date = date;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public float getPingjun() {
        return pingjun;
    }

    public String getDate() {
        return date;
    }
}
