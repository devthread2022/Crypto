package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jvt.devthread.cryptoworld.Activity.Dashboard.Common;
import com.jvt.devthread.cryptoworld.Activity.Model.CoinBuyModel;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentSellCoinBinding;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class SellCoin extends Fragment {
    private FragmentSellCoinBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String coinId, name, symbol,orderType, date, orderId,uid,unit,AmountPaid,orderID;
    double price,purchasedAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSellCoinBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        uid = firebaseAuth.getCurrentUser().getUid();
        binding.min.setText("Min."+convertValueInIndianCurrency(100));
        binding.max.setText("Max."+convertValueInIndianCurrency(250000));
        binding.preview.setOnClickListener(view1 -> {
            String amount = binding.amount.getText().toString();
            coinId = Common.coinId;
            name = Common.coinName;
            symbol = Common.coinSymbol;
            orderType = "SELL";
            Date date1 = Calendar.getInstance().getTime();
            date = String.valueOf(date1);
            Random random = new Random();
            int i = random.nextInt(999999999)+100000000;

            orderId = "CWO"+String.valueOf(i);
            unit = "1";
            price = Double.parseDouble(amount);
            purchasedAmount = Double.parseDouble(amount);
            CoinBuyModel coinBuyModel = new CoinBuyModel(coinId, name, symbol,orderType, date, orderId,unit,price,purchasedAmount);
            databaseReference.child("Users").child(uid).child("Orders").child(orderId).setValue(coinBuyModel);
            Toast.makeText(getContext(), "Sold placed successfully..", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
    public String convertValueInIndianCurrency(double amount){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("hi", "IN"));
        return formatter.format(amount);
    }
}