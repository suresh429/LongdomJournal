package com.journals.longdom.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.journals.longdom.R;
import com.journals.longdom.databinding.FragmentGalleryBinding;
import com.journals.longdom.ui.viewmodel.GalleryViewModel;

public class GalleryFragment extends Fragment {

   FragmentGalleryBinding fragmentGalleryBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentGalleryBinding = FragmentGalleryBinding.inflate(getLayoutInflater(),container,false);


        return fragmentGalleryBinding.getRoot();
    }
}