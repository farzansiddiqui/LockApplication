package com.example.lockapplication.view;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.KeyguardManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.widget.Toast;



import com.example.lockapplication.databinding.ActivityMainBinding;
import com.example.lockapplication.thread.ActivityLockTask;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    ActivityMainBinding binding;
    ArrayList<AppItemList> arrayList;
    AppAdapter adapter;
    PackageManager packageManager;
    List<ApplicationInfo> list;
    CircularProgressIndicator circularProgressIndicator;
    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    ActivityLockTask activityLockTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        packageManager = getPackageManager();
        list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        activityLockTask = new ActivityLockTask();

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        // Check if the device has a secure lock screen
        if (keyguardManager.isKeyguardSecure()) {

            // Create a new KeyguardManager.KeyguardLock instance
            KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("MyAppLock");

            // Disable the keyguard to allow us to show the PIN prompt
            keyguardLock.disableKeyguard();

            // Prompt the user to enter their PIN
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("Enter PIN to unlock", "Please enter your PIN to unlock the app");
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);

            // Re-enable the keyguard when the activity is resumed
            onResume(); {
                super.onResume();
                keyguardLock.reenableKeyguard();
            }

        } else {
            // Device does not have a secure lock screen
            Toast.makeText(this, "Device does not have a secure lock screen", Toast.LENGTH_LONG).show();
        }



        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);

        packageInfos.sort((o1, o2) -> o1.applicationInfo.loadLabel(getPackageManager()).toString().
                compareToIgnoreCase(o2.applicationInfo.loadLabel(getPackageManager()).toString()));


        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
                arrayList.add(new AppItemList(icon, appName, false));

            }

        }
        adapter = new AppAdapter(this, arrayList, (position, checked) -> {
            if (checked) {

              /*  Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
                startActivity(intent);*/
                Toast.makeText(MainActivity.this, "Turn On" + position, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Turn Off" + position, Toast.LENGTH_SHORT).show();
            }
        });
        binding.recyclerView.setAdapter(adapter);

    }

    public void showCircleProgress() {
        circularProgressIndicator = new CircularProgressIndicator(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        circularProgressIndicator.setLayoutParams(layoutParams);
        binding.mainLayout.addView(circularProgressIndicator);
        circularProgressIndicator.setIndeterminate(true);
        circularProgressIndicator.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Device Admin Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Device Admin Not Enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityLockTask.deactivateLockTask();
    }


}