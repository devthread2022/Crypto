package com.jvt.devthread.cryptoworld.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvt.devthread.cryptoworld.Activity.Model.CurrencyModel;
import com.jvt.devthread.cryptoworld.R;

import java.text.DecimalFormat;
import java.util.List;

public class PopularBuyingAdapter extends RecyclerView.Adapter<PopularBuyingAdapter.ViewHolder> {
    public Context context;
    private List<CurrencyModel> currencyModelList;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public PopularBuyingAdapter(Context context, List<CurrencyModel> currencyModelList) {
        this.context = context;
        this.currencyModelList = currencyModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurrencyModel currencyModel = currencyModelList.get(position);
        holder.name.setText(currencyModel.getName());
        holder.price.setText("$"+decimalFormat.format(currencyModel.getPrice()));

    }

    @Override
    public int getItemCount() {
        return currencyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
