package com.finz.rest.token;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.finz.R;
import com.finz.RestDinamicConstant;
import com.finz.rest.RestConstant;
import com.finz.rest.RestConverter;
import com.finz.rest.RestDeserializer;
import com.finz.rest.RestListener;
import com.finz.rest.token.entity.Token;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class RestTokenImpl implements RestToken {
    private final Toast toast;
    private RequestQueue queue;
    private Gson gson;

    @SuppressLint("ShowToast")
    public RestTokenImpl(RequestQueue queue, Gson gson, Context ctx) {
        this.queue = queue;
        this.gson = gson;
        toast = Toast.makeText(ctx, R.string.str_error_internet, Toast.LENGTH_LONG);
    }

    @Override
    public void refreshToken(final String refreshToken, final RestListener<Token> listener) {
        StringRequest req = new StringRequest(
                Request.Method.POST,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_TOKEN,
                response -> listener.onSuccess(RestDeserializer.UserDeserializer.accessToken(response, gson)),
                error -> {
                    if (error.networkResponse!=null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return RestConverter.Token.requestRefreshToken(refreshToken);
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(RestConstant.CONTENT_TYPE, RestConstant.APPLICATION_FORM);
                String creds = String.format(RestConstant.FORMAT, RestDinamicConstant.CLIENT_ID,
                        RestDinamicConstant.CLIENT_SECRET);
                String auth = RestConstant.BASIC + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put(RestConstant.AUTHORIZATION, auth);
                return params;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }


    @Override
    public void requestAccessToken(String email, String password, RestListener<Token> listener) {
        StringRequest req = new StringRequest(
                Request.Method.POST,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_TOKEN,
                response -> listener.onSuccess(RestDeserializer.UserDeserializer.accessToken(response, gson)),
                error -> {
                    if (error.networkResponse!=null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return RestConverter.Token.requestAccessToken(email, password);
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(RestConstant.CONTENT_TYPE, RestConstant.APPLICATION_FORM);
                String creds = String.format(RestConstant.FORMAT, RestDinamicConstant.CLIENT_ID,
                        RestDinamicConstant.CLIENT_SECRET);
                String auth = RestConstant.BASIC + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put(RestConstant.AUTHORIZATION, auth);
                return params;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }
}
