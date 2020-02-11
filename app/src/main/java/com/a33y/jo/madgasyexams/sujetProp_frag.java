package com.a33y.jo.madgasyexams;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class sujetProp_frag extends Fragment {
    Button term;
    Button eme;
    public sujetProp_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b,container,false);
        getViews(v);
        return v;
    }
    void getViews(View v){
        term = v.findViewById(R.id.term);
        eme = v.findViewById(R.id.eme);
        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        eme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }
}
