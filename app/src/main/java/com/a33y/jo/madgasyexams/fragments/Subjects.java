package com.a33y.jo.madgasyexams.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.a33y.jo.madgasyexams.R;
import com.a33y.jo.madgasyexams.adapters.Adapter;
import com.a33y.jo.madgasyexams.views.CustomGridView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subjects extends Fragment {


    // TODO: Rename and change types of parameters
    private final List<Object>  data = new ArrayList<>();
    private LinearLayout loading;
    InterstitialAd mInterstitialAd;
    public Subjects() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getSerializable("data") instanceof List<?> arg) {
            if(!arg.isEmpty()) {
                data.addAll(arg);
            } else {
                Log.e("Subjects", "Invalid data passed to fragment");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_subjects, container, false);
        CustomGridView gridView = view.findViewById(R.id.recycler);
        loading = view.findViewById(R.id.progressbar);
        initAd(view);

        Adapter adapter = new Adapter(getContext(), data);
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);
        DisplayMetrics dm = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        if(dm.densityDpi<240){
            gridView.setNumColumns(3);
        }
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            requestNewInterstitial(position);
//            loading.setVisibility(View.GONE);
//            navigate(position);

        });
        return view;
    }
    private void navigate(int position){
        Bundle b = new Bundle();
        b.putSerializable("subject", (Serializable) data.get(position));
        Navigation.findNavController(requireActivity(),R.id.nav_fragment).navigate(R.id.subject_files_action,b);
    }
    private void requestNewInterstitial(int position) {
        MobileAds.initialize(requireContext());
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        loading.setVisibility(View.VISIBLE);
        InterstitialAd.load(requireContext(),"ca-app-pub-7038787403547669/5022810596", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                loading.setVisibility(View.GONE);
                                navigate(position);
                                Log.d("Ads", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                // Called when fullscreen content failed to show.
                                loading.setVisibility(View.GONE);
                                navigate(position);
                                Log.d("Ads", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("Ads", "The ad was shown.");
                            }
                        });
                        mInterstitialAd.show(requireActivity());
                        Log.i("Ads", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("Ads", loadAdError.getMessage());
                        mInterstitialAd = null;
                        loading.setVisibility(View.GONE);
                        navigate(position);
                    }
                });


    }

    void initAd(View v){
        MobileAds.initialize(requireContext());
        AdView mAdView = v.findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                // All emulators
                .build();
        mAdView.loadAd(request);
    }
}