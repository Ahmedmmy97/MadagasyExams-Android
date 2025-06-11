package com.a33y.jo.madgasyexams;

import android.content.Context;
import android.content.SharedPreferences;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by ahmed on 15/3/2018.
 */

public class DataHelper {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myref ;
    private static StorageReference mStorageRef;
    private static SharedPreferences firstTime;
    public static int STORAGE_WRITE_PERMISSION =111;
    public static int num =0;
    public static String Token;
    private static final HashMap<String,Integer> iconMap = new HashMap<String, Integer>(){
        {put("Mathematique",R.drawable.math);}
        {put("Francais",R.drawable.frs);}
        {put("Anglais",R.drawable.anglais);}
        {put("Allemand",R.drawable.anglais);}
        {put("Espagnol",R.drawable.anglais);}
        {put("Histoire - Géographie",R.drawable.histo_geo);}
        {put("Malagasy",R.drawable.mlg);}
        {put("Physique Chime",R.drawable.physic);}
        {put("Philosophie",R.drawable.philo);}
        {put("SVT",R.drawable.science);}
        {put("EPS",R.drawable.eps);}

    };
    private static List<Object> assign_icon(List<Object> terms){
        try {
            for (Object t : terms) {
                for (Subject s : ((Term) t).subjects)
                    if (iconMap.containsKey(s.getTitle())) {
                        s.setIconRes(iconMap.get(s.getTitle()));
                    }
            }

        }catch (Exception e){
            for(Object s:terms){
                if(iconMap.containsKey(((Subject)s).getTitle())){
                    ((Subject)s).setIconRes(iconMap.get(((Subject)s).getTitle()));
                }
            }
        }
        return terms;
    }

    public static void getData(final Context c, DataChangedListener dataChangedListener){
        firstTime = c.getSharedPreferences("FirstTime", Context.MODE_PRIVATE);
        myref = database.getReference("Test").child("Bac");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Term>> terms = new GenericTypeIndicator<HashMap<String, Term>>(){};
                Map<String, Term> TermsMap = new HashMap<>();
                TermsMap = dataSnapshot.getValue(terms);
                List<Object> termsList = new ArrayList<>(TermsMap.values());
                for(Object term: termsList)
                for(Object subject :((Term)term).subjects){

                    for(CustomFiles file : ((Subject)subject).getFileNames()) {
                        DownloadFile(((Subject)subject),file.getFileName(),false,c);
                        DownloadFile(((Subject)subject),file.getFileNameAns(),false,c);
                    }


                }
                DataList.setBac(assign_icon(termsList));
                if(dataChangedListener!=null)
                    dataChangedListener.onDataAdded();
                myref.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*myref = database.getReference("Test").child("Bepc");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Subject>> bepc = new GenericTypeIndicator<List<Subject>>(){};
                List<Subject> slist= new ArrayList<>();
                slist = dataSnapshot.child("subjects").getValue(bepc);
                List<Object> termsList = new ArrayList<>(slist);

                for(Object subject :termsList){

                    for(CustomFiles file : ((Subject)subject).getFileNames()) {
                        DownloadFile(((Subject)subject),file.getFileName(),false,c);
                        DownloadFile(((Subject)subject),file.getFileNameAns(),false,c);
                    }

                }
                DataList.setBepc(assign_icon(termsList));
                if(dataChangedListener!=null)
                    dataChangedListener.onDataAdded();
                myref.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    private static void DownloadFile(final Subject subject,String name,boolean isAns, Context context){

      /* FirebaseStorage.getInstance().setMaxDownloadRetryTimeMillis(2000);

        mStorageRef = FirebaseStorage.getInstance().getReference();*/
        if(name.equals(""))
            return;
        final File file = new File(context.getFilesDir(), name);
        if(!file.exists()) {


/*
               StorageReference Songsref = mStorageRef.child(subject.getFileNames().get(pos)+".pdf");
                Songsref.getFile(file)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Successfully downloaded data to local file
                                // ...
                                if( dataChangedListener!=null)
                                dataChangedListener.onFileDownloaded();
                                subject.getFiles().add(file);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle failed download
                        // ...
                       if(dataChangedListener!=null)
                        dataChangedListener.onFileDownloadedfailed();
                    }
                });*/




        }else {
            subject.getFiles().add(file);
        }

    }
    public static void DownloadRemFile(final Subject subject,String name, final boolean isAns, final Context context, final LinearLayout loading,DataChangedListener dataChangedListener){

        final File file = new File(context.getFilesDir(), name);
        FirebaseStorage.getInstance().setMaxDownloadRetryTimeMillis(2000);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        if(!file.exists()) {
            String s = name;
            StorageReference Songsref = mStorageRef.child(s);
            Songsref.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...

                            subject.getFiles().add(file);


                           int filepos = subject.getFiles().indexOf(file);
                            if( dataChangedListener!=null)
                                dataChangedListener.onFileDownloaded(filepos,loading);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                    if(dataChangedListener!=null)
                        dataChangedListener.onFileDownloadedfailed(isAns);
                }
            });

        }else {
            if(dataChangedListener!=null)
                dataChangedListener.onFileDownloadedfailed(isAns);
        }

    }
   /* public static void DeleteFile(final Term song, Context context){
        final File file = new File(context.getFilesDir(), song.getFileName()+".mp3");
        if(file.exists()) {
            file.delete();
        }
        song.setMusicFile(null);
    }
   */ public interface DataChangedListener {
        void onDataAdded();
        void onFileDownloaded(int pos,LinearLayout loading);
        void onFileDownloadedfailed(boolean isAns);
    }

}
