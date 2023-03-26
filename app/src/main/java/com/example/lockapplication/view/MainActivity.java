package com.example.lockapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;


import com.example.lockapplication.databinding.ActivityMainBinding;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        ActivityMainBinding binding;
        ArrayList<AppItemList> arrayList;
        AppAdapter adapter;
        PackageManager packageManager;
        List<ApplicationInfo> list;
        CircularProgressIndicator circularProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        packageManager = getPackageManager();
        list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);



    List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);

        packageInfos.sort((o1, o2) -> o1.applicationInfo.loadLabel(getPackageManager()).toString().
                compareToIgnoreCase(o2.applicationInfo.loadLabel(getPackageManager()).toString()));



        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    Drawable icon = packageInfo.applicationInfo.loadIcon(packageManager);
                    arrayList.add(new AppItemList(icon, appName,false));

            }

        }
        adapter = new AppAdapter(this,arrayList);
        binding.recyclerView.setAdapter(adapter);

    }
public void showCircleProgress(){
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


}