package com.journals.longdom.network;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.journals.longdom.model.AbstractResponse;
import com.journals.longdom.model.ArchiveResponse;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.ContactResponse;
import com.journals.longdom.model.CurrentIssueResponse;
import com.journals.longdom.model.HomeResponse;
import com.journals.longdom.model.InPressResponse;
import com.journals.longdom.model.JournalHomeResponse;
import com.journals.longdom.model.JournalsListResponse;
import com.journals.longdom.model.VolumeIssueResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class JournalRepository {
    private MutableLiveData<String> toastMessageObserver;
    private MutableLiveData<Boolean> progressbarObservable;
    private static JournalRepository journalRepository;

    public static JournalRepository getInstance(Context context) {
        if (journalRepository == null) {
            journalRepository = new JournalRepository(context);

        }
        return journalRepository;
    }

    private Api newsApi;


    public JournalRepository(Context context) {
        newsApi = RetrofitService.createService(Api.class,context);
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
                    toastMessageObserver.setValue(t.getMessage());
                    // Whenever you want to show toast use setValue.

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
                    toastMessageObserver.setValue(t.getMessage());
                    // Whenever you want to show toast use setValue.

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
                    toastMessageObserver.setValue(t.getMessage());
                    // Whenever you want to show toast use setValue.

                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }

    //getting abstract display data response
    public MutableLiveData<AbstractResponse> getAbstractDisplayData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<AbstractResponse> categoryData = new MutableLiveData<>();
        newsApi.getAbstractDisplay(jsonObject).enqueue(new Callback<AbstractResponse>() {
            @Override
            public void onResponse(@NotNull Call<AbstractResponse> call, @NotNull Response<AbstractResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<AbstractResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    toastMessageObserver.setValue(t.getMessage());
                    // Whenever you want to show toast use setValue.

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
                    toastMessageObserver.setValue(t.getMessage());
                    // Whenever you want to show toast use setValue.

                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }

    //getting In Press data response
    public MutableLiveData<InPressResponse> getInPressData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<InPressResponse> categoryData = new MutableLiveData<>();
        newsApi.getInPressList(jsonObject).enqueue(new Callback<InPressResponse>() {
            @Override
            public void onResponse(@NotNull Call<InPressResponse> call, @NotNull Response<InPressResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<InPressResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    toastMessageObserver.setValue(t.getMessage());
                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }

    //getting archive data response
    public MutableLiveData<ArchiveResponse> getArchiveData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<ArchiveResponse> categoryData = new MutableLiveData<>();
        newsApi.getArchiveList(jsonObject).enqueue(new Callback<ArchiveResponse>() {
            @Override
            public void onResponse(@NotNull Call<ArchiveResponse> call, @NotNull Response<ArchiveResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<ArchiveResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    toastMessageObserver.setValue(t.getMessage());
                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }

    //getting volume issue data response
    public MutableLiveData<VolumeIssueResponse> getVolumeIssueData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<VolumeIssueResponse> categoryData = new MutableLiveData<>();
        newsApi.getVolumeIssueDisplay(jsonObject).enqueue(new Callback<VolumeIssueResponse>() {
            @Override
            public void onResponse(@NotNull Call<VolumeIssueResponse> call, @NotNull Response<VolumeIssueResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<VolumeIssueResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    toastMessageObserver.setValue(t.getMessage());
                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }

    //getting journal list data response
    public MutableLiveData<JournalsListResponse> getJournalListDisplay(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<JournalsListResponse> categoryData = new MutableLiveData<>();
        newsApi.getJournalListDisplay(jsonObject).enqueue(new Callback<JournalsListResponse>() {
            @Override
            public void onResponse(@NotNull Call<JournalsListResponse> call, @NotNull Response<JournalsListResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<JournalsListResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    toastMessageObserver.setValue(t.getMessage());
                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }


    //add contact data response
    public MutableLiveData<ContactResponse> getContactData(JsonObject jsonObject) {
        progressbarObservable.setValue(true);
        MutableLiveData<ContactResponse> categoryData = new MutableLiveData<>();
        newsApi.getContactDisplay(jsonObject).enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(@NotNull Call<ContactResponse> call, @NotNull Response<ContactResponse> response) {
                if (response.isSuccessful()) {
                    progressbarObservable.setValue(false);
                    categoryData.setValue(response.body());
                } else {
                    progressbarObservable.setValue(false);
                    toastMessageObserver.setValue("Something unexpected happened to our request: " + response.message()); // Whenever you want to show toast use setValue.

                }
            }

            @Override
            public void onFailure(@NotNull Call<ContactResponse> call, @NotNull Throwable t) {
                if (t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    toastMessageObserver.setValue(t.getMessage());
                }
                //categoryData.setValue(null);
                progressbarObservable.setValue(false);
            }
        });
        return categoryData;
    }

}
