package com.a33y.jo.madgasyexams;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

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
import java.util.Map;

/**
 * Created by ahmed on 15/3/2018.
 */

public class DataHelper {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myref ;
    private static StorageReference mStorageRef;
    private static SharedPreferences firstTime;
    private static DataChangedListener dataChangedListener;
    public static int STORAGE_WRITE_PERMISSION =111;
    public static int num =0;
    public static String Token;
    public static void setDataChangedListener(DataChangedListener dataChangedListener) {
        DataHelper.dataChangedListener = dataChangedListener;
    }

    public static void getData(final Context c){
        firstTime = c.getSharedPreferences("FirstTime",c.MODE_PRIVATE);
        myref = database.getReference("Bac");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Term>> terms = new GenericTypeIndicator<HashMap<String, Term>>(){};
                Map<String, Term> TermsMap = new HashMap<>();
                TermsMap = dataSnapshot.getValue(terms);
                List<Term> termsList = new ArrayList<>(TermsMap.values());
                num = termsList.size();

                    for (Term s : termsList) {




                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public static void getNewData(final Context c){

        myref = database.getReference("Bac");

        myref.keepSynced(true);
        DataList.getTermss().clear();

        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Term term = dataSnapshot.getValue(Term.class);
                term.setKey(dataSnapshot.getKey());

                 DataList.getTermss().add(term);
                 for(Subject subject :term.getSubjects()){
                     int i=0;
                     if(subject.getFileNames()==null)
                         break;
                     for(String string : subject.getFileNames()) {
                         DownloadFile(subject,i,false,c);
                         i++;
                     }
                     i=0;
                     if(subject.getFileNames_ans()==null)
                         break;
                     for(String string : subject.getFileNames_ans()) {
                         DownloadFile(subject,i,true,c);
                         i++;
                     }
                 }
                if(dataChangedListener!=null)
                    dataChangedListener.onDataAdded();


                //adapter.notifyItemInserted(0);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private static void DownloadFile(final Subject subject,int pos,boolean isAns, Context context){

      /* FirebaseStorage.getInstance().setMaxDownloadRetryTimeMillis(2000);

        mStorageRef = FirebaseStorage.getInstance().getReference();*/

        final File file = new File(context.getFilesDir(), isAns?subject.getFileNames_ans().get(pos): subject.getFileNames().get(pos));
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
    public static void DownloadRemFile(final Subject subject, final int pos, final boolean isAns, final Context context, final LinearLayout loading){

        final File file = new File(context.getFilesDir(), isAns?subject.getFileNames_ans().get(pos):subject.getFileNames().get(pos));
        FirebaseStorage.getInstance().setMaxDownloadRetryTimeMillis(2000);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        if(!file.exists()) {
            String s = isAns?subject.getFileNames_ans().get(pos):subject.getFileNames().get(pos);
            StorageReference Songsref = mStorageRef.child(s);
            Songsref.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            for(Term t : DataList.getTermss())
                                for(Subject sub : t.getSubjects())
                                    for(String string : isAns?subject.getFileNames_ans():subject.getFileNames())
                                        if(string.equals(isAns?subject.getFileNames_ans().get(pos):subject.getFileNames().get(pos)))
                                            sub.getFiles().add(file);
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
                        dataChangedListener.onFileDownloadedfailed();
                }
            });

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
        void onFileDownloadedfailed();
    }

}
