package com.a33y.jo.madgasyexams;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 29/3/2018.
 */

public class Subject implements Serializable {
    String title;
    List<File> files = new ArrayList<>();
    List<String> fileNames = new ArrayList<>();

    public List<String> getFileNames_ans() {
        return fileNames_ans;
    }

    public void setFileNames_ans(List<String> fileNames_ans) {
        this.fileNames_ans = fileNames_ans;
    }

    List<String> fileNames_ans = new ArrayList<>();

    public Subject(String title, List<String> fileNames, List<String> fileNames_ans) {
        this.title = title;
        this.fileNames = fileNames;
        this.fileNames_ans = fileNames_ans;
    }
    public Subject(String title, List<String> fileNames) {
        this.title = title;
        this.fileNames = fileNames;

    }
    public Subject() {
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
