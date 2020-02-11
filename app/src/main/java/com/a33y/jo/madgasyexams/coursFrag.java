package com.a33y.jo.madgasyexams;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class coursFrag extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Subject s;
    LinearLayout loading;
    FrameLayout frameLayout;
    public coursFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.coursfrag,container,false);

        return v;
    }

}
