package com.journals.longdom.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.journals.longdom.R;
import com.journals.longdom.databinding.FragmentContactBinding;
import com.journals.longdom.databinding.FragmentJournalContactBinding;
import com.journals.longdom.helper.ConnectionLiveData;
import com.journals.longdom.model.ContactResponse;
import com.journals.longdom.model.JournalsListResponse;
import com.journals.longdom.ui.viewmodel.JournalListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class JournalContactFragment extends Fragment implements View.OnClickListener, LifecycleRegistryOwner {

    public static final int MobileData = 2;
    public static final int WifiData = 1;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);


    String journalcode, fname, lname, email, phone, message;
    JournalListViewModel journalListViewModel;
    FragmentJournalContactBinding fragmentContactBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentContactBinding = FragmentJournalContactBinding.inflate(getLayoutInflater(), container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            journalcode = getArguments().getString("journalcode");
        }
        fragmentContactBinding.txtDailUK.setOnClickListener(this);
        fragmentContactBinding.txtmail.setOnClickListener(this);
        fragmentContactBinding.buttonNext.setOnClickListener(this);


        journalListViewModel = new ViewModelProvider(this).get(JournalListViewModel.class);





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
                Snackbar snackbar = Snackbar.make(fragmentContactBinding.getRoot().getRootView(), "No Internet connection", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.RED);
                snackbar.show();

            }
        });

        return fragmentContactBinding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtDailUK:
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                String temp1 = "tel:" + fragmentContactBinding.txtDailUK.getText().toString();
                intent1.setData(Uri.parse(temp1));
                startActivity(intent1);
                break;
            case R.id.txtmail:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{fragmentContactBinding.txtmail.getText().toString()});

                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(requireActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonNext:


                fname = Objects.requireNonNull(fragmentContactBinding.editFirst.getText()).toString();
                lname = Objects.requireNonNull(fragmentContactBinding.editLast.getText()).toString();
                email = Objects.requireNonNull(fragmentContactBinding.editEmail.getText()).toString();
                phone = Objects.requireNonNull(fragmentContactBinding.editPhone.getText()).toString();
                message = Objects.requireNonNull(fragmentContactBinding.editAddress.getText()).toString();

                if (fname.isEmpty()) {
                    Toast.makeText(requireActivity(), "Please Enter First Name", Toast.LENGTH_SHORT).show();
                } else if (lname.isEmpty()) {
                    Toast.makeText(requireActivity(), "Please Enter Last Name", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(requireActivity(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (phone.isEmpty()) {
                    Toast.makeText(requireActivity(), "Please Enter Phone", Toast.LENGTH_SHORT).show();
                }else if (journalcode.isEmpty()) {
                    Toast.makeText(requireActivity(), "Please Select Journal", Toast.LENGTH_SHORT).show();
                } else if (message.isEmpty()) {
                    Toast.makeText(requireActivity(), "Please Enter Message", Toast.LENGTH_SHORT).show();
                } else {

                    journalListViewModel.contactData(journalcode,fname,lname,email,phone,message);
                    journalListViewModel.getContactRepository().observe(getViewLifecycleOwner(), new Observer<ContactResponse>() {
                        @Override
                        public void onChanged(ContactResponse contactResponse) {
                            if (contactResponse.isStatus()){

                                Toast.makeText(requireContext(), ""+contactResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                fragmentContactBinding.editFirst.setText("");
                                fragmentContactBinding.editLast.setText("");
                                fragmentContactBinding.editAddress.setText("");
                                fragmentContactBinding.editEmail.setText("");
                                fragmentContactBinding.editPhone.setText("");
                                //Navigation.findNavController(fragmentContactBinding.getRoot()).navigate(R.id.nav_home);
                                //NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.nav_home, true).build();
                                //Navigation.findNavController(fragmentContactBinding.getRoot()).navigate(R.id.nav_home, null, navOptions);

                            }else {
                                Toast.makeText(requireActivity(), ""+contactResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    // progress bar
                    journalListViewModel.getProgressbarObservable().observe(getViewLifecycleOwner(), aBoolean -> {
                        if (aBoolean) {
                            fragmentContactBinding.progressBar.setVisibility(View.VISIBLE);

                        } else {
                            fragmentContactBinding.progressBar.setVisibility(View.GONE);

                        }
                    });

                }

                break;


        }
    }

    /* required to make activity life cycle owner */
    @NotNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }




}