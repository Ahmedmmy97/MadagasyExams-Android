package com.a33y.jo.madgasyexams;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main_frag extends Fragment {
    Button bac;
    Button bepc;
    Button sujet;
    Button comment;
    public Main_frag() {
        // Required empty public constructor
    }

    void getViews(View v){
        bac = v.findViewById(R.id.bac);
        bepc = v.findViewById(R.id.bepc);
        sujet = v.findViewById(R.id.sujet_proposes);
        comment= v.findViewById(R.id.comment_orienter);
        bac.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {

            }
        });
        bepc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new Subjects_frag();
                Bundle b = new Bundle();
                b.putString("title",bepc.getText().toString());
                fragment.setArguments(b);
                ft.setCustomAnimations(R.animator.enter_anim_right,R.animator.exit_anim_left,R.animator.enter_anim_left,R.animator.exit_anim_right);
                ft.replace(R.id.frag,fragment)   ;
                ft.addToBackStack(null);
                ft.commit();*/
                Intent intent = new Intent(getActivity(),Subjects_frag.class);
                intent.putExtra("title",bepc.getText().toString());
                startActivity(intent);

            }
        });
        sujet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new Subjects_frag();
                Bundle b = new Bundle();
                b.putString("title",sujet.getText().toString());
                fragment.setArguments(b);
                ft.setCustomAnimations(R.animator.enter_anim_right,R.animator.exit_anim_left,R.animator.enter_anim_left,R.animator.exit_anim_right);
                ft.replace(R.id.frag,fragment)   ;
                ft.addToBackStack(null);
                ft.commit();*/
                Intent intent = new Intent(getActivity(),Subjects_frag.class);
                intent.putExtra("title",sujet.getText().toString());
                startActivity(intent);


            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new Subjects_frag();
                Bundle b = new Bundle();
                b.putString("title",comment.getText().toString());
                fragment.setArguments(b);
                ft.setCustomAnimations(R.animator.enter_anim_right,R.animator.exit_anim_left,R.animator.enter_anim_left,R.animator.exit_anim_right);
                ft.replace(R.id.frag,fragment)   ;
                ft.addToBackStack(null);
                ft.commit();*/
                Intent intent = new Intent(getActivity(),Subjects_frag.class);
                intent.putExtra("title",comment.getText().toString());
                startActivity(intent);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_main,container,false);
        getViews(v);
        return v;
    }


}
