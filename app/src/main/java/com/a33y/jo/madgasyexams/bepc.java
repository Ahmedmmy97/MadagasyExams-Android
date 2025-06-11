package com.a33y.jo.madgasyexams;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.a33y.jo.madgasyexams.Adapters.Adapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class bepc extends Fragment {
    private CustomGridView gridView;
    private Adapter adapter;

    public bepc() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bepc, container, false);
        initAd(view);
        gridView = view.findViewById(R.id.recycler);

        adapter = new Adapter(getContext(), DataList.getBepc());
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        if(dm.densityDpi<240){
            gridView.setNumColumns(3);
        }
        Log.i("tag",String.valueOf(getId()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle b = new Bundle();
                b.putSerializable("subject", (Serializable) DataList.getBepc().get(position));
                Navigation.findNavController(getActivity(),R.id.nav_fragment).navigate(R.id.bepc_subject_files_action,b);

            }
        });
        return view;
    }
    void initAd(View v){
        MobileAds.initialize(getContext());
        AdView mAdView = v.findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                // All emulators
                .build();
        mAdView.loadAd(request);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}