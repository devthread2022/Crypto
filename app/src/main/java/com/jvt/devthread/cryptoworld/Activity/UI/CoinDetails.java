package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentCoinDetailsBinding;

public class CoinDetails extends Fragment {
    private FragmentCoinDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoinDetailsBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }
}