package com.a33y.jo.madgasyexams;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by ahmed on 26/1/2018.
 */

public class Connectivity {
    public static boolean isNetworkAvailable( Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static boolean checkNetwork(final Context c,View v) {
        if (!isNetworkAvailable(c)) {
            Snackbar bar = Snackbar.make(v, "Première exécution nécessite une connexion Internet!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Oui", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((Activity)c).finish();
                        }
                    });
            bar.getView().setBackgroundColor(c.getResources().getColor(R.color.colorPrimaryDark));
            bar.show();
            return false;
        }
        return true;
    }

}
