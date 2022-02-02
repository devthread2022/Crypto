package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.devthread.cryptoworld.Activity.Adapter.OrderAdapter;
import com.jvt.devthread.cryptoworld.Activity.Model.CoinBuyModel;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment {
    private FragmentHistoryBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private List<CoinBuyModel> coinBuyModelList = new ArrayList<>();
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        uid = firebaseAuth.getCurrentUser().getUid();
        binding.history.setHasFixedSize(true);
        binding.history.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        databaseReference.child("Users").child(uid).child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    coinBuyModelList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CoinBuyModel coinBuyModel = dataSnapshot.getValue(CoinBuyModel.class);
                        coinBuyModelList.add(coinBuyModel);
                    }
                    OrderAdapter orderAdapter = new OrderAdapter(getContext(),coinBuyModelList);
                    binding.history.setAdapter(orderAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}