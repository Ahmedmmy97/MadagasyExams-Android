package com.a33y.jo.madgasyexams;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class about extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView message;
    String txt = "MadaGasy Exams est une application crée pour aider les étudiants Malagasy qui prepare leurs Exames Scolaire(BEPC - BACC). Dans cette applicaiton on peut trouver des sujet types d’examen depuis l’année 98 jusqu’au 2018. On peut trouver aussi quelques leçons, exersices et des corrigés d’examen pour certains matières . \n" +
            "\n" +
            "“On vous assure qu’on ne cesse pas d’améliorer et de mettre à jour nos application pour vous aidez a reussir a vos examen”\n" +
            "\n" +
            "*Projet initié par: ANDRIANTOKY Nirinaldina Jonia\n" +
            "*Application conçue et réalisée par Ahmed Yousef\n" +
            "\n" +
            "SOURCE: Annales BACC et BEPC MADAGASCAR, les sujets d’examens ont été enlevés dans http://mediatheque.accesmad.org/educmad/\n" +
            "\n" +
            "Contact: madagasyexams@gmail.com";
    public about() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment about.
     */
    // TODO: Rename and change types and number of parameters
    public static about newInstance(String param1, String param2) {
        about fragment = new about();
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
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        message = view.findViewById(R.id.message);
        message.setText(txt);
        return view;
    }
}