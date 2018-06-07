
package com.genericthings.api;

import android.support.annotation.Nullable;

import com.google.gson.Gson;


/**
 * The type Request builder.
 *
 * @author Balasaheb Dhumal
 * @date 18/8/2017.
 */
class RequestBuilder {

    private RequestBuilder() {
    }

    /**
     * Gets json from object.
     *
     * @param object the object
     * @return the json from object
     */
    @Nullable
    public static String getJSONFromObject(@Nullable Object object) {
        if (object != null) {
            return new Gson().toJson(object);
        } else {
            return null;
        }
    }

}
