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

    public static class Order {
        private static final String KEY_ID = "id";
        private static final String KEY_PRODUCTS_ID = "products_id";
        private static final String KEY_PRICE = "price";
        private static final String KEY_QUANTITY = "quantity";
        private static final String KEY_COMMENT = "comment";
        private static final String KEY_CODE = "code";
        private static final String KEY_PRODUCT_PACKED = "product_packed";

        public static JSONObject priceQuantity(long id, BigDecimal price, BigDecimal quantity) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_ID, id);
                obj.put(KEY_PRICE, price.doubleValue());
                obj.put(KEY_QUANTITY, quantity.doubleValue());
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static JSONObject price(long id, BigDecimal price) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_PRODUCTS_ID, id);
                obj.put(KEY_PRICE, price.doubleValue());
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static JSONObject quantity(long id, BigDecimal quantity) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_ID, id);
                obj.put(KEY_QUANTITY, quantity.doubleValue());
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static JSONObject deliveryFinished(String code) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_CODE, code);
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static JSONObject comment(long id, String comment) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_ID, id);
                obj.put(KEY_COMMENT, comment);
                return obj;
            } catch (JSONException ex) {
                Log.w(TAG, ex.getMessage());
                return null;
            }
        }

        public static JSONObject check(long id, boolean val) {
            try {
                JSONObject obj = new JSONObject();
                obj.put(KEY_ID, id);
                obj.put(KEY_PRODUCT_PACKED, val);
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
