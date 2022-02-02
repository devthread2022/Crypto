package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.devthread.cryptoworld.Activity.Model.PersonalInfoModel;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentPersonalDetailsBinding;

public class PersonalDetails extends Fragment {
    private FragmentPersonalDetailsBinding binding;
    String name, uid, email, mobile, address, kycStatus, profilePic,gender="None";
    String kd;
    DatabaseReference databaseReferenceUsers,databaseReference;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonalDetailsBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference();
        databaseReferenceUsers.child("Users").child(uid).child("PersonalInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String e = snapshot.child("email").getValue().toString();
                    kd = snapshot.child("kycStatus").getValue().toString();
                    binding.email.setText(e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.verify.setOnClickListener(view1 -> {
            name = binding.name.getText().toString();
            email = binding.email.getText().toString();
            mobile = binding.mobile.getText().toString();
            address = binding.address.getText().toString();
            binding.male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        gender = "Male";
                    }
                }
            });
            binding.female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        gender = "Female";
                    }
                }
            });
            binding.notSpecify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        gender = "Not to specify";
                    }
                }
            });
            if (name.isEmpty()){
                binding.name.requestFocus();
                binding.name.setError("Name");
            }else if (mobile.isEmpty()){
                binding.mobile.requestFocus();
                binding.mobile.setError("Mobile");
            }else if (email.isEmpty()){
                binding.email.requestFocus();
                binding.email.setError("Email");
            }else if (address.isEmpty()){
                binding.address.requestFocus();
                binding.address.setError("Address");
            }else if (gender.equals("None")){
                Toast.makeText(getContext(), "Select gender..", Toast.LENGTH_SHORT).show();
            }else {
                kycStatus = kd;
                profilePic = "";
                PersonalInfoModel personalInfoModel = new PersonalInfoModel(name, uid, email, mobile, address, kycStatus, profilePic,gender);
                databaseReference.child("Users").child(uid).child("PersonalInfo").setValue(personalInfoModel);
                Toast.makeText(getContext(), "Updated successfully..", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}