package com.journals.longdom.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.HomeResponse;
import com.journals.longdom.network.JournalRepository;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<String> toastMessageObserver ;
    private MutableLiveData<Boolean> progressbarObservable;
    private MutableLiveData<CategoryResponse> mutableLiveData;

    public void init(String id){
        if (mutableLiveData != null){
            return;
        }
        JournalRepository journalRepository = JournalRepository.getInstance();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cat_id",id);
        mutableLiveData = journalRepository.getCategoryData(jsonObject);
        progressbarObservable = journalRepository.getProgressbarObservable();
        toastMessageObserver = journalRepository.getToastObserver();
    }

    public LiveData<CategoryResponse> getCategoryRepository() {
        return mutableLiveData;
    }

    public LiveData<String> getToastObserver(){
        return toastMessageObserver;
    }

    public MutableLiveData<Boolean> getProgressbarObservable() {
        return progressbarObservable;
    }

}