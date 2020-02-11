package com.a33y.jo.madgasyexams;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class sujetFrag extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Subject s;
    LinearLayout loading;
    FrameLayout frameLayout;
    public sujetFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sujetfrag,container,false);
        Bundle b = getArguments();
        s= (Subject) getArguments().getSerializable("subject");
        loading = v.findViewById(R.id.progressbar);
        loading.setVisibility(View.GONE);
        recyclerView = v.findViewById(R.id.recycler);
        frameLayout = v.findViewById(R.id.mainlayout);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        adapter = new Adapter(s,getContext(),1,false,loading);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if(adapter.getItemCount()!=0)
            frameLayout.setBackgroundColor(Color.WHITE);
        return v;
    }

}
