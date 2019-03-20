package com.finz.rest.history;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.finz.R;
import com.finz.RestDinamicConstant;
import com.finz.rest.RestConstant;
import com.finz.rest.RestConverter;
import com.finz.rest.RestDeserializer;
import com.finz.rest.RestEmptyListener;
import com.finz.rest.RestListener;
import com.finz.rest.history.entity.History;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class RestFirebaseTokenImpl implements RestFirebaseToken {

    private final Toast toast;
    private RequestQueue queue;

    @SuppressLint("ShowToast")
    public RestFirebaseTokenImpl(RequestQueue queue, Context ctx) {
        this.queue = queue;
        toast = Toast.makeText(ctx, R.string.str_error_internet, Toast.LENGTH_LONG);
    }

    @Override
    public void saveToken(String firebaseToken, String accessToken, RestListener<History> listener) {
        JsonObjectRequest req=new JsonObjectRequest(
                Request.Method.POST,
                RestDinamicConstant.URL_BASE /*+ RestConstant.ENDPOINT_FIREBASE_TOKEN*/,
                RestConverter.FirebaseToken.firebaseToken(firebaseToken),
                response -> listener.onSuccess(RestDeserializer.FirebaseTokenDeserializer.firebaseToken(response)),
                error -> {
                    if (error.networkResponse!=null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                String auth = RestConstant.BEARER + accessToken;
                params.put(RestConstant.AUTHORIZATION, auth);
                return params;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    @Override
    public void deleteToken(long idToken, String accessToken, RestEmptyListener listener) {
        StringRequest req = new StringRequest(
                Request.Method.DELETE,
                RestDinamicConstant.URL_BASE /*+ RestConstant.pathDeleteFirefabeToken(idToken)*/,
                response -> listener.onSuccess(),
                error -> {
                    if (error.networkResponse!=null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                String auth = RestConstant.BEARER + accessToken;
                params.put(RestConstant.AUTHORIZATION, auth);
                return params;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }
}
