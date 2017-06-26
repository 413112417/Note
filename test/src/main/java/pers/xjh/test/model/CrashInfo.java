package pers.xjh.test.model;

/**
 * Created by XJH on 2017/4/30.
 */

public class CrashInfo {

    private String time;

    private String info;

    public CrashInfo(String time, String info) {
        this.time = time;
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
