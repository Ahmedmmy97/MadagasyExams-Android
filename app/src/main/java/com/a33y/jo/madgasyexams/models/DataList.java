package com.a33y.jo.madgasyexams.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 29/3/2018.
 */

public class DataList {
    private static List<Term> bac = new ArrayList<>();
    private static List<Object> bepc = new ArrayList<>();

    public static List<Object> getBepc() {
        return bepc;
    }

    public static void setBepc(List<Object> bepc) {
        DataList.bepc = bepc;
    }

    public static void setBac(List<Term> bac) {
        DataList.bac = bac;
    }

    public static List<Term> getBac() {
        return bac;
    }


}
