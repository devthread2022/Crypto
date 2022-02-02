
package com.jvt.devthread.cryptoworld.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.ActivityForgotPasswordBinding;

public class ForgotPassword extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        firebaseAuth = FirebaseAuth.getInstance();
        binding.resetPasswordSend.setOnClickListener(view1 -> link());
    }
    public void link()
    {
        String userEmail = binding.resetEmail.getText().toString();
        if(userEmail.equals("")){
            Toast.makeText(this, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                }else{
                    Toast.makeText(ForgotPassword.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}