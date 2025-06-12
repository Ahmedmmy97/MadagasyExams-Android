package com.a33y.jo.madgasyexams.utils;

import static com.a33y.jo.madgasyexams.utils.Constants.iconMap;

import com.a33y.jo.madgasyexams.models.Subject;
import com.a33y.jo.madgasyexams.models.Term;

import java.util.List;

public class Tools {
    public static List<Term> fill_term_icons(List<Term> items) {
        for (Term t : items) {
            for (Subject s : t.getSubjects())
                if (iconMap.containsKey(s.getTitle())) {
                    Integer iconRes = iconMap.get(s.getTitle());
                    if (iconRes != null) {
                        s.setIconRes(iconRes);
                    }
                }
        }
        return items;
    }
    public static List<Subject> fill_subject_icons(List<Subject> items) {
        for (Subject s : items) {
            if (iconMap.containsKey(s.getTitle())) {
                Integer iconRes = iconMap.get(s.getTitle());
                if (iconRes != null) {
                    s.setIconRes(iconRes);
                }
            }
        }
        return items;
    }
}
