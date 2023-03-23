package com.example.lockapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.lockapplication.R;
import com.example.lockapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        ActivityMainBinding binding;
        ArrayList<AppItemList> arrayList;
        AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        arrayList.add(new AppItemList(R.drawable.ic_launcher_background, "appName", false));
        arrayList.add(new AppItemList(R.drawable.ic_launcher_background, "appName", true));
        arrayList.add(new AppItemList(R.drawable.ic_launcher_background, "appName", false));
        arrayList.add(new AppItemList(R.drawable.ic_launcher_background, "appName", true));
        arrayList.add(new AppItemList(R.drawable.ic_launcher_background, "appName", false));
        adapter = new AppAdapter(arrayList);
        binding.recyclerView.setAdapter(adapter);
    }
}