package com.finz.rest;

import android.util.Log;

import com.android.volley.VolleyError;
import com.finz.rest.evaluation.entity.Evaluation;
import com.finz.rest.slider.entity.Slider;
import com.finz.rest.type.entity.Type;
import com.finz.rest.user.entity.User;
import com.finz.rest.utils.entity.Bank;
import com.finz.rest.utils.entity.Param;
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
    private static final String KEY_NAME = "name";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DNI = "dni";
    private static final String KEY_PURCHASE = "purchases";
    private static final String KEY_RANGE = "range";
    private static final String KEY_POINTS = "points";

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

        public static User user(JSONObject response, Gson gson) {
            try {
                return new User(response.getInt(KEY_ID),
                        response.getString(KEY_EMAIL),
                        response.getString(KEY_NAME),
                        response.getString(KEY_LAST_NAME),
                        response.getString(KEY_PHONE),
                        response.getString(KEY_DNI));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    public static class UtilDeserializer {
        public static Param param(JSONObject response, Gson gson) {
            return gson.fromJson(response.toString(), Param.class);
        }

        public static List<Bank> banks(JSONArray response, Gson gson) {
            try {
                List<Bank> result = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    result.add(gson.fromJson(response.getJSONObject(i).toString(), Bank.class));
                }
                return result;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
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

    public static class TypeDeserializer {
        public static List<Type> Types(JSONArray array, Gson gson) {
            List<Type> result = new ArrayList<>();
            try {
                for (int i = 0; i < array.length(); i++) {
                    result.add(new Type(array.getJSONObject(i).getLong("id"), array.getJSONObject(i).getString("name")));
                }
                return result;
            } catch (JSONException e) {
                return null;
            }
        }
    }

    public static class EvaluationDeserializer {
        public static Evaluation Evaluation(JSONObject response, Gson gson) {
            try {
                return new Evaluation(response.getInt("id"),response.getString("codOperation"));
            } catch (JSONException e) {
                e.printStackTrace();
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
