package com.finz.rest;

import android.util.Log;

import com.finz.RestDinamicConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;

public class RestConverter {

    private static final String TAG = RestConverter.class.getSimpleName();


    public static class FirebaseToken {
        private static final String KEY_TOKEN = "token";

        public static JSONObject firebaseToken(String firebaseToken) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_TOKEN, firebaseToken);
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }
    }

    public static class Util {
        private static final String KEY_NAME_ON_CARD = "nameOnCard";
        private static final String KEY_EMAIL = "email";
        private static final String KEY_PHONE = "phone";
        private static final String KEY_ACOUNT_TYPE = "accountType";
        private static final String KEY_NRO_DETINY = "nroDestiny";
        private static final String KEY_BANK = "bank";
        private static final String KEY_PHOTO_SIGNATURE = "photoSignature";
        private static final String KEY_PHOTO_DNI = "photoDni";
        private static final String KEY_AMOUNT = "amount";
        private static final String KEY_TOKEN_ID = "tokenId";

        public static JSONObject disposition(String tokenC,
                                             String nameOnCard,
                                             String email,
                                             String phone,
                                             String accountType,
                                             String nroDestiny,
                                             long bank,
                                             String photoSignature,
                                             String photoDni,
                                             double amount) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_TOKEN_ID, tokenC);
                obj.put(KEY_NAME_ON_CARD, nameOnCard);
                obj.put(KEY_EMAIL, email);
                obj.put(KEY_PHONE, phone);
                obj.put(KEY_ACOUNT_TYPE, accountType);
                obj.put(KEY_NRO_DETINY, nroDestiny);
                obj.put(KEY_BANK, bank);
                obj.put(KEY_PHOTO_SIGNATURE, photoSignature);
                obj.put(KEY_PHOTO_DNI, photoDni);
                obj.put(KEY_AMOUNT, amount);
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }
    }

    public static class User {
        private static final String KEY_EMAIL = "email";
        private static final String KEY_PASSWORD = "password";
        private static final String KEY_NAME = "name";
        private static final String KEY_LAST_NAME = "lastName";
        private static final String KEY_PHONE = "phone";
        private static final String KEY_DNI = "dni";
        private static final String KEY_OLD_PASS = "oldPass";
        private static final String KEY_NEW_PASS = "newPass";
        private static final String KEY_LOAN_TYPE = "loanType";
        private static final String KEY_AMOUNT = "amount";

        public static JSONObject resetPass(String email) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_EMAIL, email);
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static JSONObject changePass(String oldPass, String newPass) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_OLD_PASS, oldPass);
                obj.put(KEY_NEW_PASS, newPass);
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static JSONObject register(String email, String password, String name,
                                          String lastName, String phone, String dni) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_EMAIL, email);
                obj.put(KEY_PASSWORD, password);
                obj.put(KEY_NAME, name);
                obj.put(KEY_LAST_NAME, lastName);
                obj.put(KEY_PHONE, phone);
                obj.put(KEY_DNI, dni);
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static JSONObject sendEvaluation(String name, String email, String dni, String phone, int loanType,
                                                Double amount) {

            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_NAME, name);
                obj.put(KEY_EMAIL, email);
                obj.put(KEY_DNI, dni);
                obj.put(KEY_PHONE, phone);
                obj.put(KEY_LOAN_TYPE, loanType);
                obj.put(KEY_AMOUNT, amount);
                Log.e("OBJECT", obj.toString());
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }

        }
    }

    public static class Token {

        private static final String KEY_USERNAME = "username";
        private static final String KEY_PASSWORD = "password";
        private static final String KEY_GRANT_TYPE = "grant_type";
        private static final String KEY_CLIENT_ID = "client_id";
        private static final String KEY_CLIENT_SECRET = "client_secret";
        private static final String KEY_REFRESH_TOKEN = "refresh_token";

        public static HashMap<String, String> requestAccessToken(String email, String password) {
            HashMap<String, String> obj = new HashMap<>();
            obj.put(KEY_USERNAME, email);
            obj.put(KEY_PASSWORD, password);
            obj.put(KEY_GRANT_TYPE, RestConstant.GRANT_TYPE);
            obj.put(KEY_CLIENT_ID, RestDinamicConstant.CLIENT_ID);
            obj.put(KEY_CLIENT_SECRET, RestDinamicConstant.CLIENT_SECRET);
            return obj;
        }

        public static HashMap<String, String> requestRefreshToken(String refreshToken) {
            HashMap<String, String> obj = new HashMap<>();
            obj.put(KEY_REFRESH_TOKEN, refreshToken);
            obj.put(KEY_GRANT_TYPE, RestConstant.REFRESH_TOKEN);
            obj.put(KEY_CLIENT_ID, RestDinamicConstant.CLIENT_ID);
            obj.put(KEY_CLIENT_SECRET, RestDinamicConstant.CLIENT_SECRET);
            return obj;
        }
    }

}
