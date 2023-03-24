package com.example.lockapplication.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lockapplication.view.AppItemList;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<AppItemList>> mutableLiveData = new MutableLiveData<>();

    public void setItems(ArrayList<AppItemList> arrayList) {
        mutableLiveData.setValue(arrayList);

    }


    public LiveData<ArrayList<AppItemList>> getAppList() {
        return mutableLiveData;
    }


}
