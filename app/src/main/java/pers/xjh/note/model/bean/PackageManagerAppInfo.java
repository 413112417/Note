package pers.xjh.note.model.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by XJH on 2017/4/29.
 */

public class PackageManagerAppInfo {
    private String appLabel;
    private Drawable appIcon;
    private String packageName;

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
