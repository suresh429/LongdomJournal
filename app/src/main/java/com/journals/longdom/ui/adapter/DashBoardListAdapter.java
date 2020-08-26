package com.journals.longdom.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.journals.longdom.R;
import com.journals.longdom.databinding.CatgeogryListItemBinding;
import com.journals.longdom.databinding.DashboardListItemBinding;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.DashBoardModel;

import java.util.List;


public class DashBoardListAdapter extends RecyclerView.Adapter<DashBoardListAdapter.ViewHolder> {

    List<DashBoardModel> modelList;

    Context context;
    public DashBoardListAdapter(List<DashBoardModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public DashBoardListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DashboardListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardListAdapter.ViewHolder holder, int position) {

        holder.rowItemBinding.txtDasBoardTitle.setText(modelList.get(position).getDashBoardTitle());
        /*holder.rowItemBinding.getRoot().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("journal", modelList.get(position).getJournal());
            bundle.putString("page_url", modelList.get(position).getHome_url());
            bundle.putString("journalcode", modelList.get(position).getJournalcode());
            bundle.putString("rel_keyword", modelList.get(position).getRel_keyword());
            bundle.putString("journal_logo", modelList.get(position).getJournal_logo());
            Navigation.findNavController(v).navigate(R.id.dashBoardFragment,bundle);
        });*/

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        DashboardListItemBinding rowItemBinding;

        public ViewHolder(@NonNull DashboardListItemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
        }
    }
}
