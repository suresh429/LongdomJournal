package com.journals.longdom.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.snackbar.Snackbar;
import com.journals.longdom.adapter.CurrentIssuesAdapter;
import com.journals.longdom.adapter.ScientificJournalsAdapter;
import com.journals.longdom.databinding.CurrentIssueItemBinding;
import com.journals.longdom.databinding.FragmentHomeBinding;
import com.journals.longdom.model.HomeResponse;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements InternetConnectivityListener {
    private InternetAvailabilityChecker mInternetAvailabilityChecker;
    ArrayList<HomeResponse.CatDetailsBean> scientificJournalsList = new ArrayList<>();
    ArrayList<HomeResponse.CurrissueHighlightsBean> currentIssuesList = new ArrayList<>();
    HomeViewModel homeViewModel;
    FragmentHomeBinding fragmentHomeBinding;
    ScientificJournalsAdapter scientificJournalsAdapter;
    CurrentIssuesAdapter currentIssuesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // check internet
        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // homeViewModel.init("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJfaWQiOiJNMjA3MTE2MDM5OTIiLCJjbGllbnRfaWQiOjk0LCJ1c2VyX2lkIjoxLCJpYXQiOjE1OTgyNzczMzQsImV4cCI6MTU5ODI3ODIzNH0.aSpoFjtTP38f9hifpn9KTkaG22VRbIvUCbAiOBU76oI");

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
        homeViewModel.getNewsRepository().observe(getViewLifecycleOwner(), homeResponse -> {
            List<HomeResponse.CatDetailsBean> catDetailsBeanList = homeResponse.getCat_details();
            List<HomeResponse.CurrissueHighlightsBean> currissueHighlightsBeanList = homeResponse.getCurrissue_highlights();

            scientificJournalsList.addAll(catDetailsBeanList);
            currentIssuesList.addAll(currissueHighlightsBeanList);

            scientificJournalsAdapter = new ScientificJournalsAdapter(catDetailsBeanList);
            fragmentHomeBinding.recyclerScientificJournals.setAdapter(scientificJournalsAdapter);

            currentIssuesAdapter = new CurrentIssuesAdapter(currissueHighlightsBeanList);
            fragmentHomeBinding.recyclerHomeCurrentIssue.setAdapter(currentIssuesAdapter);

            fragmentHomeBinding.progressBar.setVisibility(View.GONE);

            scientificJournalsAdapter.notifyDataSetChanged();
            currentIssuesAdapter.notifyDataSetChanged();
        });


        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {

            Snackbar snackbar = Snackbar.make(fragmentHomeBinding.getRoot().getRootView(), "Back to Internet connection", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.GREEN);
            snackbar.show();
        } else {
           // checkNetworkConnection(getActivity(),isConnected);
           // Snackbar.make(fragmentHomeBinding.getRoot().getRootView(), "Internet connection is lost", Snackbar.LENGTH_SHORT).show();

            Snackbar snackbar = Snackbar.make(fragmentHomeBinding.getRoot().getRootView(), "Internet connection is lost", Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.RED);
            snackbar.show();
        }



    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        mInternetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
    }*/


}