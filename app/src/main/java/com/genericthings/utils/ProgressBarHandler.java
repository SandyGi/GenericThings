

package com.genericthings.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.genericthings.R;

/**
 * The type Progress bar handler.
 *
 * @author Balasaheb Dhumal
 */
public class ProgressBarHandler {
    @NonNull
    private final ProgressDialog progressDialog;

    /**
     * Instantiates a new Progress bar handler.
     *
     * @param context the context
     */
    public ProgressBarHandler(Context context) {

        progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.progress));
        progressDialog.show();
        progressDialog.setContentView(R.layout.progressdialog);
        progressDialog.setCancelable(false);
        dismiss();
    }

    /**
     * Show.
     */
    public void show() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * Is shown boolean.
     *
     * @return the boolean
     */
    public boolean isShown() {
        return progressDialog.isShowing();
    }

    /**
     * Dismiss.
     */
    public void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
