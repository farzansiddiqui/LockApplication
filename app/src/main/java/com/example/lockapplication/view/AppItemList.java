package com.example.lockapplication.view;

import android.graphics.drawable.Drawable;

public class AppItemList {
    public Drawable imageId;
    public String appName;
    public boolean status;

    public AppItemList(Drawable imageId, String appName, boolean status) {
        this.imageId = imageId;
        this.appName = appName;
        this.status = status;
    }

    public String getAppName() {
        return appName;
    }

    public boolean isStatus() {
        return status;
    }

    public Drawable getImageId() {
        return imageId;
    }
}
