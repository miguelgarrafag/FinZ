package com.finz.rest.history;


import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.finz.R;
import com.finz.RestDinamicConstant;
import com.finz.rest.RestConstant;
import com.finz.rest.RestDeserializer;
import com.finz.rest.RestListListener;
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

    @Override
    public void evaluationHistory(String accessToken, RestListListener<EvaluationHistory> listener) {
        JsonArrayRequest req=new JsonArrayRequest(
                Request.Method.GET,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_HISTORY_EVALUATION,
                null,
                response -> listener.onSuccess(RestDeserializer.HistoryDeserializer.evaluations(response, gson)),
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
    public void dispositionHistory(String accessToken, RestListListener<DispositionHistory> listener) {
        JsonArrayRequest req=new JsonArrayRequest(
                Request.Method.GET,
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_HISTORY_EVALUATION,
                null,
                response -> listener.onSuccess(RestDeserializer.HistoryDeserializer.dispositions(response, gson)),
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
