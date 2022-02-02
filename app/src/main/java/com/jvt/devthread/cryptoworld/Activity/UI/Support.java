package com.jvt.devthread.cryptoworld.Activity.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentSupportBinding;

public class Support extends Fragment {
    private FragmentSupportBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupportBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.email.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","devthread2020@gmail.com", null));
            intent.putExtra(Intent.EXTRA_SUBJECT,"Report" );
            intent.putExtra(Intent.EXTRA_TEXT, "your message");
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        });
        binding.call.setOnClickListener(view1 -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9900367097")); //tel:9900367097
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, 10);
            }
            else {
                try{
                    startActivity(callIntent);  //call activity and make phone call
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getActivity(),"Your Activity is not found",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}