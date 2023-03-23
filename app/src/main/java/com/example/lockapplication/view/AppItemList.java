package com.example.lockapplication.view;

public class AppItemList {
    public int imageId;
    public String appName;
    public boolean status;

    public AppItemList(int imageId, String appName, boolean status) {
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

    public int getImageId() {
        return imageId;
    }
}
