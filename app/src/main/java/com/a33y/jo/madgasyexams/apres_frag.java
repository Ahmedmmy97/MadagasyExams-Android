package com.a33y.jo.madgasyexams;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class apres_frag extends Fragment {

    public apres_frag() {
        // Required empty public constructor
    }

TextView link1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_apres,container,false);

        return v;
    }

}
