package com.a33y.jo.madgasyexams;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.transition.Transition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity implements DataHelper.DataChangedListener {
     FrameLayout frag;
    private SharedPreferences firstTime;
    Button bac;
    Button bepc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(null);
        }
        setContentView(R.layout.main);
        frag = findViewById(R.id.frag);
        try{
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch (Exception e){

        }
         getViews();
         frag.animate().alpha(1.0f).setDuration(1500).start();

        // test.create(this);
     /* if(checkFirstTime()){
          if(Connectivity.checkNetwork(this,frag)) {
              DataHelper.setDataChangedListener(this);
              DataHelper.getNewData(this);
          }

      }else {
          DataHelper.setDataChangedListener(this);
          DataHelper.getNewData(this);
      }*/

        // overridePendingTransition(R.animator.enter_anim_right,R.animator.exit_anim_left);

    }
    void getViews(){
        bac = findViewById(R.id.bac);
        bepc = findViewById(R.id.bepc);


        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,bac_frag.class);
                startActivity(intent);
            }
        });
        bepc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Subjects_frag.class);
                intent.putExtra("title",bepc.getText().toString());
                startActivity(intent);

            }
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);

    }
    private boolean checkFirstTime(){
        firstTime = this.getSharedPreferences("FirstTime",this.MODE_PRIVATE);
        return  firstTime.getBoolean("first",true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                
            }
           return  true;
    }

    @Override
    public void onDataAdded() {
       /* FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = new Main_frag();
        ft.add(R.id.frag,fragment,"main");
        ft.commit();
        SharedPreferences.Editor editor = firstTime.edit();
        editor.putBoolean("first", false);
        editor.commit();*/
    }

    @Override
    public void onFileDownloaded(int pos, LinearLayout loading) {

    }

    @Override
    public void onFileDownloadedfailed() {

    }
}
