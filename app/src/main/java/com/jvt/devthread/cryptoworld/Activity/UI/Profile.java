package com.jvt.devthread.cryptoworld.Activity.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
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
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentProfileBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Profile extends Fragment {
    private FragmentProfileBinding binding;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static  Fragment activeFragment;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
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
        binding.about.setOnClickListener(view1 -> {
            /*Fragment about = new About();
            loadFragment(about,"About");*/
            Toast.makeText(getContext(), "Will be available soon..", Toast.LENGTH_SHORT).show();
        });
        binding.profile.setOnClickListener(view1 -> {
            Fragment profile = new ProfileDetails();
            loadFragment(profile,"ProfileDetails");
        });
        binding.bank.setOnClickListener(view1 -> {
            Fragment bank = new BankDetails();
            loadFragment(bank,"BankDetails");
        });
        binding.history.setOnClickListener(view1 -> {
            Fragment history = new History();
            loadFragment(history,"History");
        });
        binding.kyc.setOnClickListener(view1 -> {
            Fragment kyc = new KYC();
            loadFragment(kyc,"KYC");
        });
        binding.support.setOnClickListener(view1 -> {
            Fragment support = new Support();
            loadFragment(support,"Support");
        });
        binding.edit.setOnClickListener(view1 -> {
            Fragment edit = new PersonalDetails();
            loadFragment(edit,"PersonalDetails");
        });
        return view;
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