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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link baac#newInstance} factory method to
 * create an instance of this fragment.
 */
public class baac extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CustomGridView gridView;
    private Adapter adapter;
    AdView adView;
    public baac() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment baac.
     */
    // TODO: Rename and change types and number of parameters
    public static baac newInstance(String param1, String param2) {
        baac fragment = new baac();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_baac, container, false);
        initAd(view);

        gridView = view.findViewById(R.id.recycler);

        adapter = new Adapter(getContext(), DataList.getBac());
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
                List<Subject> subjects = new ArrayList<>();
                for (Object t:DataList.getBac()
                     ) {

                    if(t.equals(DataList.getBac().get(position))){
                        subjects = ((Term)t).subjects;
                    }
                }
                Bundle b = new Bundle();
                b.putSerializable("data", (Serializable) subjects);
                Navigation.findNavController(getActivity(),R.id.nav_fragment).navigate(R.id.subjects_action,b);

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
}