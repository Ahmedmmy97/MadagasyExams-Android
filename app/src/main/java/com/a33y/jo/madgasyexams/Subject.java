package com.a33y.jo.madgasyexams;

import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 29/3/2018.
 */

public class Subject implements Serializable {
    private String title;
    private int iconRes = R.drawable.ic_cat;
    private List<File> files = new ArrayList<>();
    private List<CustomFiles> fileNames = new ArrayList<>();

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public List<String> getFileNames_ans() {
        return fileNames_ans;
    }

    public void setFileNames_ans(List<String> fileNames_ans) {
        this.fileNames_ans = fileNames_ans;
    }

    List<String> fileNames_ans = new ArrayList<>();


    public Subject() {
    }

    public List<CustomFiles> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<CustomFiles> fileNames) {
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
