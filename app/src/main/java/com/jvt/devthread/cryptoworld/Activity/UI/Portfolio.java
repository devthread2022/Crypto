package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.devthread.cryptoworld.Activity.Adapter.PortfolioAdapter;
import com.jvt.devthread.cryptoworld.Activity.Model.CoinBuyModel;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentPortfolioBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Portfolio extends Fragment {

    private FragmentPortfolioBinding binding;
    DatabaseReference databaseReference1,databaseReference;
    FirebaseAuth firebaseAuth;
    String uid;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static  Fragment activeFragment;
    private List<CoinBuyModel> coinBuyModelList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPortfolioBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference1.child("Users").child(uid).child("Portfolio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String cvalue = snapshot.child("currentValue").getValue().toString();
                    String ivalue = snapshot.child("investedValue").getValue().toString();
                    String gvalue = snapshot.child("gainLossValue").getValue().toString();
                    binding.cv.setText(convertValueInIndianCurrency(Double.parseDouble(cvalue)));
                    binding.iv.setText(convertValueInIndianCurrency(Double.parseDouble(ivalue)));
                    binding.gv.setText(convertValueInIndianCurrency(Double.parseDouble(gvalue)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.order.setOnClickListener(view1 -> {
            Fragment order = new History();
            loadFragment(order,"History");
        });
        binding.orderRecycler.setHasFixedSize(true);
        binding.orderRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        databaseReference.child("Users").child(uid).child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    coinBuyModelList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CoinBuyModel coinBuyModel = dataSnapshot.getValue(CoinBuyModel.class);
                        coinBuyModelList.add(coinBuyModel);
                    }
                    PortfolioAdapter portfolioAdapter = new PortfolioAdapter(getContext(),coinBuyModelList);
                    binding.orderRecycler.setAdapter(portfolioAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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