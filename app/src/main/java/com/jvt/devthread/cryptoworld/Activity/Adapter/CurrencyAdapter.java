package com.jvt.devthread.cryptoworld.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jvt.devthread.cryptoworld.Activity.Dashboard.Common;
import com.jvt.devthread.cryptoworld.Activity.Model.CurrencyModel;
import com.jvt.devthread.cryptoworld.Activity.UI.BuyCoin;
import com.jvt.devthread.cryptoworld.R;

import java.text.DecimalFormat;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    public Context context;
    private List<CurrencyModel> currencyModelList;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public CurrencyAdapter(Context context, List<CurrencyModel> currencyModelList) {
        this.context = context;
        this.currencyModelList = currencyModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurrencyModel currencyModel = currencyModelList.get(position);
        holder.name.setText(currencyModel.getName());
        holder.symbol.setText(currencyModel.getSymbol());
        holder.price.setText("$"+decimalFormat.format(currencyModel.getPrice()));
        holder.dominance.setText(currencyModel.getDominance()+"%");
        holder.itemView.setOnClickListener(view -> {
            Common.coinName = currencyModelList.get(position).getName();
            Common.coinSymbol = currencyModelList.get(position).getSymbol();
            Common.coinPrice = currencyModelList.get(position).getPrice();
            Common.coinId = currencyModelList.get(position).getCoinId();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment buyCoin = new BuyCoin();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, buyCoin)
                    .addToBackStack(null).commit();
        });

    }

    @Override
    public int getItemCount() {
        return currencyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, symbol, price,dominance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            symbol = itemView.findViewById(R.id.symbol);
            price = itemView.findViewById(R.id.price);
            dominance = itemView.findViewById(R.id.dominance);
        }
    }
}
