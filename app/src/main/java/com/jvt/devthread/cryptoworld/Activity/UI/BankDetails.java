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
import com.jvt.devthread.cryptoworld.Activity.Model.BankDetailModel;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentBankDetailsBinding;

public class BankDetails extends Fragment {
    private FragmentBankDetailsBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String bankName, accountNumber,confirmAccount, IfsCode,uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentBankDetailsBinding.inflate(inflater,container,false);
       View view = binding.getRoot();
       uid = firebaseAuth.getCurrentUser().getUid();
       binding.verify.setOnClickListener(view1 -> {
           bankName = binding.bank.getText().toString();
           accountNumber = binding.account.getText().toString();
           confirmAccount = binding.confirmAccount.getText().toString();
           IfsCode = binding.ifsc.getText().toString();
           if (bankName.isEmpty()){
               binding.bank.requestFocus();
               binding.bank.setError("Bank Name");
           }else if (accountNumber.isEmpty()){
               binding.account.requestFocus();
               binding.account.setError("Account Number");
           }else if (confirmAccount.isEmpty()){
               binding.confirmAccount.requestFocus();
               binding.confirmAccount.setError("Account Number");
           }else if (IfsCode.isEmpty()){
               binding.ifsc.requestFocus();
               binding.ifsc.setError("IFSC");
           }else if (!accountNumber.equals(confirmAccount)){
               Toast.makeText(getContext(), "Account number does not match please check..", Toast.LENGTH_SHORT).show();
           }else {
               BankDetailModel bankDetailModel = new BankDetailModel(bankName, accountNumber, IfsCode);
               databaseReference.child("Users").child(uid).child("BankDetails").setValue(bankDetailModel);
               Toast.makeText(getContext(), "Saved successfully..", Toast.LENGTH_SHORT).show();
           }
       });
       return view;
    }
}