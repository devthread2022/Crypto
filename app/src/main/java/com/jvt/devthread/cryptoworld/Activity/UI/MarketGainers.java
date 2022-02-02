package com.jvt.devthread.cryptoworld.Activity.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.jvt.devthread.cryptoworld.Activity.Adapter.CurrencyAdapter;
import com.jvt.devthread.cryptoworld.Activity.Model.CurrencyModel;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.FragmentMarketGainersBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarketGainers extends Fragment {
    private FragmentMarketGainersBinding binding;
    private ArrayList<CurrencyModel> currencyModelArrayList = new ArrayList<>();
    private CurrencyAdapter currencyAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMarketGainersBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        currencyAdapter = new CurrencyAdapter(getContext(),currencyModelArrayList);
        binding.gainerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.gainerRecycler.setAdapter(currencyAdapter);
        getCurrencyData();
        return view;
    }
    private void getCurrencyData() {
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
                    currencyAdapter.notifyDataSetChanged();

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
}