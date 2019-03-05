package com.finz.rest.slider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.finz.R;
import com.finz.RestDinamicConstant;
import com.finz.rest.RestConstant;
import com.finz.rest.RestDeserializer;
import com.finz.rest.RestListListener;
import com.finz.rest.slider.entity.Slider;
import com.google.gson.Gson;

import javax.inject.Singleton;

@Singleton
public class RestSliderImpl implements RestSlider {
    private final Toast toast;
    private RequestQueue queue;
    private Gson gson;

    @SuppressLint("ShowToast")
    public RestSliderImpl(RequestQueue queue, Gson gson, Context ctx) {
        this.queue = queue;
        this.gson = gson;
        toast = Toast.makeText(ctx, R.string.str_error_internet, Toast.LENGTH_LONG);
    }

    @Override
    public void list(final RestListListener<Slider> listener) {
        JsonArrayRequest req = new JsonArrayRequest(
                RestDinamicConstant.URL_BASE + RestConstant.ENDPOINT_SLIDER,
                response -> listener.onSuccess(RestDeserializer.SliderDeserializer.Sliders(response, gson)),
                error -> {
                    if (error.networkResponse!=null)
                        listener.onError(error.networkResponse.statusCode, error.getMessage());
                    else
                        toast.show();
                }
        );
        req.setRetryPolicy(new DefaultRetryPolicy(RestConstant.TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }

}
