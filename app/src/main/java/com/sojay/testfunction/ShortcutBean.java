package com.sojay.testfunction;

public class ShortcutBean {

    private Class intentClass;
    private String shortcutId;
    private String extraMsg;

    private int iconId;
    private String shortName;
    private String longName;

    public ShortcutBean(Class intentClass, String shortcutId, int iconId, String shortName, String longName) {
        this.intentClass = intentClass;
        this.shortcutId = shortcutId;
        this.iconId = iconId;
        this.shortName = shortName;
        this.longName = longName;
    }

    public ShortcutBean(Class intentClass, String shortcutId, String extraMsg, int iconId, String shortName, String longName) {
        this.intentClass = intentClass;
        this.shortcutId = shortcutId;
        this.extraMsg = extraMsg;
        this.iconId = iconId;
        this.shortName = shortName;
        this.longName = longName;
    }

    public Class getIntentClass() {
        return intentClass;
    }

    public String getShortcutId() {
        return shortcutId;
    }

    public String getExtraMsg() {
        return extraMsg;
    }

    public int getIconId() {
        return iconId;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }
}
