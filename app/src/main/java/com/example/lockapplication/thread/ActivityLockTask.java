package com.example.lockapplication.thread;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import java.util.List;


public class ActivityLockTask extends Activity {
    private DevicePolicyManager dpm;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, DeviceAdminReceiver.class);
    }

    public void activateLockTask() {
        if (dpm.isLockTaskPermitted(this.getPackageName())) {
            startLockTask();
        }
    }

    public void deactivateLockTask() {
        stopLockTask();
    }

    public boolean isLockTaskActive() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(1);
        if (runningTasks != null && runningTasks.size() > 0) {
            String topActivity = runningTasks.get(0).topActivity.getPackageName();
            return topActivity.equals(getPackageName());
        }
        return false;
    }
}
