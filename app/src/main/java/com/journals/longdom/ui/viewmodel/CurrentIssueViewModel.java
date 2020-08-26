package com.journals.longdom.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.journals.longdom.model.CurrentIssueResponse;
import com.journals.longdom.model.JournalHomeResponse;
import com.journals.longdom.network.JournalRepository;

import static android.content.ContentValues.TAG;

public class CurrentIssueViewModel extends ViewModel {
    private MutableLiveData<String> toastMessageObserver ;
    private MutableLiveData<Boolean> progressbarObservable;
    private MutableLiveData<CurrentIssueResponse> mutableLiveData;

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
        mutableLiveData = journalRepository.getCurrentIssueData(jsonObject);
        progressbarObservable = journalRepository.getProgressbarObservable();
        toastMessageObserver = journalRepository.getToastObserver();
    }

    public LiveData<CurrentIssueResponse> getCurrentIssueRepository() {
        return mutableLiveData;
    }

    public LiveData<String> getToastObserver(){
        return toastMessageObserver;
    }

    public MutableLiveData<Boolean> getProgressbarObservable() {
        return progressbarObservable;
    }

}