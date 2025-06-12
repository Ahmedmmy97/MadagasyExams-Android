package com.a33y.jo.madgasyexams.activities;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.a33y.jo.madgasyexams.R;
import com.a33y.jo.madgasyexams.models.Subject;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.Objects;

public class PdfViewer extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfviewer);

        pdfView = findViewById(R.id.pdf);
        if(getFile()!=null) {
            pdfView.fromFile(getFile()).enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    // allows to draw something on the current page, usually visible in the middle of the screen
                    // allows to draw something on all pages, separately for every page. Called only for visible pages
                    // called after document is loaded and starts to be rendered
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(1)
                    .load();
        }else{
            finish();
        }

    }
    File getFile(){
        Intent intent = getIntent();
        boolean isAnswer = intent.getBooleanExtra("isAnswer", false);
        Subject s = (Subject) intent.getSerializableExtra("subject");
        return isAnswer ? Objects.requireNonNull(s).getExams().get(intent.getIntExtra("pos", 0)).getAnswerFile() : Objects.requireNonNull(s).getExams().get(intent.getIntExtra("pos", 0)).getExamFile();
    }

}
