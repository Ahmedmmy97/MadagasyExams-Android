package com.a33y.jo.madgasyexams;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class bac_frag extends AppCompatActivity {
    Button ss;
    Button sm;
    Button se;
    public bac_frag() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_a);
        getViews();
    }

    void getViews(){
        ss = findViewById(R.id.ss);
        sm = findViewById(R.id.sm);
        se = findViewById(R.id.se);
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(bac_frag.this,Subjects_frag.class);
                intent.putExtra("title",ss.getText().toString());
                startActivity(intent);

            }
        });
        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(bac_frag.this,Subjects_frag.class);
                intent.putExtra("title",sm.getText().toString());
                startActivity(intent);
            }
        });
        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(bac_frag.this,Subjects_frag.class);
                intent.putExtra("title",se.getText().toString());
                startActivity(intent);
            }
        });
    }
}
