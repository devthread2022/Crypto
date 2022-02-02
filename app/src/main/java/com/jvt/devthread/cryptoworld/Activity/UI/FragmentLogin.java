package com.jvt.devthread.cryptoworld.Activity.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.devthread.cryptoworld.Activity.ForgotPassword;
import com.jvt.devthread.cryptoworld.Activity.HomeActivity;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentLoginBinding;

import org.jetbrains.annotations.NotNull;

public class FragmentLogin extends Fragment {
    private FragmentLoginBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        binding.passwordForget.setOnClickListener(v -> sendForgetPassword());
        binding.loginButton.setOnClickListener(this::loginUser);
        binding.showPassBtn.setOnClickListener(this::showHidePass);
        return view;
    }
    public void loginUser(View view)
    {
        String e = binding.email.getEditableText().toString();
        String p=binding.editPassword.getEditableText().toString();
        if (e.isEmpty())
        {
            binding.email.setError("Enter  Email");
            binding.email.requestFocus();
        }
        else if(p.isEmpty())
        {
            binding.editPassword.setError("Enter Password");
            binding.editPassword.requestFocus();
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(task -> {
                if(!task.isSuccessful())
                {

                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    checkEmailVerified(view);
                    if(firebaseAuth.getCurrentUser()!=null) {
                        String UID = firebaseAuth.getCurrentUser().getUid();
                    }

                }
            });
        }
    }
    public void checkEmailVerified(View view)
    {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        boolean emailFlag=firebaseUser.isEmailVerified();
        if (emailFlag)
        {
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();
        }
        else
        {
            verify();
            firebaseAuth.signOut();
        }

    }
    public void sendForgetPassword()
    {
        startActivity(new Intent(getActivity(), ForgotPassword.class));
    }
    public void verify()
    {
        Toast.makeText(getContext(), "Email verification link sent please check your mail..", Toast.LENGTH_SHORT).show();
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
    }
}