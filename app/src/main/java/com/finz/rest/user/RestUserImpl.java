package com.finz.rest.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.finz.RestDinamicConstant;
import com.finz.rest.RestEmptyListener;
import com.google.gson.Gson;
import com.finz.R;
import com.finz.rest.RestConstant;
import com.finz.rest.RestConverter;
import com.finz.rest.RestDeserializer;
import com.finz.rest.RestListener;
import com.finz.rest.user.entity.User;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * @author SudTechnologies
 */
@Singleton
public class RestUserImpl implements RestUser {

    private final Toast toast;

    private RequestQueue queue;
    private Gson gson;

    @SuppressLint("ShowToast")
    public RestUserImpl(RequestQueue queue, Gson gson, Context ctx) {
        this.queue = queue;
        this.gson = gson;
        toast = Toast.makeText(ctx, R.string.str_error_internet, Toast.LENGTH_LONG);
    }

    @Override
    public void register(String email, String password, String name,
                         String lastName, String phone, String dni,
                         final RestListener<User> listener) {
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_USER_SIGNUP,
                RestConverter.User.register(email, password, name, lastName, phone, dni),
                response -> listener.onSuccess(RestDeserializer.UserDeserializer.user(response)),
                error -> {
                    if (error.networkResponse != null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        );
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    @Override
    public void userInfo(final String token, final RestListener<User> listener) {
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_USER_INFO,
                null,
                response -> listener.onSuccess(RestDeserializer.UserDeserializer.user(response)),
                error -> {
                    if (error.networkResponse != null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                String auth = RestConstant.BEARER + token;
                params.put(RestConstant.AUTHORIZATION, auth);
                return params;
            }

        };
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    @Override
    public void recoverPwd(String email, final RestEmptyListener listener) {
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_USER_RECOVER_PASS,
                RestConverter.User.resetPass(email),
                response -> listener.onSuccess(),
                error -> {
                    if (error.networkResponse != null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        );
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

    @Override
    public void changePwd(final String oldPass, final String newPass, final String token, final RestEmptyListener listener) {
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_USER_CHANGE_PASS,
                RestConverter.User.changePass(oldPass, newPass),
                response -> listener.onSuccess(),
                error -> {
                    if (error.networkResponse != null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                String auth = RestConstant.BEARER + token;
                params.put(RestConstant.AUTHORIZATION, auth);
                return params;
            }
        };
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }
}
