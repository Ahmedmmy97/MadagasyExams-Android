package com.a33y.jo.madgasyexams;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class splash_second_frag extends Fragment {

    public Context c;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.c = context;
    }

    public splash_second_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_frag,container,false);
        c= SplashActivity.c;
        new Handler().postDelayed(new Runnable(){
            @Override
            public  void run()
            {
                Intent intent = new Intent(c, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
                ((SplashActivity)c).finish();
            }
        },2000);
        return v;
    }

}
