package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.devthread.cryptoworld.Activity.Adapter.ViewPagerAdapter;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentProfileDetailsBinding;

public class ProfileDetails extends Fragment {
    private FragmentProfileDetailsBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileDetailsBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        initViews();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(uid).child("PersonalInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String kyc = snapshot.child("kycStatus").getValue().toString();
                    String identification = snapshot.child("email").getValue().toString();
                    binding.identification.setText(identification);
                    if (kyc.equals("Pending")){
                        binding.kycStatus.setText("KYC Pending");
                    }else {
                        binding.kycStatus.setText("KYC Verified");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    private void initViews() {
        setupViewPager(binding.pager);
        binding.tabLayout.setupWithViewPager(binding.pager);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new PersonalInfo(),"Personal");
        viewPagerAdapter.addFragment(new BankInfo(),"Bank");
        viewPager.setAdapter(viewPagerAdapter);
    }
}