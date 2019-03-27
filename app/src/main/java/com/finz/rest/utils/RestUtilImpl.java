package com.finz.rest.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.finz.R;
import com.finz.RestDinamicConstant;
import com.finz.rest.RestConstant;
import com.finz.rest.RestConverter;
import com.finz.rest.RestDeserializer;
import com.finz.rest.RestEmptyListener;
import com.finz.rest.RestListListener;
import com.finz.rest.RestListener;
import com.finz.rest.utils.entity.BankType;
import com.finz.rest.utils.entity.Evaluation;
import com.finz.rest.utils.entity.Param;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * @author SudTechnologies
 */
@Singleton
public class RestUtilImpl implements RestUtil {

    private final Toast toast;

    private RequestQueue queue;
    private Gson gson;

    @SuppressLint("ShowToast")
    public RestUtilImpl(RequestQueue queue, Gson gson, Context ctx) {
        this.queue = queue;
        this.gson = gson;
        toast = Toast.makeText(ctx, R.string.str_error_internet, Toast.LENGTH_LONG);
    }

    @Override
    public void params(final String token, final RestListener<Param> listener) {
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_PARAM,
                null,
                response -> listener.onSuccess(RestDeserializer.UtilDeserializer.param(response, gson)),
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
    public void banks(final String token, final RestListListener<BankType> listener) {
        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.GET,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_BANK,
                null,
                response -> listener.onSuccess(RestDeserializer.UtilDeserializer.banksType(response, gson)),
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
    public void disposition(String token, String tokenC, String nameOnCard, String email, String phone, String accountType, String nroDestiny, long bank, String photoSignature, String photoDni, double amount, RestEmptyListener listener) {
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_DISPOSITION,
                RestConverter.Util.disposition(tokenC,nameOnCard,email,phone,accountType,nroDestiny,bank,photoSignature,photoDni,amount),
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

    @Override
    public void loadType(final String token, RestListListener<BankType> listener) {
        JsonArrayRequest req = new JsonArrayRequest(
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_LOAN_TYPE,
                response -> listener.onSuccess(RestDeserializer.UtilDeserializer.banksType(response, gson)),
                error -> {
                    if (error.networkResponse != null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }) {
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
    public void sendEvaluation(String token,
                               String name,
                               String email,
                               String dni,
                               String phone,
                               long loanType,
                               Double amount,
                               RestListener<Evaluation> listener) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_OPEVALUATION,
                RestConverter.User.sendEvaluation(name, email, dni, phone, loanType, amount),
                response -> listener.onSuccess(RestDeserializer.EvaluationDeserializer.Evaluation(response)),
                error -> {
                    if (error.networkResponse != null){
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    } else
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
