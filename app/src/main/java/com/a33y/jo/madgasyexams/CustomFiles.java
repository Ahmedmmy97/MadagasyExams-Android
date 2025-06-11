package com.a33y.jo.madgasyexams;

import java.io.Serializable;

public class CustomFiles implements Serializable {
    private String fileName;
    private String fileNameAns;

    public CustomFiles() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameAns() {
        return fileNameAns;
    }

    public void setFileNameAns(String fileNameAns) {
        this.fileNameAns = fileNameAns;
    }
}
