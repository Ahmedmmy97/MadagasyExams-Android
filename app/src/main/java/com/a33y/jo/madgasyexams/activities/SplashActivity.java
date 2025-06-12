package com.a33y.jo.madgasyexams.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.a33y.jo.madgasyexams.helpers.DataHelper;
import com.a33y.jo.madgasyexams.R;
import com.a33y.jo.madgasyexams.utils.Connectivity;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements DataHelper.DataChangedListener {
    private static final int splashTime = 5000;
    private SharedPreferences firstTime;
    ImageView logo;
    ProgressBar loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.splash_frag);
        logo = findViewById(R.id.logo);
        loadingbar = findViewById(R.id.loading);
        new Handler().postDelayed(() -> {
            try {
                FirebaseDatabase.getInstance().setPersistenceEnabled(false);
            } catch (Exception ignored) {

            }
            if (Connectivity.checkNetwork(SplashActivity.this, findViewById(R.id.frag))) {
                DataHelper.getData(SplashActivity.this, SplashActivity.this);
            }
        }, 2000);

        FirebaseMessaging.getInstance().subscribeToTopic("2.1");
    }

    @Override
    public void onDataAdded() {
        loadingbar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        finish();
    }

    @Override
    public void onFileDownloaded(int pos, boolean isAns) {

    }

    @Override
    public void onFileDownloadedfailed(boolean isAns) {

    }
}
