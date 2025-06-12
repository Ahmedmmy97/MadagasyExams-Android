package com.a33y.jo.madgasyexams.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.navigation.NavController;

import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.a33y.jo.madgasyexams.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavController controller;
    static public int Subjects_ID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bottomNavigationView = findViewById(R.id.bottom_navigatin_view);

        controller = Navigation.findNavController(this,R.id.nav_fragment);
        controller.enableOnBackPressed(true);
        NavigationUI.setupWithNavController(
                bottomNavigationView,
                controller
        );
    }

    @Override
    public void onBackPressed() {
        int count = Navigation.findNavController(this,R.id.nav_fragment).getCurrentBackStack().getValue().size();

        if(count>2)
            super.onBackPressed();
        else
            moveTaskToBack(true);
    }
}