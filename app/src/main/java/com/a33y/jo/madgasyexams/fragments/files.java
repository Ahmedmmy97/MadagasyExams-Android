package com.a33y.jo.madgasyexams.fragments;

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

import com.a33y.jo.madgasyexams.R;
import com.a33y.jo.madgasyexams.activities.PdfViewer;
import com.a33y.jo.madgasyexams.adapters.RecyclerAdapter;
import com.a33y.jo.madgasyexams.helpers.DataHelper;
import com.a33y.jo.madgasyexams.helpers.ListItemClickListener;
import com.a33y.jo.madgasyexams.models.Exam;
import com.a33y.jo.madgasyexams.models.Subject;
import com.a33y.jo.madgasyexams.utils.Connectivity;
import com.a33y.jo.madgasyexams.views.CustomRecyclerView;
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
import java.io.Serializable;


public class files extends Fragment implements ListItemClickListener, DataHelper.DataChangedListener {


    private Subject subject;
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
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        initAd(view);
        CustomRecyclerView recyclerView = view.findViewById(R.id.recycler);
        loading = view.findViewById(R.id.progressbar);
        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), subject.getExams(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setExpanded(true);
        TextView title = view.findViewById(R.id.cat);
        title.setText(subject.getTitle());

        return view;
    }


    private void requestNewInterstitial(int position, boolean isAns) {
        MobileAds.initialize(requireContext());
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        if (Connectivity.isNetworkAvailable(requireContext()))
            InterstitialAd.load(requireContext(), "ca-app-pub-7038787403547669/5022810596", adRequest,
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

                                    navigate(position, isAns);
                                    loading.setVisibility(View.GONE);
                                    Log.d("Ads", "The ad was dismissed.");
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    // Called when fullscreen content failed to show.

                                    navigate(position, isAns);
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
                            mInterstitialAd.show(requireActivity());
                            Log.i("Ads", "onAdLoaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.i("Ads", loadAdError.getMessage());
                            mInterstitialAd = null;
                            loading.setVisibility(View.GONE);
                            navigate(position, isAns);
                        }
                    });
        else {
            loading.setVisibility(View.GONE);
            navigate(position, isAns);
        }

    }

    private void navigate(int position, boolean isAns) {
        loading.setVisibility(View.GONE);
        Intent intent = new Intent(getContext(), PdfViewer.class);
        intent.putExtra("subject", subject);
        intent.putExtra("pos", position);
        intent.putExtra("isAnswer", isAns);
        startActivity(intent);
    }

    @Override
    public void onClick(int pos) {
        file_pos = pos;
        final Exam exam = subject.getExams().get(pos);
        String[] choices = {"Télécharger le fichier d'examen", "Télécharger les fichiers de réponses"};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Options de téléchargement");

        builder.setItems(choices, (dialog, which) -> {
            // the user clicked on colors[which]

            switch (which) {
                case 0:
                    if (!(exam.getExamFile() != null && exam.getExamFile().exists())) {
                        if (Connectivity.isNetworkAvailable(requireContext())) {
                            loading.setVisibility(View.VISIBLE);
                            DataHelper.downloadRemoteFile(subject, subject.getExams().get(pos),false, requireContext(), files.this);
                        } else {
                            Snackbar bar = Snackbar.make(requireView(), "Il n'y a pas de connexion internet!", Snackbar.LENGTH_LONG);
                            bar.getView().setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
                            bar.show();
                        }
                    } else {
                        loading.setVisibility(View.VISIBLE);
                        requestNewInterstitial(file_pos, false);
                    }
                    break;
                case 1:
                    if (exam.getAnswerName() == null || exam.getAnswerName().isEmpty()) {
                        Snackbar bar = Snackbar.make(requireView(), "Il n'y a pas de corriger disponibles.", Snackbar.LENGTH_LONG);
                        bar.getView().setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorRed));
                        bar.show();
                        return;
                    }
                    if (!(exam.getAnswerFile() != null && exam.getAnswerFile().exists())) {
                        if (Connectivity.isNetworkAvailable(requireContext())) {
                            loading.setVisibility(View.VISIBLE);
                            DataHelper.downloadRemoteFile(subject, subject.getExams().get(pos), true, requireContext(), files.this);
                        } else {
                            Snackbar bar = Snackbar.make(requireView(), "Il n'y a pas de connexion internet!", Snackbar.LENGTH_LONG);
                            bar.getView().setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
                            bar.show();
                        }
                    } else {
                        loading.setVisibility(View.VISIBLE);
                        requestNewInterstitial(file_pos, true);
                    }
                    break;
                default:
                    break;
            }
        });
        builder.show();

    }

    void initAd(View v) {
        MobileAds.initialize(requireContext());
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
    public void onFileDownloaded(int pos, boolean isAns) {
        requestNewInterstitial(pos, isAns);
    }

    @Override
    public void onFileDownloadedfailed(boolean isAns) {
        loading.setVisibility(View.GONE);
        if (isAns) {
            Snackbar bar = Snackbar.make(requireView(), "il n'y a pas de corriger disponibles.", Snackbar.LENGTH_LONG);
            bar.getView().setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorRed));
            bar.show();
        }

    }
}