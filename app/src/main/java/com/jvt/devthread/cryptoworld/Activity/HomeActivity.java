package com.jvt.devthread.cryptoworld.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jvt.devthread.cryptoworld.Activity.Dashboard.AmountSummaryToHomeActivity;
import com.jvt.devthread.cryptoworld.Activity.Dashboard.Common;
import com.jvt.devthread.cryptoworld.Activity.Dashboard.Dashboard;
import com.jvt.devthread.cryptoworld.Activity.Model.CoinBuyModel;
import com.jvt.devthread.cryptoworld.Activity.Model.PortfolioModel;
import com.jvt.devthread.cryptoworld.Activity.UI.Market;
import com.jvt.devthread.cryptoworld.Activity.UI.Portfolio;
import com.jvt.devthread.cryptoworld.Activity.UI.Profile;
import com.jvt.devthread.cryptoworld.Activity.UI.Refer;
import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.ActivityHomeBinding;
import com.razorpay.PaymentResultListener;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity implements PaymentResultListener, AmountSummaryToHomeActivity {
    final FragmentManager fm = getSupportFragmentManager();
    private ActivityHomeBinding binding;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static  Fragment activeFragment;
    String coinId, name, symbol,orderType, date, orderId,uid,unit,AmountPaid,orderID;
    double price,purchasedAmount;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    double currentValue, investedValue, gainLossValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        uid = firebaseAuth.getCurrentUser().getUid();
        Fragment featureFragment=new Dashboard();
        loadFragment(featureFragment,"Dashboard");
        binding.home.setOnClickListener(view1 -> {
            Fragment featureFragment1=new Dashboard();
            loadFragment(featureFragment1,"Dashboard");
        });
        binding.portfolio.setOnClickListener(view1 -> {
            Fragment portfolio=new Portfolio();
            loadFragment(portfolio,"Portfolio");
        });
        binding.refer.setOnClickListener(view1 -> {
            Fragment refer = new Refer();
            loadFragment(refer,"Refer");
        });
        binding.market.setOnClickListener(view1 -> {
            Fragment market = new Market();
            loadFragment(market,"Market");
        });
        binding.profile.setOnClickListener(view1 -> {
            Fragment profile = new Profile();
            loadFragment(profile,"Profile");
        });

    }
    @SuppressLint("StaticFieldLeak")
    private void loadFragment(Fragment fragment, String tag)
    {

        executorService.execute(() ->{
            if (fragment!=null)
            {
                fm.beginTransaction().replace(R.id.fragment_container, fragment,tag).addToBackStack(tag).commitAllowingStateLoss();
            }
            handler.post(() ->{
                activeFragment=fm.findFragmentById(R.id.fragment_container);
            });
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        coinId = Common.coinId;
        name = Common.coinName;
        symbol = Common.coinSymbol;
        orderType = Common.orderType;
        Date date1 = Calendar.getInstance().getTime();
        date = String.valueOf(date1);
        orderId = Common.orderId;
        unit = Common.unit;
        price = Common.coinPrice;
        purchasedAmount = Common.purchasedAmount;
        CoinBuyModel coinBuyModel = new CoinBuyModel(coinId, name, symbol,orderType, date, orderId,unit,price,purchasedAmount);
        databaseReference.child("Users").child(uid).child("Orders").child(orderId).setValue(coinBuyModel);
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
        currentValue = Common.current+Common.purchasedAmount;
        gainLossValue = Common.gain;
        investedValue = Common.invested+Common.purchasedAmount;
        PortfolioModel portfolioModel = new PortfolioModel(currentValue, investedValue, gainLossValue);
        databaseReference1.child("Users").child(uid).child("Portfolio").setValue(portfolioModel);
        Toast.makeText(this, "Order placed successfully..", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void amount(String netAmount, String orderId) {
        AmountPaid=netAmount;
        orderId=orderID;
    }
}