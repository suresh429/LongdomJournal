package com.journals.longdom.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.CurrentIssueResponse;
import com.journals.longdom.model.HomeResponse;
import com.journals.longdom.model.JournalHomeResponse;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class JournalRepository {
    private MutableLiveData<String> toastMessageObserver;
    private MutableLiveData<Boolean> progressbarObservable;
    private static JournalRepository journalRepository;

    public static JournalRepository getInstance() {
        if (journalRepository == null) {
            journalRepository = new JournalRepository();
        }
        return journalRepository;
    }

    private Api newsApi;

    public JournalRepository() {
        newsApi = RetrofitService.createService(Api.class);
        progressbarObservable = new MutableLiveData<>();
        toastMessageObserver = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getProgressbarObservable() {
        return progressbarObservable;
    }

    public MutableLiveData<String> getToastObserver() {
        return toastMessageObserver;
    }

    // getting home data response
    public MutableLiveData<HomeResponse> getHomeData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<HomeResponse> homeData = new MutableLiveData<>();
        newsApi.getHomeList(jsonObject).enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(@NotNull Call<HomeResponse> call, @NotNull Response<HomeResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    homeData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<HomeResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    Log.d(TAG, "onFailure: " + "failure");
                }
                // homeData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return homeData;
    }


    //getting category data response
    public MutableLiveData<CategoryResponse> getCategoryData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<CategoryResponse> categoryData = new MutableLiveData<>();
        newsApi.getCategoryList(jsonObject).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NotNull Call<CategoryResponse> call, @NotNull Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<CategoryResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    Log.d(TAG, "onFailure: " + "failure");
                }
                categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }


    //getting journal home data response
    public MutableLiveData<JournalHomeResponse> getJournalHomeData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<JournalHomeResponse> categoryData = new MutableLiveData<>();
        newsApi.getJournalHomeDetails(jsonObject).enqueue(new Callback<JournalHomeResponse>() {
            @Override
            public void onResponse(@NotNull Call<JournalHomeResponse> call, @NotNull Response<JournalHomeResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<JournalHomeResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    Log.d(TAG, "onFailure: " + "failure");
                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }

    //getting current Issue data response
    public MutableLiveData<CurrentIssueResponse> getCurrentIssueData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<CurrentIssueResponse> categoryData = new MutableLiveData<>();
        newsApi.getCurrentIssueList(jsonObject).enqueue(new Callback<CurrentIssueResponse>() {
            @Override
            public void onResponse(@NotNull Call<CurrentIssueResponse> call, @NotNull Response<CurrentIssueResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentIssueResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    Log.d(TAG, "onFailure: " + "failure");
                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }
}
