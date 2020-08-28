package com.journals.longdom.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.InPressResponse;
import com.journals.longdom.network.JournalRepository;

import static android.content.ContentValues.TAG;

public class InPressViewModel extends ViewModel {
    private MutableLiveData<String> toastMessageObserver ;
    private MutableLiveData<Boolean> progressbarObservable;
    private MutableLiveData<InPressResponse> mutableLiveData;

    public void init(String journalcode,String rel_keyword,String journal_logo){
        if (mutableLiveData != null){
            return;
        }
        JournalRepository journalRepository = JournalRepository.getInstance();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("journalcode",journalcode);
        jsonObject.addProperty("rel_keyword",rel_keyword);
        jsonObject.addProperty("journal_logo",journal_logo);
        Log.d(TAG, "init: "+jsonObject);
        mutableLiveData = journalRepository.getInPressData(jsonObject);
        progressbarObservable = journalRepository.getProgressbarObservable();
        toastMessageObserver = journalRepository.getToastObserver();
    }

    public LiveData<InPressResponse> getInPressRepository() {
        return mutableLiveData;
    }

    public LiveData<String> getToastObserver(){
        return toastMessageObserver;
    }

    public MutableLiveData<Boolean> getProgressbarObservable() {
        return progressbarObservable;
    }

}