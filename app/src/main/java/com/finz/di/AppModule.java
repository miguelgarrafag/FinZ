package com.finz.di;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.finz.FinZApp;
import com.finz.rest.slider.RestSlider;
import com.finz.rest.slider.RestSliderImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.finz.aplication.ApplicationPreferences;
import com.finz.rest.firebaseToken.RestFirebaseToken;
import com.finz.rest.firebaseToken.RestFirebaseTokenImpl;
import com.finz.rest.token.RestToken;
import com.finz.rest.token.RestTokenImpl;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    @Provides
    Context provideContext(FinZApp app) {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));

        return builder.create();
    }

    @Provides
    @Singleton
    RequestQueue provideRequestQueue(FinZApp app) {
        return Volley.newRequestQueue(app);
    }

    //PREFERENCES
    @Provides
    @Singleton
    ApplicationPreferences providesAppPreference(Context ctx) {
        return new ApplicationPreferences(ctx);
    }

    //REST
    @Provides
    RestToken provideRestToken(RequestQueue queue, Gson gson, Context ctx) {
        return new RestTokenImpl(queue, gson, ctx);
    }

    @Provides
    RestFirebaseToken provideRestFirebaseToken(RequestQueue queue, Context ctx){
        return new RestFirebaseTokenImpl(queue,ctx);
    }

    @Provides
    RestSlider provideRestSlider(RequestQueue queue, Gson gson, Context ctx){
        return new RestSliderImpl(queue, gson, ctx);
    }
}
