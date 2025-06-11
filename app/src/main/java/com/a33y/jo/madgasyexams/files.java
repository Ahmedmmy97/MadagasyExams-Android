package com.a33y.jo.madgasyexams;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a33y.jo.madgasyexams.Adapters.RecyclerAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;


public class files extends Fragment implements ListItemClickListener, DataHelper.DataChangedListener {


    // TODO: Rename and change types of parameters
    private Subject subject;
    private CustomRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView title;
    private int file_pos;
    private LinearLayout loading;
    InterstitialAd mInterstitialAd;

    public files() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subject = (Subject) getArguments().getSerializable("subject");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        //requestNewInterstitial(0);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        initAd(view);
        recyclerView = view.findViewById(R.id.recycler);
        loading = view.findViewById(R.id.progressbar);
        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), subject.getFileNames(), this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setExpanded(true);
        title = view.findViewById(R.id.cat);
        title.setText(subject.getTitle());

        return view;
    }


    private void requestNewInterstitial(int position) {
        MobileAds.initialize(getContext());
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        if (Connectivity.isNetworkAvailable(getContext()))
            InterstitialAd.load(getContext(), "ca-app-pub-7038787403547669/5022810596", adRequest,
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

                                    navigate(position);
                                    loading.setVisibility(View.GONE);
                                    Log.d("Ads", "The ad was dismissed.");
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when fullscreen content failed to show.

                                    navigate(position);
                                    loading.setVisibility(View.GONE);
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
                            mInterstitialAd.show(getActivity());
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
        else {
            loading.setVisibility(View.GONE);
            navigate(position);
        }

    }

    private void navigate(int position) {
        loading.setVisibility(View.GONE);
        Intent intent = new Intent(getContext(), PdfViewer.class);
        intent.putExtra("subject", subject);
        intent.putExtra("pos", position);
        startActivity(intent);
    }

    boolean CheckFile(Subject s, int pos, boolean isAns) {
        for (File f : s.getFiles()) {
            if (!isAns)
                if (f.getName().equals(s.getFileNames().get(pos).getFileName())) {
                    file_pos = s.getFiles().indexOf(f);
                    return true;
                } else if (f.getName().equals(s.getFileNames().get(pos).getFileNameAns())) {
                    file_pos = s.getFiles().indexOf(f);
                    return true;
                }
        }
        return false;
    }

    @Override
    public void onClick(int pos) {

        String[] choices = {"Télécharger le fichier d'examen", "Télécharger les fichiers de réponses"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Options de téléchargement");

        builder.setItems(choices, (dialog, which) -> {
            // the user clicked on colors[which]

            switch (which) {
                case 0:
                    if (!CheckFile(subject, pos, false)) {
                        if (Connectivity.isNetworkAvailable(getContext())) {
                            loading.setVisibility(View.VISIBLE);
                            DataHelper.DownloadRemFile(subject, subject.getFileNames().get(pos).getFileName(), false, getContext(), loading, files.this);
                        } else {
                            Snackbar bar = Snackbar.make(getView(), "Il n'y a pas de connexion internet!", Snackbar.LENGTH_LONG);
                            bar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                            bar.show();
                        }
                    } else {
                        loading.setVisibility(View.VISIBLE);
                        requestNewInterstitial(file_pos);

                        //navigate(file_pos);
                    }
                    break;
                case 1:
                    if (!CheckFile(subject, pos, true)) {
                        if (Connectivity.isNetworkAvailable(getContext())) {
                            loading.setVisibility(View.VISIBLE);
                            DataHelper.DownloadRemFile(subject, subject.getFileNames().get(pos).getFileNameAns(), true, getContext(), loading, files.this);
                        } else {
                            Snackbar bar = Snackbar.make(getView(), "Il n'y a pas de connexion internet!", Snackbar.LENGTH_LONG);
                            bar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                            bar.show();
                        }
                    } else {
                        loading.setVisibility(View.VISIBLE);
                        requestNewInterstitial(file_pos);

                        //navigate(file_pos);
                    }
                    break;
                default:
                    break;
            }
        });
        builder.show();

    }

    void initAd(View v) {
        MobileAds.initialize(getContext());
        AdView mAdView = v.findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                // All emulators
                .build();
        mAdView.loadAd(request);
    }

    @Override
    public void onDataAdded() {

    }

    @Override
    public void onFileDownloaded(int pos, LinearLayout loading) {

        requestNewInterstitial(pos);
        /*loading.setVisibility(View.GONE);
        navigate(pos);*/
    }

    @Override
    public void onFileDownloadedfailed(boolean isAns) {
        loading.setVisibility(View.GONE);
        if (isAns) {
            Snackbar bar = Snackbar.make(getView(), "il n'y a pas de corriger disponibles.", Snackbar.LENGTH_LONG);
            bar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            bar.show();
        }

    }
}