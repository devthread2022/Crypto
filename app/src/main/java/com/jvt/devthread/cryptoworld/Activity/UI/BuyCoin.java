package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvt.devthread.cryptoworld.Activity.Dashboard.Common;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentBuyCoinBinding;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BuyCoin extends Fragment {
    private FragmentBuyCoinBinding binding;
    String amount;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static  Fragment activeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentBuyCoinBinding.inflate(inflater,container,false);
       View view = binding.getRoot();
       binding.coinName.setText(Common.coinName);
       binding.min.setText("Min."+convertValueInIndianCurrency(100));
       binding.max.setText("Max."+convertValueInIndianCurrency(250000));
       binding.balance.setText("Balance "+convertValueInIndianCurrency(0));
       binding.preview.setOnClickListener(view1 -> {
           amount = binding.amount.getText().toString();
           double pAmount = Double.parseDouble(amount);
           Common.purchasedAmount = pAmount;
           if (amount.isEmpty()){
               binding.amount.requestFocus();
               binding.amount.setError("Amount..");
           }else {
               Fragment confirm = new ConfirmBuy();
               loadFragment(confirm,"ConfirmBuy");
           }

       });
       return view;
    }
    public String convertValueInIndianCurrency(double amount){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("hi", "IN"));
        return formatter.format(amount);
    }
    private void loadFragment(Fragment fragment, String tag) {
        executorService.execute(() -> {
            if (fragment != null) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();

            }
            handler.post(() -> {
                activeFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            });
        });
    }
}