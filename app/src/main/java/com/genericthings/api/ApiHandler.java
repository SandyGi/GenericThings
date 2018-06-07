
package com.genericthings.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.GsonBuilder;
import com.mobisoft.anubhuti.helper.SharedPrefHelper;
import com.mobisoft.anubhuti.util.ConstantData;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T> the type parameter
 * @author Balasaheb Dhumal
 * @date 18/8/2017.
 */
class ApiHandler<T> extends Request<T> {

    private static final String PROTOCOL_CHARSET = "utf-8";
    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    private final Class<T> mResponseClass;
    private final Response.Listener<T> mListener;
    private final String mRequestBody;

    /**
     * Content type for request.
     *
     * @param url           the url
     * @param requestBody   the request body
     * @param responseClass the response class
     * @param listener      the listener
     * @param errorListener the error listener
     */
    public ApiHandler(String url, String requestBody, Class<T> responseClass, Response.Listener<T> listener,
                      Response.ErrorListener errorListener) {

        this(Method.POST, url, requestBody, responseClass, listener,
                errorListener);
         Log.e("URL",""+url);
         Log.e("requestBody",""+requestBody);
       // Log.e("Url",url);
     //   Log.e("InputJson",requestBody);

    }

    private ApiHandler(int method, String url, String requestBody, Class<T> responseClass, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        setRetryPolicy(getDefaultRetryPolicy());
        setShouldCache(false);
        mResponseClass = responseClass;
        mRequestBody = requestBody;
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected Response<T> parseNetworkResponse(@NonNull NetworkResponse networkResponse) {
        try {

            String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            Log.e("response",jsonString);
            T response = new GsonBuilder().create().fromJson(jsonString, mResponseClass);
            return Response.success(response,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            FirebaseCrash.report(e);
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @NonNull
    private DefaultRetryPolicy getDefaultRetryPolicy() {
        return new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @NonNull
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>(2);
        params.put(ConstantData.Constant.SECURITY_TOKEN, new SharedPrefHelper().getString(ConstantData.Preference.PREF_PROFILE_ACCESS_TOKEN));
        params.put(ConstantData.Constant.LANGUAGE_CULTURE, new SharedPrefHelper().getString(ConstantData.Preference.PREF_PROFILE_LANGUAGE_CULTURE));
        return params;
    }

    @Nullable
    @Override
    public byte[] getBody() {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return new byte[1];
        }
    }

}
