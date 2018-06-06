package com.genericthings.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.firebase.crash.FirebaseCrash;

public class CommonMethods {
    /**
     * Is network available boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Volley error check.
     *
     * @param context                the context
     * @param error                  the error
     * @param singleButtonCallback   the single button callback
     */
    public static void volleyErrorCheck(@NonNull Context context, VolleyError error, MaterialDialog.SingleButtonCallback singleButtonCallback) {

        try {
            FirebaseCrash.log(error.getMessage());
            FirebaseCrash.logcat(error.networkResponse.statusCode, error.getMessage(), VolleyError.class.getCanonicalName());
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            } else if (error instanceof AuthFailureError) {

            } else if (error instanceof ServerError) {

            } else if (error instanceof NetworkError) {

            } else if (error instanceof ParseError) {

            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
        }
    }
}
