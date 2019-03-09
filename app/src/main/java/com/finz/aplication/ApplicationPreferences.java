package com.finz.aplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.finz.rest.user.entity.User;
import com.google.gson.Gson;
import com.finz.rest.token.entity.Token;

public class ApplicationPreferences {

    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER = "user";
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

    public void setUser(User user) {
        prefs.edit().putString(KEY_USER, gson.toJson(user)).apply();
    }

    public User getUser() {
        return gson.fromJson(prefs.getString(KEY_USER, null), User.class);
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
        setToken(null);
        setUser(null);
    }

}