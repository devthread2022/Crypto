package com.jvt.devthread.cryptoworld.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jvt.devthread.cryptoworld.Activity.Model.CoinBuyModel;
import com.jvt.devthread.cryptoworld.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    public Context context;
    private List<CoinBuyModel> coinBuyModelList;

    public OrderAdapter(Context context, List<CoinBuyModel> coinBuyModelList) {
        this.context = context;
        this.coinBuyModelList = coinBuyModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_history_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CoinBuyModel coinBuyModel = coinBuyModelList.get(position);
        holder.oType.setText(coinBuyModel.getOrderType());
        holder.date.setText(coinBuyModel.getDate());
        holder.price.setText(convertValueInIndianCurrency(coinBuyModel.getPurchasedAmount()));
        holder.symbol.setText(coinBuyModel.getSymbol());
        holder.name.setText(coinBuyModel.getName());
        holder.unit.setText(coinBuyModel.getUnit());
    }

    @Override
    public int getItemCount() {
        return coinBuyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView oType, date, price, unit, name, symbol;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            oType = itemView.findViewById(R.id.orderType);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.orderValue);
            unit = itemView.findViewById(R.id.unit);
            name = itemView.findViewById(R.id.name);
            symbol = itemView.findViewById(R.id.symbol);

        }
    }
    public String convertValueInIndianCurrency(double amount){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("hi", "IN"));
        return formatter.format(amount);
    }
}
