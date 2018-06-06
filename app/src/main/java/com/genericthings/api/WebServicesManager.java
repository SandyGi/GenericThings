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

package com.genericthings.api;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.genericthings.helper.UiHelper;
import com.genericthings.helper.VolleySingleton;
import com.genericthings.model.BaseOutputModel;
import com.genericthings.utils.CommonMethods;
import com.genericthings.utils.ConstantData;
import com.genericthings.utils.ProgressBarHandler;

import org.greenrobot.eventbus.EventBus;

/**
 * The type Web services manager.
 *
 * @author Balasaheb Dhumal
 * @date 18/8/2017.
 */
public class WebServicesManager implements Response.ErrorListener, MaterialDialog.SingleButtonCallback {


    private final Context context;
    private ProgressBarHandler mProgressBarHandler;

    /**
     * Instantiates a new Web services manager.
     *
     * @param context the context
     */
    public WebServicesManager(Context context) {
        this.context = context;
    }

    /**
     * Send sign up request.
     *
     * @param <T>   the type parameter
     * @param input the input
     * @param url   the url
     */
    public <T> void sendSignUpRequest(T input, String url) {
        if (CommonMethods.isNetworkAvailable(context)) {
            Request request = new ApiHandler<>(url, RequestBuilder.getJSONFromObject(input), BaseOutputModel.class,
                    this::postEventBusData, this);
            execute(request, url, context);
        } else {
            UiHelper.showNetworkConnectionDialog(context, "Network Error", "Please check your internet connection", "Ok", this);
        }
    }


    private <T> void postEventBusData(T response) {
        hideProgressBar(mProgressBarHandler);
        if (response instanceof VolleyError) {
            CommonMethods.volleyErrorCheck(context, (VolleyError) response, this);
        } else {
            EventBus.getDefault().post(response);
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        hideProgressBar(mProgressBarHandler);
        postEventBusData(volleyError);
    }

    private void execute(@NonNull Request request, String tag, @NonNull Context context) {
        showProgressBar(context);
        VolleySingleton volleySingleton = VolleySingleton.getInstance();
        volleySingleton.initVolleyRequestQueue(context);
        volleySingleton.addToRequestQueue(request, tag);
    }

    private void showProgressBar(Context context) {
        hideProgressBar(mProgressBarHandler);
        if (context instanceof Activity) {
            mProgressBarHandler = new ProgressBarHandler(context);
            mProgressBarHandler.show();
        }
    }

    private void hideProgressBar(@Nullable ProgressBarHandler progressBarHandler) {
        if (progressBarHandler != null && progressBarHandler.isShown()) {
            mProgressBarHandler.dismiss();
        }
    }

    @Override
    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
        if (ConstantData.Constant.TAG_VOLLEY_ERROR.equals(materialDialog.getTag())) {
            materialDialog.dismiss();
        } else {
            Activity activity = scanForActivity(materialDialog.getContext());
            if (activity != null) {
                activity.finish();
            }
        }
    }

    private Activity scanForActivity(Context cont) {
        if (cont == null) {
            return null;
        } else if (cont instanceof Activity) {
            return (Activity) cont;
        } else if (cont instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) cont).getBaseContext());
        }

        return null;
    }
}
