package com.journals.longdom.network;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.journals.longdom.model.HomeResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JournalRepository {
    private MutableLiveData<String> toastMessageObserver ;
    private MutableLiveData<Boolean> progressbarObservable;
    private static JournalRepository journalRepository;

    public static JournalRepository getInstance(){
        if (journalRepository == null){
            journalRepository = new JournalRepository();
        }
        return journalRepository;
    }

    private Api newsApi;

    public JournalRepository(){
        newsApi = RetrofitService.createService(Api.class);
        progressbarObservable = new MutableLiveData<>();
        toastMessageObserver = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getProgressbarObservable() {
        return progressbarObservable;
    }

    public MutableLiveData<String> getToastObserver(){
        return toastMessageObserver;
    }

    public MutableLiveData<HomeResponse> getHomeData(JsonObject jsonObject){
        progressbarObservable.setValue(true);
        MutableLiveData<HomeResponse> homeData = new MutableLiveData<>();
        newsApi.getHomeList(jsonObject).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(@NotNull Call<HomeResponse> call, @NotNull Response<HomeResponse> response) {
                if (response.isSuccessful()){
                    progressbarObservable.setValue(false);
                    homeData.setValue(response.body());
                }else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: "+response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<HomeResponse> call, @NotNull Throwable t) {
                homeData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return homeData;
    }

}
