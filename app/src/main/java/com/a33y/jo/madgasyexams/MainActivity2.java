package com.a33y.jo.madgasyexams;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
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