package com.example.lockapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lockapplication.R;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.ArrayList;


public class AppAdapter extends RecyclerView.Adapter<AppAdapter.MyAppViewModel> {

    ArrayList<AppItemList> arrayList;
    SwitchChInterface anInterface;
    Context context;

    public AppAdapter(Context context,ArrayList<AppItemList> arrayList,SwitchChInterface anInterface) {
        this.context = context;
        this.arrayList = arrayList;
        this.anInterface = anInterface;

    }

    @NonNull
    @Override
    public MyAppViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyAppViewModel(LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAppViewModel holder, int position) {
        AppItemList appItemList = arrayList.get(position);

        holder.imageView.setImageDrawable(appItemList.getImageId());
        holder.textView.setText(appItemList.getAppName());
        holder.aSwitch.setChecked(appItemList.isStatus());

        holder.aSwitch.setOnCheckedChangeListener((compoundButton, bChecked) -> {
            anInterface.onChSwitchButton(holder.getAdapterPosition(), bChecked);
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyAppViewModel extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView textView;
        MaterialSwitch aSwitch;

        public MyAppViewModel(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.app_imageView);
            textView = itemView.findViewById(R.id.app_name);
            aSwitch = itemView.findViewById(R.id.switchMaterial);

        }


    }
    public interface SwitchChInterface{
        void onChSwitchButton(int position, boolean checked);
    }

}
