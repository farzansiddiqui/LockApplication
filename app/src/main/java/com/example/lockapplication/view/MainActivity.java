package com.example.lockapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.example.lockapplication.R;
import com.example.lockapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        ActivityMainBinding binding;
        ArrayList<AppItemList> arrayList;
        AppAdapter adapter;
        PackageManager packageManager;
        List<ApplicationInfo> list;

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        packageManager = getPackageManager();
        list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo app : list) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && !app.sourceDir.startsWith("/system/app") && !app.sourceDir.startsWith("/system/priv-app")){
                String name = packageManager.getApplicationLabel(app).toString();
                Log.d("TAG", "onCreate: "+name);
                Drawable icon  = app.loadIcon(packageManager);
                Intent intent = packageManager.getLaunchIntentForPackage(app.packageName);
                if (intent != null){
                    arrayList.add(new AppItemList(icon, name,false));
                }

            }

        }
        adapter = new AppAdapter(arrayList);
        binding.recyclerView.setAdapter(adapter);
    }
}