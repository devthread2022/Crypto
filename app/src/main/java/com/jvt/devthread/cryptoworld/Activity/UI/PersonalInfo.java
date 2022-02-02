package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentPersonalInfoBinding;

public class PersonalInfo extends Fragment {
    private FragmentPersonalInfoBinding binding;
    DatabaseReference databaseReference1,databaseReference;
    FirebaseAuth firebaseAuth;
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonalInfoBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").child(uid).child("PersonalInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String mobile = snapshot.child("mobile").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    binding.name.setText(name);
                    binding.mobile.setText(mobile);
                    binding.email.setText(email);
                    binding.address.setText(address);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference1.child("Users").child(uid).child("PanDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String dob = snapshot.child("dob").getValue().toString();
                    String pan = snapshot.child("panNumber").getValue().toString();
                    binding.dob.setText(dob);
                    binding.pan.setText(pan);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}