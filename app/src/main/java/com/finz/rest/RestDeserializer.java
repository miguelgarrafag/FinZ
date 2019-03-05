package com.finz.rest;

import android.util.Log;

import com.finz.rest.slider.entity.Slider;
import com.google.gson.Gson;
import com.finz.rest.firebaseToken.entity.FirebaseToken;
import com.finz.rest.token.entity.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestDeserializer {

    private static final String TAG = RestDeserializer.class.getSimpleName();
    private static final String KEY_ID = "id";
    private static final String KEY_RECORDS = "records";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_TOKEN = "token";

    public static class UserDeserializer {

        public static String token(JSONObject obj) {
            try {
                return obj.getJSONObject(KEY_RECORDS).getString(KEY_ACCESS_TOKEN);
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static Token accessToken(String response, Gson gson) {
            return gson.fromJson(response, Token.class);
        }
    }

    public static class SliderDeserializer {

        public static List<Slider> Sliders(JSONArray response, Gson gson) {
            try {
                List<Slider> result = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    result.add(gson.fromJson(response.getJSONObject(i).toString(), Slider.class));
                }
                return result;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }
    }

    public static class FirebaseTokenDeserializer {

        public static FirebaseToken firebaseToken(JSONObject response) {
            try {
                return new FirebaseToken(response.getLong(KEY_ID),
                        response.getString(KEY_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}