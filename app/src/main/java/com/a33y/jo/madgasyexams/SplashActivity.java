package com.a33y.jo.madgasyexams;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity implements DataHelper.DataChangedListener  {
    private static int  splashTime = 5000;
    private SharedPreferences firstTime;
    public static Context c;
    ImageView logo ;
    ProgressBar loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_frag);
        c = SplashActivity.this;
        logo = findViewById(R.id.logo);
        loadingbar = findViewById(R.id.loading);
        new Handler().postDelayed(new Runnable(){
            @Override
            public  void run() {
                try{
                    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                }catch (Exception e){

                }
                // test.create(this);
                if(checkFirstTime()){
                    if(Connectivity.checkNetwork(SplashActivity.this,findViewById(R.id.frag))) {
                        DataHelper.setDataChangedListener(SplashActivity.this);
                        DataHelper.getNewData(SplashActivity.this);
                    }

                }else {
                    DataHelper.setDataChangedListener(SplashActivity.this);
                    DataHelper.getNewData(SplashActivity.this);
                }
            }},2000);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FirebaseMessaging", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        DataHelper.Token =token;

                    }
                });
        FirebaseMessaging.getInstance().subscribeToTopic("1.0");


    }



    private boolean checkFirstTime(){
        firstTime = this.getSharedPreferences("FirstTime",this.MODE_PRIVATE);
        return  firstTime.getBoolean("first",true);
    }

    @Override
    public void onDataAdded() {
        //loadingbar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
       //intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, logo, ViewCompat.getTransitionName(logo));
            startActivity(intent,options.toBundle());
        }else {

            startActivity(intent);
        }
       // startActivity(intent);
        SharedPreferences.Editor editor = firstTime.edit();
        editor.putBoolean("first", false);
        editor.commit();

       // finish();
    }

    @Override
    public void onFileDownloaded(int pos , LinearLayout loading) {

    }

    @Override
    public void onFileDownloadedfailed() {

    }
}
