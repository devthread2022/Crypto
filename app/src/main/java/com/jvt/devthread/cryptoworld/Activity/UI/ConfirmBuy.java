package com.jvt.devthread.cryptoworld.Activity.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.devthread.cryptoworld.Activity.Dashboard.AmountSummaryToHomeActivity;
import com.jvt.devthread.cryptoworld.Activity.Dashboard.Common;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentConfirmBuyBinding;
import com.razorpay.Checkout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class ConfirmBuy extends Fragment {
    private FragmentConfirmBuyBinding binding;
    private static DecimalFormat decimalFormat = new DecimalFormat("#.########");
    AmountSummaryToHomeActivity mCallback;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmBuyBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(uid).child("Portfolio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String cv = snapshot.child("currentValue").getValue().toString();
                    String iv = snapshot.child("investedValue").getValue().toString();
                    String gv = snapshot.child("gainLossValue").getValue().toString();
                    double gvv = Double.parseDouble(gv);
                    double cvv = Double.parseDouble(cv);
                    double ivv = Double.parseDouble(iv);
                    Common.invested = ivv;
                    Common.current = cvv;
                    Common.gain = gvv;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.coinName.setText(Common.coinName);
        Random random = new Random();
        int i = random.nextInt(999999999)+100000000;
        String ord = "CWO"+String.valueOf(i);
        binding.orderID.setText(ord);
        binding.orderAmount.setText(convertValueInIndianCurrency(Common.purchasedAmount));
        binding.buy.setOnClickListener(view1 -> {
            Common.orderId = ord;
            Common.orderType = "BUY";
            Common.unit = decimalFormat.format(1);
            paymentGateway(ord);
        });
        return view;
    }

    private void paymentGateway(String ord) {
        String net_amount = String.valueOf(Common.purchasedAmount).replace(",", "");

        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "CryptoWorld");
            options.put("description", "Razorpay Payment Gateway");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://firebasestorage.googleapis.com/v0/b/devthread-f1aa3.appspot.com/o/jvt_logo.png?alt=media&token=f12a96a4-3eaf-4ef5-8175-2748d741a6d2");
            options.put("currency", "INR");

            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(net_amount);
            total = total * 100;
            options.put("amount", total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

            options.put("prefill", preFill);

            mCallback.amount(net_amount,ord);
            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String convertValueInIndianCurrency(double amount){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("hi", "IN"));
        return formatter.format(amount);
    }
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (AmountSummaryToHomeActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }
    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }
}