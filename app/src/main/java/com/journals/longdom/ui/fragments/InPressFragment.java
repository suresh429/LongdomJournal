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
import com.journals.longdom.databinding.FragmentInPressBinding;
import com.journals.longdom.helper.ConnectionLiveData;
import com.journals.longdom.model.CurrentIssueResponse;
import com.journals.longdom.model.InPressResponse;
import com.journals.longdom.ui.adapter.CurrentIssuesAdapter1;
import com.journals.longdom.ui.adapter.InPressAdapter;
import com.journals.longdom.ui.viewmodel.CurrentIssueViewModel;
import com.journals.longdom.ui.viewmodel.InPressViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InPressFragment#} factory method to
 * create an instance of this fragment.
 */
public class InPressFragment extends Fragment implements LifecycleRegistryOwner {

   
    FragmentInPressBinding fragmentInPressBinding;

    private static final String TAG = "CategoryFragment";
    public static final int MobileData = 2;
    public static final int WifiData = 1;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    ArrayList<InPressResponse.InpressDetailsBean> inpressDetailsBeanArrayList = new ArrayList<>();
    InPressViewModel inPressViewModel;

    InPressAdapter inPressAdapter;

    String journalcode,rel_keyword,journal_logo,ActionBarTitle;
    public InPressFragment() {
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
        fragmentInPressBinding=FragmentInPressBinding.inflate(getLayoutInflater(),container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            journalcode = getArguments().getString("journalcode");
            rel_keyword = getArguments().getString("rel_keyword");
            journal_logo = getArguments().getString("journal_logo");
            ActionBarTitle = getArguments().getString("ActionBarTitle");
        }
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(ActionBarTitle);

        inPressViewModel = new ViewModelProvider(this).get(InPressViewModel.class);
        inPressViewModel.init(journalcode,rel_keyword,journal_logo);

        // progress bar
        inPressViewModel.getProgressbarObservable().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                fragmentInPressBinding.progressBar.setVisibility(View.VISIBLE);

            }else {
                fragmentInPressBinding.progressBar.setVisibility(View.GONE);
            }
        });

        // Alert toast msg
        inPressViewModel.getToastObserver().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });

        // get home data
        inPressViewModel.getInPressRepository().observe(getViewLifecycleOwner(), homeResponse -> {


            if (homeResponse.isStatus()) {
                List<InPressResponse.InpressDetailsBean> inpressDetailsBeanList = homeResponse.getInpress_details();

                inpressDetailsBeanArrayList.addAll(inpressDetailsBeanList);

                inPressAdapter = new InPressAdapter(inpressDetailsBeanList);
                fragmentInPressBinding.recyclerInPressList.setAdapter(inPressAdapter);

                fragmentInPressBinding.progressBar.setVisibility(View.GONE);

                inPressAdapter.notifyDataSetChanged();
                fragmentInPressBinding.txtEmptyView.setVisibility(View.GONE);
                Log.d(TAG, "onCreateView: "+" data found");
            }else {
                Log.d(TAG, "onCreateView: "+"NO data");
                fragmentInPressBinding.recyclerInPressList.setVisibility(View.GONE);
                fragmentInPressBinding.txtEmptyView.setVisibility(View.VISIBLE);
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
                Snackbar snackbar = Snackbar.make(fragmentInPressBinding.getRoot().getRootView(), "No Internet connection", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();

            }
        });

        return fragmentInPressBinding.getRoot();
    }

    /* required to make activity life cycle owner */
    @NotNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}