package com.journals.longdom.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.journals.longdom.databinding.CatgeogryListItemBinding;
import com.journals.longdom.databinding.CurrentIssueItemBinding;
import com.journals.longdom.model.CategoryResponse;
import com.journals.longdom.model.HomeResponse;
import java.util.List;

import static com.journals.longdom.network.RetrofitService.IMAGE_CATEGORY_URL;
import static com.journals.longdom.network.RetrofitService.IMAGE_HOME_URL;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    List<CategoryResponse.SubcatDetailsBean> modelList;

    Context context;
    public CategoryListAdapter(List<CategoryResponse.SubcatDetailsBean> modelList,Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CatgeogryListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {

        holder.rowItemBinding.txtCategoryTitle.setText(modelList.get(position).getManagejournal());
        holder.rowItemBinding.txtCategoryEdition.setText(modelList.get(position).getVol_issue_name());
        Glide.with(context)
                .load(IMAGE_CATEGORY_URL +modelList.get(position).getFlyerimg())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.rowItemBinding.imgJournal.setImageDrawable(resource);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {


                    }

                });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CatgeogryListItemBinding rowItemBinding;

        public ViewHolder(@NonNull CatgeogryListItemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
        }
    }
}