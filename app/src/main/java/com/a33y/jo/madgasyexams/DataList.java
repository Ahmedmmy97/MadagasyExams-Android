package com.a33y.jo.madgasyexams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 29/3/2018.
 */

public class DataList {
    private static List<Object> bac = new ArrayList<>();
    private static List<Object> bepc = new ArrayList<>();

    public static List<Object> getBepc() {
        return bepc;
    }

    public static void setBepc(List<Object> bepc) {
        DataList.bepc = bepc;
    }

    public static void setBac(List<Object> bac) {
        DataList.bac = bac;
    }

    public static List<Object> getBac() {
        return bac;
    }


}
