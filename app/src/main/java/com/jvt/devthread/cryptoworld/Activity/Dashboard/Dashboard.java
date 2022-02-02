package com.jvt.devthread.cryptoworld.Activity.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jvt.devthread.cryptoworld.Activity.Adapter.BannerAdapter;
import com.jvt.devthread.cryptoworld.Activity.Adapter.CurrencyAdapter;
import com.jvt.devthread.cryptoworld.Activity.Adapter.PopularBuyingAdapter;
import com.jvt.devthread.cryptoworld.Activity.Adapter.PopularSellingAdapter;
import com.jvt.devthread.cryptoworld.Activity.Model.BannerModel;
import com.jvt.devthread.cryptoworld.Activity.Model.CurrencyModel;
import com.jvt.devthread.cryptoworld.Activity.UI.Market;
import com.jvt.devthread.cryptoworld.Activity.UI.Portfolio;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dashboard extends Fragment {
    private FragmentDashboardBinding binding;
    private ArrayList<CurrencyModel> currencyModelArrayList = new ArrayList<>();
    private PopularBuyingAdapter popularBuyingAdapter;
    private PopularSellingAdapter popularSellingAdapter;
    private List<BannerModel> bannerModelList = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference1;
    FirebaseAuth firebaseAuth;
    String uid;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static  Fragment activeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        binding.seeA.setOnClickListener(view1 -> {
            Fragment seeAll = new Market();
            loadFragment(seeAll,"Market");
        });
        binding.seeAll.setOnClickListener(view1 -> {
            Fragment seeAll = new Market();
            loadFragment(seeAll,"Market");
        });
        binding.buy.setOnClickListener(view1 -> {
            Fragment buy = new Market();
            loadFragment(buy,"Market");
        });
        binding.sell.setOnClickListener(view1 -> {
            Fragment portfolio = new Portfolio();
            loadFragment(portfolio,"Portfolio");
        });
        popularBuyingAdapter = new PopularBuyingAdapter(getContext(),currencyModelArrayList);
        binding.buyingRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.buyingRecycler.setAdapter(popularBuyingAdapter);
        popularSellingAdapter = new PopularSellingAdapter(getContext(),currencyModelArrayList);
        binding.sellingRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.sellingRecycler.setAdapter(popularSellingAdapter);
        binding.bannerRecycler.setHasFixedSize(true);
        binding.bannerRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        databaseReference.child("Banners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        BannerModel bannerModel = dataSnapshot.getValue(BannerModel.class);
                        bannerModelList.add(bannerModel);
                    }
                    BannerAdapter bannerAdapter = new BannerAdapter(getContext(),bannerModelList);
                    binding.bannerRecycler.setAdapter(bannerAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getPopularBuyingData();
        getPopularSellingData();
        databaseReference1.child("Users").child(uid).child("Portfolio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String cvalue = snapshot.child("currentValue").getValue().toString();
                    binding.value.setText(convertValueInIndianCurrency(Double.parseDouble(cvalue)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void getPopularSellingData() {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i=0; i<dataArray.length(); i++){
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        String coinId = dataObj.getString("id");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        String dominance = USD.getString("market_cap_dominance");
                        currencyModelArrayList.add(new CurrencyModel(coinId,name,symbol,dominance,price));
                    }
                    popularSellingAdapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to extract data..", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","f667647d-e97d-4c90-854e-f54600b5c033");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void getPopularBuyingData() {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i=0; i<dataArray.length(); i++){
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String name = dataObj.getString("name");
                        String symbol = dataObj.getString("symbol");
                        String coinId = dataObj.getString("id");
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        double price = USD.getDouble("price");
                        String dominance = USD.getString("market_cap_dominance");
                        currencyModelArrayList.add(new CurrencyModel(coinId,name,symbol,dominance,price));
                    }
                    popularBuyingAdapter.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Failed to extract data..", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed to get data.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","f667647d-e97d-4c90-854e-f54600b5c033");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public String convertValueInIndianCurrency(double amount){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("hi", "IN"));
        return formatter.format(amount);
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