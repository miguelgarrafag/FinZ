package com.finz.rest.evaluation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import com.finz.rest.RestListListener;
import com.finz.rest.RestListener;
import com.finz.rest.evaluation.entity.Evaluation;
import com.finz.rest.type.RestType;
import com.finz.rest.type.entity.Type;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class RestEvaluationImpl implements RestEvaluation {
    private final Toast toast;
    private RequestQueue queue;
    private Gson gson;

    @SuppressLint("ShowToast")
    public RestEvaluationImpl(RequestQueue queue, Gson gson, Context ctx) {
        this.queue = queue;
        this.gson = gson;
        toast = Toast.makeText(ctx, R.string.str_error_internet, Toast.LENGTH_LONG);
    }

    @Override
    public void sendEvaluation(String token, String name, String email, String dni, String phone,
                               Integer loanType, Double amount, RestListener<Evaluation> listener) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_EVALUATE,
                RestConverter.User.sendEvaluation(name, email, dni, phone, loanType, amount),
                response -> listener.onSuccess(RestDeserializer.EvaluationDeserializer.Evaluation(response, gson)),
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




    /*
    @Override
    public void loadType(final String token, RestListListener<Type> listener) {
        JsonArrayRequest req = new JsonArrayRequest(
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_LOAN_TYPE,
                response -> listener.onSuccess(RestDeserializer.TypeDeserializer.Types(response, gson)),
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
    }*/
}
