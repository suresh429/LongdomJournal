package com.journals.longdom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.journals.longdom.databinding.CurrentIssueItemBinding;
import com.journals.longdom.model.HomeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.journals.longdom.network.RetrofitService.IMAGE_URL;

public class CurrentIssuesAdapter extends RecyclerView.Adapter<CurrentIssuesAdapter.ViewHolder> {

    List<HomeResponse.CurrissueHighlightsBean> modelList;

    public CurrentIssuesAdapter(List<HomeResponse.CurrissueHighlightsBean> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public CurrentIssuesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CurrentIssueItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentIssuesAdapter.ViewHolder holder, int position) {

        holder.rowItemBinding.txtIssueType.setText(modelList.get(position).getArt_type());
        holder.rowItemBinding.txtIssueTitle.setText(modelList.get(position).getTitle());
        holder.rowItemBinding.txtIssueAuthor.setText(modelList.get(position).getAuthor_names());

        if (modelList.get(position).getAbstractlink() != null || !modelList.get(position).getAbstractlink().equalsIgnoreCase("")){
            holder.rowItemBinding.txtAbstract.setVisibility(View.VISIBLE);
        }else {
            holder.rowItemBinding.txtAbstract.setVisibility(View.GONE);
        }

        if (modelList.get(position).getPdflink() != null || !modelList.get(position).getPdflink().equalsIgnoreCase("")){
            holder.rowItemBinding.txtPDF.setVisibility(View.VISIBLE);
        }else {
            holder.rowItemBinding.txtPDF.setVisibility(View.GONE);
        }


        if (modelList.get(position).getFulltextlink() instanceof String) {
            holder.rowItemBinding.txtFullText.setVisibility(View.VISIBLE);
        }else {
            holder.rowItemBinding.txtFullText.setVisibility(View.GONE);
        }


        /*if (modelList.get(position).getFulltextlink() != null ){
            holder.rowItemBinding.txtFullText.setVisibility(View.VISIBLE);
        }else {
            holder.rowItemBinding.txtFullText.setVisibility(View.GONE);
        }*/

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CurrentIssueItemBinding rowItemBinding;

        public ViewHolder(@NonNull CurrentIssueItemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
        }
    }
}
