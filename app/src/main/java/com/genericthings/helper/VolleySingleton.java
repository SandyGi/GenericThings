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

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * The type Volley singleton.
 *
 * @author Balasaheb Dhumal
 * @date 18/8/2017.
 */
public class VolleySingleton {


    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    private VolleySingleton() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized VolleySingleton getInstance() {
        if (mInstance == null) {
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }


    /**
     * Init volley request queue.
     *
     * @param context the context
     */
    public void initVolleyRequestQueue(@NonNull final Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Gets request queue.
     *
     * @return the request queue
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * Add to request queue.
     *
     * @param <T> the type parameter
     * @param req the req
     * @param tag the tag
     */
    public <T> void addToRequestQueue(@NonNull Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? "Johndeere" : tag);
        mRequestQueue.add(req);
    }

    /**
     * Add to request queue.
     *
     * @param <T> the type parameter
     * @param req the req
     */
    public <T> void addToRequestQueue(@NonNull Request<T> req) {
        req.setTag("Johndeere");
        mRequestQueue.add(req);
    }

    /**
     * Cancel pending requests.
     *
     * @param tag the tag
     */
    public void cancelPendingRequests(@NonNull Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Start pending requests.
     *
     * @param tag the tag
     */
    public void startPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.start();
        }
    }

}
