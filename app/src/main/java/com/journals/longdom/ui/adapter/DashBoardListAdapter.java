package com.journals.longdom.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.journals.longdom.databinding.DashboardListItemBinding;
import com.journals.longdom.model.DashBoardModel;

import java.util.List;


public class DashBoardListAdapter extends RecyclerView.Adapter<DashBoardListAdapter.ViewHolder> {

    List<DashBoardModel> modelList;
    Context context;
    DashBoardListener dashBoardListener;

    public DashBoardListAdapter(List<DashBoardModel> modelList, Context context, DashBoardListener dashBoardListener) {
        this.modelList = modelList;
        this.context = context;
        this.dashBoardListener = dashBoardListener;
    }


    @NonNull
    @Override
    public DashBoardListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DashboardListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardListAdapter.ViewHolder holder, int position) {

        holder.rowItemBinding.txtDasBoardTitle.setText(modelList.get(position).getDashBoardTitle());
        holder.rowItemBinding.getRoot().setOnClickListener(v -> {
          dashBoardListener.onItemClick(modelList,position);
        });

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

    public interface DashBoardListener{
        void onItemClick(List<DashBoardModel> dashBoardModel, int position);
    }
}
