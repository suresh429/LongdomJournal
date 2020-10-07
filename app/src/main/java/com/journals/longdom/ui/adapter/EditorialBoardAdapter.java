package com.journals.longdom.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.journals.longdom.R;
import com.journals.longdom.databinding.EditorialBoardItemBinding;
import com.journals.longdom.databinding.ScientificJournalItemBinding;
import com.journals.longdom.model.EditorialBoardResponse;
import com.journals.longdom.model.HomeResponse;

import java.util.List;

import static com.journals.longdom.network.RetrofitService.IMAGE_HOME_URL;

public class EditorialBoardAdapter extends RecyclerView.Adapter<EditorialBoardAdapter.ViewHolder> {

    List<EditorialBoardResponse.EditorialboardarrBean> modelList;

    Context context;

    public EditorialBoardAdapter(List<EditorialBoardResponse.EditorialboardarrBean> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public EditorialBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(EditorialBoardItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EditorialBoardAdapter.ViewHolder holder, int position) {

        holder.rowItemBinding.txtName.setText(modelList.get(position).getEname() + "  " + modelList.get(position).getE_qlf());

        if (modelList.get(position).getEditor_type() != null && !modelList.get(position).getEditor_type().equalsIgnoreCase("null") && !modelList.get(position).getEditor_type().isEmpty()) {
            holder.rowItemBinding.txtView1.setText(modelList.get(position).getEditor_type());
        } else {
            holder.rowItemBinding.txtView1.setVisibility(View.GONE);
        }
        if (modelList.get(position).getEditor_dept() != null && !modelList.get(position).getEditor_dept().equalsIgnoreCase("null") && !modelList.get(position).getEditor_dept().isEmpty()) {
            holder.rowItemBinding.txtView2.setText(modelList.get(position).getEditor_desig() + " " + modelList.get(position).getEditor_dept());
        } else {
            holder.rowItemBinding.txtView2.setVisibility(View.GONE);
        }

        if (modelList.get(position).getEx_unv_name() != null && !modelList.get(position).getEx_unv_name().equalsIgnoreCase("null") && !modelList.get(position).getEx_unv_name().isEmpty()) {
            holder.rowItemBinding.txtView3.setText(modelList.get(position).getUname() + "" + modelList.get(position).getEx_unv_name() + "" + modelList.get(position).getCountry_name());
        } else {
            holder.rowItemBinding.txtView3.setVisibility(View.GONE);
        }


        Glide.with(context)
                .load(modelList.get(position).getPhoto())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.rowItemBinding.imgPhoto.setImageDrawable(resource);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {


                    }

                });
        holder.rowItemBinding.btnBiography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("value", modelList.get(position).getBiography());
                bundle.putString("ActionBarTitle", "Biography");

                if (modelList.get(position).getBiography() != null && !modelList.get(position).getBiography().equalsIgnoreCase("null") && !modelList.get(position).getBiography().isEmpty()) {
                    Navigation.findNavController(v).navigate(R.id.biographyResearchFragment, bundle);
                } else {
                    Toast.makeText(context, "No Biography Found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.rowItemBinding.btnResearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("value", modelList.get(position).getResearch_interest());
                bundle.putString("ActionBarTitle", "Research Interest");
                if (modelList.get(position).getResearch_interest() != null && !modelList.get(position).getResearch_interest().equalsIgnoreCase("null") && !modelList.get(position).getResearch_interest().isEmpty()) {
                    Navigation.findNavController(v).navigate(R.id.biographyResearchFragment, bundle);
                } else {
                    Toast.makeText(context, "No Research Interest Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditorialBoardItemBinding rowItemBinding;

        public ViewHolder(@NonNull EditorialBoardItemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
        }
    }
}
