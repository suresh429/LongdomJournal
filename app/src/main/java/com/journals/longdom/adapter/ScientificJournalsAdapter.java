package com.journals.longdom.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.journals.longdom.databinding.ScientificJournalItemBinding;
import com.journals.longdom.model.HomeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.journals.longdom.network.RetrofitService.IMAGE_URL;

public class ScientificJournalsAdapter extends RecyclerView.Adapter<ScientificJournalsAdapter.ViewHolder> {

    List<HomeResponse.CatDetailsBean> modelList;

    public ScientificJournalsAdapter(List<HomeResponse.CatDetailsBean> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ScientificJournalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ScientificJournalItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScientificJournalsAdapter.ViewHolder holder, int position) {

        holder.rowItemBinding.txtJournalName.setText(modelList.get(position).getCat_name());
        Picasso.get().load(IMAGE_URL +modelList.get(position).getCat_img()).into(holder.rowItemBinding.imgJournal);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ScientificJournalItemBinding rowItemBinding;

        public ViewHolder(@NonNull ScientificJournalItemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
        }
    }
}
