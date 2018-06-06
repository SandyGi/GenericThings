/*
 *
 *
 *  * Copyright (c) 2017-2018 by Mobisoft Infotech Pvt Ltd, Inc.
 *
 *  * All Rights Reserved
 *
 *  * Company Confidential
 *
 *
 */

package com.genericthings.helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.genericthings.R;
import com.google.firebase.crash.FirebaseCrash;

/**
 * The type Ui helper.
 */
public class UiHelper {


    /**
     * Show network connection dialog.
     *
     * @param context              the context
     * @param title                the title
     * @param message              the message
     * @param positiveButton       the positive button
     * @param singleButtonCallback the single button callback
     */
    public static void showNetworkConnectionDialog(@NonNull Context context, @NonNull String title, @NonNull String message, @NonNull String positiveButton, @NonNull MaterialDialog.SingleButtonCallback singleButtonCallback) {
        FirebaseCrash.log(message);
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(positiveButton)
                .onAny(singleButtonCallback).backgroundColor(ContextCompat.getColor(context, R.color.colorAppBackGround))
                .show();
    }

    /**
     * Hide keyboard.
     *
     * @param context the context
     */
    public static void hideKeyboard(@NonNull Context context) {
        // Check if no view has focus:
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

