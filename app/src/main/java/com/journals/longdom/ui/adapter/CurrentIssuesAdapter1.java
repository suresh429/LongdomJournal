package com.journals.longdom.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.journals.longdom.databinding.CurrentIssueItem1Binding;
import com.journals.longdom.databinding.CurrentIssueItemBinding;
import com.journals.longdom.model.CurrentIssueResponse;
import com.journals.longdom.model.HomeResponse;

import java.util.List;

import static android.content.ContentValues.TAG;

public class CurrentIssuesAdapter1 extends RecyclerView.Adapter<CurrentIssuesAdapter1.ViewHolder> {

    List<CurrentIssueResponse.CurrentissueDetailsBean> modelList;

    public CurrentIssuesAdapter1(List<CurrentIssueResponse.CurrentissueDetailsBean> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public CurrentIssuesAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CurrentIssueItem1Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CurrentIssuesAdapter1.ViewHolder holder, int position) {

        holder.rowItemBinding.txtIssueType.setText(modelList.get(position).getArt_type());
        holder.rowItemBinding.txtIssueTitle.setText(modelList.get(position).getTitle());
        holder.rowItemBinding.txtIssueAuthor.setText(modelList.get(position).getAuthor_names());
        holder.rowItemBinding.txtIssueDOI.setText("DOI : " + modelList.get(position).getDoi_num());

        if (modelList.get(position).getAbstractlink() != null && !modelList.get(position).getAbstractlink().equalsIgnoreCase("null") && !modelList.get(position).getAbstractlink().isEmpty() ) {
            holder.rowItemBinding.txtAbstract.setVisibility(View.VISIBLE);
        } else {
            holder.rowItemBinding.txtAbstract.setVisibility(View.GONE);
        }

        if (modelList.get(position).getPdflink() != null && !modelList.get(position).getPdflink().equalsIgnoreCase("null") && !modelList.get(position).getPdflink().isEmpty() ) {
            holder.rowItemBinding.txtPDF.setVisibility(View.VISIBLE);
        } else {
            holder.rowItemBinding.txtPDF.setVisibility(View.GONE);
        }
        if (modelList.get(position).getFulltextlink() != null && !modelList.get(position).getFulltextlink().equalsIgnoreCase("null") && !modelList.get(position).getFulltextlink().isEmpty() ) {
            holder.rowItemBinding.txtFullText.setVisibility(View.VISIBLE);
        } else {
            holder.rowItemBinding.txtFullText.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CurrentIssueItem1Binding rowItemBinding;

        public ViewHolder(@NonNull CurrentIssueItem1Binding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
        }
    }
}
