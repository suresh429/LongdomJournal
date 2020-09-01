package com.journals.longdom.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.journals.longdom.databinding.FragmentInPressBinding;
import com.journals.longdom.databinding.FragmentVolumeIssueBinding;
import com.journals.longdom.helper.ConnectionLiveData;
import com.journals.longdom.model.InPressResponse;
import com.journals.longdom.model.VolumeIssueResponse;
import com.journals.longdom.ui.adapter.InPressAdapter;
import com.journals.longdom.ui.adapter.VolumeIssueAdapter;
import com.journals.longdom.ui.viewmodel.InPressViewModel;
import com.journals.longdom.ui.viewmodel.VolumeIssueViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VolumeIssueFragment#} factory method to
 * create an instance of this fragment.
 */
public class VolumeIssueFragment extends Fragment implements LifecycleRegistryOwner {

   
    FragmentVolumeIssueBinding fragmentVolumeIssueBinding;

    private static final String TAG = "VolumeIssueFragment";
    public static final int MobileData = 2;
    public static final int WifiData = 1;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    ArrayList<VolumeIssueResponse.VolIssueDetailsBean> volIssueDetailsBeanArrayList = new ArrayList<>();
    VolumeIssueViewModel volumeIssueViewModel;

    VolumeIssueAdapter volumeIssueAdapter;


    String journalcode,volume,issue,year ,ActionBarTitle;
    public VolumeIssueFragment() {
        // Required empty public constructor
    }

   

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentVolumeIssueBinding=FragmentVolumeIssueBinding.inflate(getLayoutInflater(),container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            journalcode = getArguments().getString("journalcode");
            volume = getArguments().getString("volume");
            issue = getArguments().getString("issue");
            year = getArguments().getString("year");
            ActionBarTitle = getArguments().getString("ActionBarTitle");
        }
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(ActionBarTitle);

        volumeIssueViewModel = new ViewModelProvider(this).get(VolumeIssueViewModel.class);
        volumeIssueViewModel.init(journalcode,volume,issue,year);

        // progress bar
        volumeIssueViewModel.getProgressbarObservable().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                fragmentVolumeIssueBinding.progressBar.setVisibility(View.VISIBLE);

            }else {
                fragmentVolumeIssueBinding.progressBar.setVisibility(View.GONE);
            }
        });

        // Alert toast msg
        volumeIssueViewModel.getToastObserver().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });

        // get home data
        volumeIssueViewModel.getVolumeIssueRepository().observe(getViewLifecycleOwner(), homeResponse -> {


            if (homeResponse.isStatus()) {
                List<VolumeIssueResponse.VolIssueDetailsBean> volIssueDetailsBeanList = homeResponse.getVol_issue_details();

                volIssueDetailsBeanArrayList.addAll(volIssueDetailsBeanList);

                volumeIssueAdapter = new VolumeIssueAdapter(volIssueDetailsBeanList,requireActivity());
                fragmentVolumeIssueBinding.recyclerVolumeIssueList.setAdapter(volumeIssueAdapter);

                volumeIssueAdapter.notifyDataSetChanged();

                fragmentVolumeIssueBinding.progressBar.setVisibility(View.GONE);
                fragmentVolumeIssueBinding.txtEmptyView.setVisibility(View.GONE);
                Log.d(TAG, "onCreateView: "+" data found");
            }else {
                Log.d(TAG, "onCreateView: "+"NO data");
                fragmentVolumeIssueBinding.recyclerVolumeIssueList.setVisibility(View.GONE);
                fragmentVolumeIssueBinding.txtEmptyView.setVisibility(View.VISIBLE);
            }
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
                Snackbar snackbar = Snackbar.make(fragmentVolumeIssueBinding.getRoot().getRootView(), "No Internet connection", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();

            }
        });

        return fragmentVolumeIssueBinding.getRoot();
    }

    /* required to make activity life cycle owner */
    @NotNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}