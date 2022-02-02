package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvt.devthread.cryptoworld.Activity.Adapter.ViewPagerAdapter;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentMarketBinding;

public class Market extends Fragment {
    private FragmentMarketBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMarketBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        initViews();
        return view;
    }
    private void initViews() {
        setupViewPager(binding.pager);
        binding.tabLayout.setupWithViewPager(binding.pager);
    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new AllMarket(),"All");
        viewPagerAdapter.addFragment(new MarketGainers(),"Gainers");
        viewPagerAdapter.addFragment(new MarketLoosers(),"Losers");
        viewPager.setAdapter(viewPagerAdapter);
    }
}