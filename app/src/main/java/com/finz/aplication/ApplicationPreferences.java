package com.finz.aplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.finz.rest.token.entity.Token;

public class ApplicationPreferences {

    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER = "user";
    private static final String KEY_FIREBASE_TOKEN = "firebase_token";
    private static final String KEY_FIREBASE_TOKEN_ID = "firebase_token_id";
    private static final String KEY_FIREBASE_TOKEN_STATUS = "firebase_token_status";

    private static final String KEY_SLIDERS = "sliders";

    private SharedPreferences prefs;
    private Gson gson;

    public ApplicationPreferences(Context ctx) {
        prefs = ctx.getSharedPreferences("appPreference", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void setSliders(boolean val) {
        prefs.edit().putBoolean(KEY_SLIDERS, val).apply();
    }

    public boolean getSliders() {
        return prefs.getBoolean(KEY_SLIDERS,false);
    }


    public void setFirebaseToken(String token) {
        prefs.edit().putString(KEY_FIREBASE_TOKEN, token).apply();
    }

    public String getFirebaseToken() {
        return prefs.getString(KEY_FIREBASE_TOKEN,null);
    }

    public void setFirebaseTokenStatus(boolean status) {
        prefs.edit().putBoolean(KEY_FIREBASE_TOKEN_STATUS, status).apply();
    }

    public boolean getFirebaseTokenStatus() {
        return prefs.getBoolean(KEY_FIREBASE_TOKEN_STATUS,false);
    }

    public void setFirebaseTokenID(long token) {
        prefs.edit().putLong(KEY_FIREBASE_TOKEN_ID, token).apply();
    }

    public long getFirebaseTokenID() {
        return prefs.getLong(KEY_FIREBASE_TOKEN_ID,0);
    }

    public void setUser(String user) {
        prefs.edit().putString(KEY_USER, user).apply();
    }

    public String getUser() {
        return prefs.getString(KEY_USER,null);
    }

    public void setToken(Token token) {
        String json = gson.toJson(token);
        prefs.edit().putString(KEY_TOKEN, json).apply();
    }

    public Token getToken() {
        String json = prefs.getString(KEY_TOKEN, null);
        return gson.fromJson(json, Token.class);
    }

    public void clearall() {
        setFirebaseTokenStatus(true);
        setFirebaseTokenID(0);
        setToken(null);
        setUser(null);
    }

}