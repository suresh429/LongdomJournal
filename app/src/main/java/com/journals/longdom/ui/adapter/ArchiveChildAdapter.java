package com.journals.longdom.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.journals.longdom.databinding.ArchiveChildListItemBinding;
import com.journals.longdom.databinding.InpressItemBinding;
import com.journals.longdom.model.ArchiveChildItem;
import com.journals.longdom.model.InPressResponse;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ArchiveChildAdapter extends RecyclerView.Adapter<ArchiveChildAdapter.ViewHolder> {

    List<ArchiveChildItem> modelList;

    public ArchiveChildAdapter(List<ArchiveChildItem> modelList) {
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

        holder.rowItemBinding.txtArchiveChildName.setText(modelList.get(position).getChildItemTitle());

        holder.rowItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "YEAR: "+modelList.get(position).getYear()+
                        "VOLUME: "+modelList.get(position).getVol()+
                        "ISSUE: "+modelList.get(position).getIssue()+
                        "JOURNAl: "+modelList.get(position).getJournal());
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
