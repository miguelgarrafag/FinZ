package com.finz.rest.history;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.finz.R;
import com.finz.RestDinamicConstant;
import com.finz.rest.RestConstant;
import com.finz.rest.RestDeserializer;
import com.finz.rest.RestPageListener;
import com.finz.rest.history.entity.DispositionHistory;
import com.finz.rest.history.entity.EvaluationHistory;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
public class RestHistoryImpl implements RestHistory {

    private final Toast toast;
    private RequestQueue queue;
    private Gson gson;

    @SuppressLint("ShowToast")
    public RestHistoryImpl(RequestQueue queue, Gson gson, Context ctx) {
        this.queue = queue;
        this.gson = gson;
        toast = Toast.makeText(ctx, R.string.str_error_internet, Toast.LENGTH_LONG);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void evaluationHistory(String accessToken, int page, RestPageListener<EvaluationHistory> listener) {
        JsonObjectRequest req=new JsonObjectRequest(
                Request.Method.GET,
                RestDinamicConstant.URL_BASE + RestConstant.pathPageHistoryEvaluation(page),
                null,
                response -> listener.onSuccess(RestDeserializer.HistoryDeserializer.pageableEvaluations(response, gson)),
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

    @SuppressWarnings("unchecked")
    @Override
    public void dispositionHistory(String accessToken, int page, RestPageListener<DispositionHistory> listener) {
        JsonObjectRequest req=new JsonObjectRequest(
                Request.Method.GET,
                RestDinamicConstant.URL_BASE + RestConstant.pathPageHistoryDisposition(page),
                null,
                response -> listener.onSuccess(RestDeserializer.HistoryDeserializer.pageableDispositions(response, gson)),
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
}
