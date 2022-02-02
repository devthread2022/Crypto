package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jvt.devthread.cryptoworld.Activity.Model.BankDetailModel;
import com.jvt.devthread.cryptoworld.Activity.Model.PanDetailsModel;
import com.jvt.devthread.cryptoworld.Activity.Model.PersonalInfoModel;
import com.jvt.devthread.cryptoworld.Activity.Model.PortfolioModel;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentSignUpBinding;

import java.util.Objects;

public class FragmentSignUp extends Fragment {
    private FragmentSignUpBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceUsers,databaseReferenceInvest,databaseReferencePan,databaseReferenceBank;
    String name, uid, email, mobile, address, kycStatus, profilePic,gender;
    String bankName, accountNumber, IfsCode;
    String panNumber, nameOnPan, dob;
    double currentValue, investedValue, gainLossValue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference();
        databaseReferenceBank = FirebaseDatabase.getInstance().getReference();
        databaseReferenceInvest = FirebaseDatabase.getInstance().getReference();
        databaseReferencePan = FirebaseDatabase.getInstance().getReference();
        binding.signUpTieEmail.setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS | EditorInfo.TYPE_TEXT_VARIATION_FILTER);

        binding.signUpButton.setOnClickListener(this::signUpUser);
        binding.showPassBtn.setOnClickListener(this::showHidePass);
        binding.showConfirmPassBtn.setOnClickListener(this::showHidePass);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                Log.w("getInstanceId failed",task.getException());
                return;
            }
        });
        return view;
    }
    public void signUpUser(View v)
    {
        final String email1=binding.signUpTieEmail.getText().toString();
        final String password=binding.editPassword.getText().toString();
        final String confirmPassword=binding.editConfirmPassword.getText().toString();
        if (email1.isEmpty())
        {
            binding.signUpTieEmail.setError("Enter Email");
            binding.signUpTieEmail.requestFocus();
        }
        else if (password.isEmpty())
        {
            binding.editPassword.setError("Enter Password");
            binding.editPassword.requestFocus();
        }
        else if(confirmPassword.isEmpty())
        {

            binding.editConfirmPassword.setError("Enter Password");
            binding.editConfirmPassword.requestFocus();
        }
        else {
            if (password.equals(confirmPassword)) {
                firebaseAuth.createUserWithEmailAndPassword(email1, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //storing data into realTime database
                        String UID= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                        name = "";
                        uid = UID;
                        email = email1;
                        mobile = "";
                        address ="";
                        kycStatus = "Pending";
                        profilePic = "";
                        gender = "";
                        PersonalInfoModel personalInfoModel = new PersonalInfoModel(name, uid, email, mobile, address, kycStatus, profilePic,gender);
                        databaseReferenceUsers.child("Users").child(uid).child("PersonalInfo").setValue(personalInfoModel);
                        bankName = "";
                        accountNumber = "";
                        IfsCode = "";
                        BankDetailModel bankDetailModel = new BankDetailModel(bankName,accountNumber,IfsCode);
                        databaseReferenceBank.child("Users").child(uid).child("BankDetails").setValue(bankDetailModel);
                        panNumber = "";
                        nameOnPan = "";
                        dob = "";
                        email = email1;
                        PanDetailsModel panDetailsModel = new PanDetailsModel(panNumber,nameOnPan,dob,email);
                        databaseReferencePan.child("Users").child(uid).child("PanDetails").setValue(panDetailsModel);
                        currentValue = 0.0;
                        investedValue = 0.0;
                        gainLossValue = 0.0;
                        PortfolioModel portfolioModel = new PortfolioModel(currentValue,investedValue,gainLossValue);
                        databaseReferenceInvest.child("Users").child(uid).child("Portfolio").setValue(portfolioModel);
                        sendEmailVerification(v);

                    } else {
                        Toast.makeText(getActivity(), "Registration failed. Please try again..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                Toast.makeText(getActivity(), "Password doesn't match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendEmailVerification(View v)
    {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    sent();
                    firebaseAuth.signOut();
                }
                else {
                    Toast.makeText(getActivity(), "Email did not send!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void sent()
    {
        Toast.makeText(getContext(), "Email verification link sent. Please check mail..", Toast.LENGTH_SHORT).show();
    }

    public void showHidePass(View view){

        if(view.getId()==R.id.show_pass_btn){
            if(binding.editPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                binding.editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                binding.editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
        if(view.getId()==R.id.show_confirm_pass_btn){
            if(binding.editConfirmPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                binding.editConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                binding.editConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}