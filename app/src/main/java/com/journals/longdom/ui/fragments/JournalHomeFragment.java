package com.journals.longdom.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.lifecycle.ViewModelProvider;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.journals.longdom.R;
import com.journals.longdom.databinding.FragmentCategoryBinding;
import com.journals.longdom.databinding.FragmentJournalHomeBinding;
import com.journals.longdom.helper.ConnectionLiveData;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.JournalHomeResponse;
import com.journals.longdom.ui.adapter.CategoryListAdapter;
import com.journals.longdom.ui.viewmodel.CategoryViewModel;
import com.journals.longdom.ui.viewmodel.JournalHomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalHomeFragment#} factory method to
 * create an instance of this fragment.
 */
public class JournalHomeFragment extends Fragment implements LifecycleRegistryOwner {
    public static final int MobileData = 2;
    public static final int WifiData = 1;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    ArrayList<JournalHomeResponse> journalHomeResponseArrayList = new ArrayList<>();
    JournalHomeViewModel journalHomeViewModel;

    FragmentJournalHomeBinding fragmentJournalHomeBinding;
    String ActionBarTitle, page_url;

    public JournalHomeFragment() {
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
        fragmentJournalHomeBinding = FragmentJournalHomeBinding.inflate(getLayoutInflater(),container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ActionBarTitle = getArguments().getString("ActionBarTitle");
            page_url = getArguments().getString("page_url");

        }
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(ActionBarTitle);



        journalHomeViewModel = new ViewModelProvider(this).get(JournalHomeViewModel.class);
        journalHomeViewModel.init(page_url);

        // progress bar
        journalHomeViewModel.getProgressbarObservable().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                fragmentJournalHomeBinding.progressBar.setVisibility(View.VISIBLE);

            }else {
                fragmentJournalHomeBinding.progressBar.setVisibility(View.GONE);
            }
        });

        // Alert toast msg
        journalHomeViewModel.getToastObserver().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });

        // get home data
        journalHomeViewModel.getJournalHomeRepository().observe(getViewLifecycleOwner(), homeResponse -> {
           // List<JournalHomeResponse> catDetailsBeanList = homeResponse.getAbt_journal_details();

            if (homeResponse.isStatus()){
                fragmentJournalHomeBinding.txtJournalHome.setText(Html.fromHtml(homeResponse.getAbt_journal_details()));
                fragmentJournalHomeBinding.txtJournalHome.setMovementMethod(LinkMovementMethod.getInstance());
                fragmentJournalHomeBinding.txtEmptyView.setVisibility(View.GONE);

            }else {
                fragmentJournalHomeBinding.txtEmptyView.setVisibility(View.VISIBLE);
                fragmentJournalHomeBinding.txtJournalHome.setVisibility(View.GONE);
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
                Snackbar snackbar = Snackbar.make(fragmentJournalHomeBinding.getRoot().getRootView(), "No Internet connection", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();

            }
        });

        return fragmentJournalHomeBinding.getRoot();
    }

    /* required to make activity life cycle owner */
    @NotNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}