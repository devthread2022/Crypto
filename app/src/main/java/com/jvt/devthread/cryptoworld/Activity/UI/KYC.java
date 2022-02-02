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
import com.jvt.devthread.cryptoworld.Activity.Model.PanDetailsModel;
import com.jvt.devthread.cryptoworld.Activity.Model.UserPanVerificationModel;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentKYCBinding;

import java.util.Random;

public class KYC extends Fragment {
    private FragmentKYCBinding binding;
    String panNumber, nameOnPan, dob, email,uid,userUid, requestNumber;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentKYCBinding.inflate(inflater,container,false);
       View view = binding.getRoot();
       uid = firebaseAuth.getCurrentUser().getUid();
       binding.verify.setOnClickListener(view1 -> {
           panNumber = binding.panNumber.getText().toString();
           nameOnPan = binding.name.getText().toString();
           dob = binding.dob.getText().toString();
           email = binding.email.getText().toString();
           if (panNumber.isEmpty()){
               binding.panNumber.requestFocus();
               binding.panNumber.setError("PAN Number");
           }else if (nameOnPan.isEmpty()){
               binding.name.requestFocus();
               binding.name.setError("Name as on PAN");
           }else if (dob.isEmpty()){
               binding.dob.requestFocus();
               binding.dob.setError("DoB as on PAN");
           }else if (email.isEmpty()){
               binding.email.requestFocus();
               binding.email.setError("Email Id");
           }else {
               PanDetailsModel panDetailsModel = new PanDetailsModel(panNumber, nameOnPan, dob, email);
               databaseReference.child("Users").child(uid).child("PanDetails").setValue(panDetailsModel);
               userUid = uid;
               Random random = new Random();
               int i = random.nextInt(9999999)+1000000;
               requestNumber = String.valueOf(i);
               DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
               UserPanVerificationModel panVerificationModel = new UserPanVerificationModel(panNumber, nameOnPan, dob, email, userUid, requestNumber);
               databaseReference1.child("PanVerificationRequest").child(requestNumber).setValue(panVerificationModel);
               Toast.makeText(getContext(), "Request Submitted...", Toast.LENGTH_SHORT).show();
           }
       });
       return view;
    }
}