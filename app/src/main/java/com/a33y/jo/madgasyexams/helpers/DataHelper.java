package com.a33y.jo.madgasyexams.helpers;

import static com.a33y.jo.madgasyexams.utils.Tools.fill_term_icons;

import android.content.Context;
import android.content.SharedPreferences;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.a33y.jo.madgasyexams.models.Exam;
import com.a33y.jo.madgasyexams.models.Subject;
import com.a33y.jo.madgasyexams.models.Term;
import com.a33y.jo.madgasyexams.models.DataList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Objects;

/**
 * Created by ahmed on 15/3/2018.
 */

public class DataHelper {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myref;
    private static StorageReference mStorageRef;
    private static SharedPreferences firstTime;
    public static int STORAGE_WRITE_PERMISSION = 111;
    public static int num = 0;
    public static String Token;

    public static void getData(final Context c, DataChangedListener dataChangedListener) {
//        firstTime = c.getSharedPreferences("FirstTime", Context.MODE_PRIVATE);
        myref = database.getReference("TestV2").child("Bac");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Term>> terms = new GenericTypeIndicator<>() {};
                Map<String, Term> TermsMap;
                TermsMap = dataSnapshot.getValue(terms);
                List<Term> termsList = new ArrayList<>(Objects.requireNonNull(TermsMap).values());
                for (Term term : termsList)
                    for (Subject subject : term.getSubjects()) {
                        for (Exam exam : subject.getExams()) {
                            exam.assignFiles();
                        }
                    }
                DataList.setBac(fill_term_icons(termsList));
                if (dataChangedListener != null)
                    dataChangedListener.onDataAdded();
                myref.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void downloadRemoteFile(final Subject subject, Exam exam, Boolean isAns, final Context context , DataChangedListener dataChangedListener) {
        final String name = isAns ? exam.getAnswerName() : exam.getName();
        final File file = new File(context.getFilesDir(), name);
        FirebaseStorage.getInstance().setMaxDownloadRetryTimeMillis(2000);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        if (!file.exists()) {
            StorageReference ref = mStorageRef.child(name);
            ref.getFile(file)
                    .addOnSuccessListener(taskSnapshot -> {

                        subject.getExams().forEach(Exam::assignFiles);

                        int filepos = subject.getExams().indexOf(exam);
                        if (dataChangedListener != null)
                            dataChangedListener.onFileDownloaded(filepos,isAns);
                    }).addOnFailureListener(exception -> {
                        // Handle failed download
                        // ...
                        if (dataChangedListener != null)
                            dataChangedListener.onFileDownloadedfailed(isAns);
                    });

        } else {
            if (dataChangedListener != null)
                dataChangedListener.onFileDownloadedfailed(isAns);
        }

    }

   public interface DataChangedListener {
        void onDataAdded();

        void onFileDownloaded(int pos, boolean isAns);

        void onFileDownloadedfailed(boolean isAns);
    }

}
