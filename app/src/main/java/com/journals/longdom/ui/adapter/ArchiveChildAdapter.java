package com.journals.longdom.ui.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.journals.longdom.R;
import com.journals.longdom.databinding.ArchiveChildListItemBinding;
import com.journals.longdom.model.ArchiveResponse;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ArchiveChildAdapter extends RecyclerView.Adapter<ArchiveChildAdapter.ViewHolder> {

    List<ArchiveResponse.ArchiveYearsBean.ArchiveDetailsBean> modelList;

    public ArchiveChildAdapter(List<ArchiveResponse.ArchiveYearsBean.ArchiveDetailsBean> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ArchiveChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ArchiveChildListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ArchiveChildAdapter.ViewHolder holder, int position) {

        holder.rowItemBinding.txtArchiveChildName.setText(modelList.get(position).getVol_issue_name());

        holder.rowItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("year",modelList.get(position).getYear());
                bundle.putString("volume",modelList.get(position).getVol());
                bundle.putString("issue",modelList.get(position).getIssue());
                bundle.putString("journalcode",modelList.get(position).getJournal());
                bundle.putString("ActionBarTitle",modelList.get(position).getVol_issue_name());
                Navigation.findNavController(v).navigate(R.id.volumeIssueFragment,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ArchiveChildListItemBinding rowItemBinding;

        public ViewHolder(@NonNull ArchiveChildListItemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
        }
    }
}
