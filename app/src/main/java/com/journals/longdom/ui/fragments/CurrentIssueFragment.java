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
import com.journals.longdom.databinding.FragmentCategoryBinding;
import com.journals.longdom.databinding.FragmentCurrentIssueBinding;
import com.journals.longdom.helper.ConnectionLiveData;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.CurrentIssueResponse;
import com.journals.longdom.ui.adapter.CategoryListAdapter;
import com.journals.longdom.ui.adapter.CurrentIssuesAdapter1;
import com.journals.longdom.ui.viewmodel.CategoryViewModel;
import com.journals.longdom.ui.viewmodel.CurrentIssueViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentIssueFragment#} factory method to
 * create an instance of this fragment.
 */
public class CurrentIssueFragment extends Fragment implements LifecycleRegistryOwner {

    FragmentCurrentIssueBinding fragmentCurrentIssueBinding;
    private static final String TAG = "CategoryFragment";
    public static final int MobileData = 2;
    public static final int WifiData = 1;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    ArrayList<CurrentIssueResponse.CurrentissueDetailsBean> currentissueDetailsBeanArrayList = new ArrayList<>();
    CurrentIssueViewModel currentIssueViewModel;

    CurrentIssuesAdapter1 currentIssuesAdapter1;

    String journalcode,rel_keyword,journal_logo,ActionBarTitle;
    public CurrentIssueFragment() {
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
        fragmentCurrentIssueBinding = FragmentCurrentIssueBinding.inflate(getLayoutInflater(), container, false);



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            journalcode = getArguments().getString("journalcode");
            rel_keyword = getArguments().getString("rel_keyword");
            journal_logo = getArguments().getString("journal_logo");
            ActionBarTitle = getArguments().getString("ActionBarTitle");
        }
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(ActionBarTitle);

        currentIssueViewModel = new ViewModelProvider(this).get(CurrentIssueViewModel.class);
        currentIssueViewModel.init(journalcode,rel_keyword,journal_logo);

        // progress bar
        currentIssueViewModel.getProgressbarObservable().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                fragmentCurrentIssueBinding.progressBar.setVisibility(View.VISIBLE);

            }else {
                fragmentCurrentIssueBinding.progressBar.setVisibility(View.GONE);
            }
        });

        // Alert toast msg
        currentIssueViewModel.getToastObserver().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });

        // get home data
        currentIssueViewModel.getCurrentIssueRepository().observe(getViewLifecycleOwner(), homeResponse -> {


          /*  if (homeResponse != null){
                List<CurrentIssueResponse.CurrentissueDetailsBean> catDetailsBeanList = homeResponse.getCurrentissue_details();

                currentissueDetailsBeanArrayList.addAll(catDetailsBeanList);

                currentIssuesAdapter1 = new CurrentIssuesAdapter1(catDetailsBeanList);
                fragmentCurrentIssueBinding.recyclerCurrentIssueList.setAdapter(currentIssuesAdapter1);

                fragmentCurrentIssueBinding.progressBar.setVisibility(View.GONE);

                currentIssuesAdapter1.notifyDataSetChanged();
                fragmentCurrentIssueBinding.txtEmptyView.setVisibility(View.GONE);
                Log.d(TAG, "onCreateView: "+" data found");
            }else {
                Log.d(TAG, "onCreateView: "+"NO data");
                fragmentCurrentIssueBinding.recyclerCurrentIssueList.setVisibility(View.GONE);
                fragmentCurrentIssueBinding.txtEmptyView.setVisibility(View.VISIBLE);
            }*/

            if (homeResponse.isStatus()) {
                List<CurrentIssueResponse.CurrentissueDetailsBean> catDetailsBeanList = homeResponse.getCurrentissue_details();

                currentissueDetailsBeanArrayList.addAll(catDetailsBeanList);

                currentIssuesAdapter1 = new CurrentIssuesAdapter1(catDetailsBeanList);
                fragmentCurrentIssueBinding.recyclerCurrentIssueList.setAdapter(currentIssuesAdapter1);

                fragmentCurrentIssueBinding.progressBar.setVisibility(View.GONE);

                currentIssuesAdapter1.notifyDataSetChanged();
                fragmentCurrentIssueBinding.txtEmptyView.setVisibility(View.GONE);
                Log.d(TAG, "onCreateView: "+" data found");
            }else {
                Log.d(TAG, "onCreateView: "+"NO data");
                fragmentCurrentIssueBinding.recyclerCurrentIssueList.setVisibility(View.GONE);
                fragmentCurrentIssueBinding.txtEmptyView.setVisibility(View.VISIBLE);
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
                Snackbar snackbar = Snackbar.make(fragmentCurrentIssueBinding.getRoot().getRootView(), "No Internet connection", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();

            }
        });

        return fragmentCurrentIssueBinding.getRoot();
    }

    /* required to make activity life cycle owner */
    @NotNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}