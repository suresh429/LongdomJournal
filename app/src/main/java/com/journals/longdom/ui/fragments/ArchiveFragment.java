package com.journals.longdom.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.journals.longdom.R;
import com.journals.longdom.databinding.FragmentArchiveBinding;
import com.journals.longdom.databinding.FragmentCategoryBinding;
import com.journals.longdom.helper.ConnectionLiveData;
import com.journals.longdom.model.ArchiveChildItem;
import com.journals.longdom.model.ArchiveHeaderItem;
import com.journals.longdom.model.ArchiveResponse;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.ui.adapter.ArchiveChildAdapter;
import com.journals.longdom.ui.adapter.ArchiveHeadAdapter;
import com.journals.longdom.ui.adapter.CategoryListAdapter;
import com.journals.longdom.ui.viewmodel.ArchiveViewModel;
import com.journals.longdom.ui.viewmodel.CategoryViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArchiveFragment#} factory method to
 * create an instance of this fragment.
 */
public class ArchiveFragment extends Fragment implements LifecycleRegistryOwner {
    private static final String TAG = "ArchiveFragment";
    public static final int MobileData = 2;
    public static final int WifiData = 1;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    FragmentArchiveBinding fragmentArchiveBinding;
    ArrayList<ArchiveResponse.ArchiveDetailsBean> archiveDetailsBeanArrayList = new ArrayList<>();
    ArrayList<ArchiveHeaderItem> archiveHeaderItemArrayList = new ArrayList<>();
    ArrayList<ArchiveChildItem> archiveChildItemArrayList = new ArrayList<>();
    ArchiveViewModel archiveViewModel;

    ArchiveHeadAdapter archiveHeadAdapter;

    String ActionBarTitle,journalcode;

    public ArchiveFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentArchiveBinding = FragmentArchiveBinding.inflate(getLayoutInflater(), container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ActionBarTitle = getArguments().getString("ActionBarTitle");
            journalcode = getArguments().getString("journalcode");
        }
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(ActionBarTitle);

        archiveViewModel = new ViewModelProvider(this).get(ArchiveViewModel.class);
        archiveViewModel.init(journalcode);

        // progress bar
        archiveViewModel.getProgressbarObservable().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                fragmentArchiveBinding.progressBar.setVisibility(View.VISIBLE);

            }else {
                fragmentArchiveBinding.progressBar.setVisibility(View.GONE);
            }
        });

        // Alert toast msg
        archiveViewModel.getToastObserver().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });

        // get home data
        archiveViewModel.getArchiveRepository().observe(getViewLifecycleOwner(), homeResponse -> {


            if (homeResponse != null){
                List<ArchiveResponse.ArchiveDetailsBean> catDetailsBeanList = homeResponse.getArchive_details();
                archiveDetailsBeanArrayList.addAll(catDetailsBeanList);

                archiveChildItemArrayList.clear();
                archiveHeaderItemArrayList.clear();
                for (ArchiveResponse.ArchiveDetailsBean archiveDetailsBean : catDetailsBeanList){

                    if (archiveDetailsBean.getYear().equalsIgnoreCase("2017")) {
                        archiveChildItemArrayList.add(new ArchiveChildItem(archiveDetailsBean.getVol_issue_name(),archiveDetailsBean.getYear(),archiveDetailsBean.getJournal(),archiveDetailsBean.getVol(),archiveDetailsBean.getIssue()));
                        archiveHeaderItemArrayList.add(new ArchiveHeaderItem(archiveDetailsBean.getYear(), archiveChildItemArrayList));
                    }
                }

                archiveHeadAdapter = new ArchiveHeadAdapter(archiveHeaderItemArrayList);
                fragmentArchiveBinding.recyclerArchiveHeadList.setAdapter(archiveHeadAdapter);

                fragmentArchiveBinding.progressBar.setVisibility(View.GONE);

                archiveHeadAdapter.notifyDataSetChanged();
                fragmentArchiveBinding.txtEmptyView.setVisibility(View.GONE);
                Log.d(TAG, "onCreateView: "+" data found");
            }else {
                Log.d(TAG, "onCreateView: "+"NO data");
                fragmentArchiveBinding.recyclerArchiveHeadList.setVisibility(View.GONE);
                fragmentArchiveBinding.txtEmptyView.setVisibility(View.VISIBLE);
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
                Snackbar snackbar = Snackbar.make(fragmentArchiveBinding.getRoot().getRootView(), "No Internet connection", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();

            }
        });

        return fragmentArchiveBinding.getRoot();
    }

    /* required to make activity life cycle owner */
    @NotNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}