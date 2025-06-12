package com.a33y.jo.madgasyexams.models;

import com.a33y.jo.madgasyexams.MadagasyExamsApplication;

import java.io.File;
import java.io.Serializable;

public class Exam implements Serializable {
    private String name;
    private String answerName;
    private File examFile;
    private File answerFile;

    public Exam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public File getExamFile() {
        return examFile;
    }

    public void setExamFile(File examFile) {
        this.examFile = examFile;
    }

    public File getAnswerFile() {
        return answerFile;
    }

    public void setAnswerFile(File answerFile) {
        this.answerFile = answerFile;
    }

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public void assignFiles(){
        if (examFile == null && name != null && !name.isEmpty()) {
            final File file = new File(MadagasyExamsApplication.getAppContext().getFilesDir(), name);
            if (file.exists()) {
                examFile = file;
            }
        }
        if (answerFile == null && answerName != null && !answerName.isEmpty()) {
            final File file = new File(MadagasyExamsApplication.getAppContext().getFilesDir(), answerName);
            if (file.exists()) {
                answerFile = file;
            }
        }
    }
}

