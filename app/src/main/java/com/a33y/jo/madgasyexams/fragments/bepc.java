package com.a33y.jo.madgasyexams.fragments;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.a33y.jo.madgasyexams.R;
import com.a33y.jo.madgasyexams.adapters.Adapter;
import com.a33y.jo.madgasyexams.models.DataList;
import com.a33y.jo.madgasyexams.views.CustomGridView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.Serializable;


public class bepc extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bepc, container, false);
        initAd(view);
        CustomGridView gridView = view.findViewById(R.id.recycler);

        Adapter adapter = new Adapter(getContext(), DataList.getBepc());
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);
        DisplayMetrics dm = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        if(dm.densityDpi<240){
            gridView.setNumColumns(3);
        }
        Log.i("tag",String.valueOf(getId()));
        gridView.setOnItemClickListener((parent, view1, position, id) -> {

            Bundle b = new Bundle();
            b.putSerializable("subject", (Serializable) DataList.getBepc().get(position));
            Navigation.findNavController(requireActivity(),R.id.nav_fragment).navigate(R.id.bepc_subject_files_action,b);

        });
        return view;
    }
    void initAd(View v){
        MobileAds.initialize(requireContext());
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