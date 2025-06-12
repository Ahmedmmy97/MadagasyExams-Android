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
import com.a33y.jo.madgasyexams.models.Subject;
import com.a33y.jo.madgasyexams.models.Term;
import com.a33y.jo.madgasyexams.views.CustomGridView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class baac extends Fragment {
    AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_baac, container, false);
        initAd(view);

        CustomGridView gridView = view.findViewById(R.id.recycler);
        List<Object> list = new ArrayList<>(DataList.getBac());
        Adapter adapter = new Adapter(getContext(), list);
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);
        DisplayMetrics dm = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        if (dm.densityDpi < 240) {
            gridView.setNumColumns(3);
        }
        Log.i("tag", String.valueOf(getId()));
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            List<Subject> subjects = new ArrayList<>();
            for (Term t : DataList.getBac()
            ) {
                if (t.equals(DataList.getBac().get(position))) {
                    subjects = t.getSubjects();
                }
            }
            Bundle b = new Bundle();
            b.putSerializable("data", (Serializable) subjects);
            Navigation.findNavController(requireActivity(), R.id.nav_fragment).navigate(R.id.subjects_action, b);

        });
        return view;
    }

    void initAd(View v) {
        MobileAds.initialize(requireContext());
        AdView mAdView = v.findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .build();
        mAdView.loadAd(request);
    }
}