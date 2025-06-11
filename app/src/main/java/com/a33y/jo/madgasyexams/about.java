package com.a33y.jo.madgasyexams;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class about extends Fragment {
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView message = view.findViewById(R.id.message);
        message.setText(txt);
        return view;
    }
}