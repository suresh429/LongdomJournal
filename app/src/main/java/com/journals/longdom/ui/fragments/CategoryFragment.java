package com.journals.longdom.ui.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.journals.longdom.R;
import com.journals.longdom.databinding.FragmentCategoryBinding;
import com.journals.longdom.databinding.FragmentHomeBinding;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.HomeResponse;
import com.journals.longdom.ui.adapter.CategoryListAdapter;
import com.journals.longdom.ui.adapter.CurrentIssuesAdapter;
import com.journals.longdom.ui.adapter.ScientificJournalsAdapter;
import com.journals.longdom.ui.viewmodel.CategoryViewModel;
import com.journals.longdom.ui.viewmodel.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    FragmentCategoryBinding fragmentCategoryBinding;
    ArrayList<CategoryResponse.SubcatDetailsBean> subcatDetailsBeanArrayList = new ArrayList<>();
    CategoryViewModel categoryViewModel;

    CategoryListAdapter categoryListAdapter;

    String catId="",catName;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCategoryBinding = FragmentCategoryBinding.inflate(getLayoutInflater(), container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            catId = getArguments().getString("catId");
            catName = getArguments().getString("catName");
        }
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(catName);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.init(catId);

        // progress bar
        categoryViewModel.getProgressbarObservable().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){
                fragmentCategoryBinding.progressBar.setVisibility(View.VISIBLE);

            }else {
                fragmentCategoryBinding.progressBar.setVisibility(View.GONE);
            }
        });

        // Alert toast msg
        categoryViewModel.getToastObserver().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });

        // get home data
        categoryViewModel.getCategoryRepository().observe(getViewLifecycleOwner(), homeResponse -> {
            List<CategoryResponse.SubcatDetailsBean>catDetailsBeanList = homeResponse.getSubcat_details();

            subcatDetailsBeanArrayList.addAll(catDetailsBeanList);

            categoryListAdapter = new CategoryListAdapter(catDetailsBeanList,getActivity());
            fragmentCategoryBinding.recyclerCategoryList.setAdapter(categoryListAdapter);

            fragmentCategoryBinding.progressBar.setVisibility(View.GONE);

            categoryListAdapter.notifyDataSetChanged();
        });


        return fragmentCategoryBinding.getRoot();
    }
}