package com.journals.longdom.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.snackbar.Snackbar;
import com.journals.longdom.helper.ConnectionLiveData;
import com.journals.longdom.model.ConnectionModel;
import com.journals.longdom.ui.adapter.CurrentIssuesAdapter;
import com.journals.longdom.ui.adapter.ScientificJournalsAdapter;
import com.journals.longdom.databinding.FragmentHomeBinding;
import com.journals.longdom.model.HomeResponse;
import com.journals.longdom.ui.viewmodel.HomeViewModel;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements LifecycleRegistryOwner {

    public static final int MobileData = 2;
    public static final int WifiData = 1;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    ArrayList<HomeResponse.CatDetailsBean> scientificJournalsList = new ArrayList<>();
    ArrayList<HomeResponse.CurrissueHighlightsBean> currentIssuesList = new ArrayList<>();
    HomeViewModel homeViewModel;
    FragmentHomeBinding fragmentHomeBinding;
    ScientificJournalsAdapter scientificJournalsAdapter;
    CurrentIssuesAdapter currentIssuesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init("1");

        // progress bar
        homeViewModel.getProgressbarObservable().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                fragmentHomeBinding.progressBar.setVisibility(View.VISIBLE);
                fragmentHomeBinding.txtJournalName.setVisibility(View.GONE);
                fragmentHomeBinding.txtCurrentIssueName.setVisibility(View.GONE);
            }else {
                fragmentHomeBinding.progressBar.setVisibility(View.GONE);
                fragmentHomeBinding.txtJournalName.setVisibility(View.VISIBLE);
                fragmentHomeBinding.txtCurrentIssueName.setVisibility(View.VISIBLE);
            }
        });

        // Alert toast msg
        homeViewModel.getToastObserver().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });

        // get home data
        homeViewModel.getHomeRepository().observe(getViewLifecycleOwner(), homeResponse -> {
            List<HomeResponse.CatDetailsBean> catDetailsBeanList = homeResponse.getCat_details();
            List<HomeResponse.CurrissueHighlightsBean> currissueHighlightsBeanList = homeResponse.getCurrissue_highlights();

            scientificJournalsList.addAll(catDetailsBeanList);
            currentIssuesList.addAll(currissueHighlightsBeanList);

            scientificJournalsAdapter = new ScientificJournalsAdapter(catDetailsBeanList,getActivity());
            fragmentHomeBinding.recyclerScientificJournals.setAdapter(scientificJournalsAdapter);

            currentIssuesAdapter = new CurrentIssuesAdapter(currissueHighlightsBeanList);
            fragmentHomeBinding.recyclerHomeCurrentIssue.setAdapter(currentIssuesAdapter);

            fragmentHomeBinding.progressBar.setVisibility(View.GONE);

            scientificJournalsAdapter.notifyDataSetChanged();
            currentIssuesAdapter.notifyDataSetChanged();
        });


        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getActivity());
        connectionLiveData.observe(getViewLifecycleOwner(), connection -> {
            /* every time connection state changes, we'll be notified and can perform action accordingly */
            if (connection.getIsConnected()) {
                switch (connection.getType()) {
                    case WifiData:
                       // Toast.makeText(getActivity(), String.format("Wifi turned ON"), Toast.LENGTH_SHORT).show();
                        break;
                    case MobileData:
                       // Toast.makeText(getActivity(), String.format("Mobile data turned ON"), Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {
                Snackbar snackbar = Snackbar.make(fragmentHomeBinding.getRoot().getRootView(), "No Internet connection", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();

            }
        });

        return fragmentHomeBinding.getRoot();
    }



    /* required to make activity life cycle owner */
    @NotNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}