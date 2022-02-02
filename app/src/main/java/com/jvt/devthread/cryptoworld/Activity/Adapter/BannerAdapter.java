package com.jvt.devthread.cryptoworld.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jvt.devthread.cryptoworld.Activity.Model.BannerModel;
import com.jvt.devthread.cryptoworld.R;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    public Context context;
    private List<BannerModel> bannerModelList;

    public BannerAdapter(Context context, List<BannerModel> bannerModelList) {
        this.context = context;
        this.bannerModelList = bannerModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BannerModel bannerModel = bannerModelList.get(position);
        Glide.with(context).load(bannerModel.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return bannerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
