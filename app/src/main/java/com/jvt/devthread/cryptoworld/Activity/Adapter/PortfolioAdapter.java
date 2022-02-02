package com.jvt.devthread.cryptoworld.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jvt.devthread.cryptoworld.Activity.Dashboard.Common;
import com.jvt.devthread.cryptoworld.Activity.Model.CoinBuyModel;
import com.jvt.devthread.cryptoworld.Activity.UI.BuyCoin;
import com.jvt.devthread.cryptoworld.Activity.UI.SellCoin;
import com.jvt.devthread.cryptoworld.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder> {
    public Context context;
    private List<CoinBuyModel> coinBuyModelList;

    public PortfolioAdapter(Context context, List<CoinBuyModel> coinBuyModelList) {
        this.context = context;
        this.coinBuyModelList = coinBuyModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CoinBuyModel coinBuyModel = coinBuyModelList.get(position);
        holder.name.setText(coinBuyModel.getName());
        holder.symbol.setText(coinBuyModel.getSymbol());
        holder.price.setText(convertValueInIndianCurrency(coinBuyModel.getPurchasedAmount()));
        holder.orderType.setText(coinBuyModel.getOrderType());
        holder.itemView.setOnClickListener(view -> {
            Common.coinName = coinBuyModelList.get(position).getName();
            Common.coinSymbol = coinBuyModelList.get(position).getSymbol();
            Common.coinPrice = coinBuyModelList.get(position).getPrice();
            Common.coinId = coinBuyModelList.get(position).getCoinId();
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment buyCoin = new SellCoin();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, buyCoin)
                    .addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return coinBuyModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, symbol, price, orderType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            symbol = itemView.findViewById(R.id.symbol);
            price = itemView.findViewById(R.id.price);
            orderType = itemView.findViewById(R.id.dominance);
        }
    }
    public String convertValueInIndianCurrency(double amount){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("hi", "IN"));
        return formatter.format(amount);
    }
}
