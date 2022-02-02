package com.jvt.devthread.cryptoworld.Activity.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentReferBinding;

public class Refer extends Fragment {
    private FragmentReferBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReferBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.refer.setOnClickListener(view1 -> {
            share();
        });
        return view;
    }
    public void share()
    {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Share with");
        String share_message="Download our app to get Rs.50 BTC for free.";
        intent.putExtra(Intent.EXTRA_TEXT,share_message);
        startActivity(Intent.createChooser(intent,"Share Via"));
    }
}