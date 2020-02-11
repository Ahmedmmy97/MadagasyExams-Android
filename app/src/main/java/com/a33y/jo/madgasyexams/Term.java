package com.a33y.jo.madgasyexams;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ahmed on 7/4/2018.
 */

public class Term implements Serializable {
    String title;
    List<Subject> subjects;
    String key;
    public Term(String title, List<Subject> subjects) {
        this.title = title;
        this.subjects = subjects;
    }

    public Term() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
