package com.a33y.jo.madgasyexams;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity implements DataHelper.DataChangedListener  {
    private static final int  splashTime = 5000;
    private SharedPreferences firstTime;
    ImageView logo ;
    ProgressBar loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.splash_frag);
        logo = findViewById(R.id.logo);
        loadingbar = findViewById(R.id.loading);
        new Handler().postDelayed(new Runnable(){
            @Override
            public  void run() {
                try{
                    FirebaseDatabase.getInstance().setPersistenceEnabled(false);
                }catch (Exception e){

                }
                // test.create(c);
                //checkFirstTime();
                    if(Connectivity.checkNetwork(SplashActivity.this,findViewById(R.id.frag))) {

                        DataHelper.getData(SplashActivity.this,SplashActivity.this);
                    }


            }},2000);

        FirebaseMessaging.getInstance().subscribeToTopic("2.1");


    }



    private boolean checkFirstTime(){
        firstTime = this.getSharedPreferences("FirstTime", MODE_PRIVATE);
        return  firstTime.getBoolean("first",true);
    }

    @Override
    public void onDataAdded() {
        loadingbar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity2.class);
        //intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);


        startActivity(intent);

       // startActivity(intent);
        /*SharedPreferences.Editor editor = firstTime.edit();
        editor.putBoolean("first", false);
        editor.commit();*/

       finish();
    }

    @Override
    public void onFileDownloaded(int pos , LinearLayout loading) {

    }

    @Override
    public void onFileDownloadedfailed(boolean isAns) {

    }
}
