package com.finz.constant;

public class ConstantsCore {
    public static class Utilities {
        public static final int ZERO = 0;
    }

    public static class HttpStatus {
        public static final int NOT_FOUND = 404;
        public static final int CONFLICT = 409;
        public static final int UNAUTHORIZED = 401;
        public static final int BAD_REQUEST = 400;
    }

    public static class Intent {
        public static final String ORDER = "order";
        public static final String PICKS = "picks";
        public static final String PICK = "pick";
        public static final String LOGOUT = "logout";
    }

    public static class Bundle {
        public static final String ORDER = "order";
        public static final String NRO_ORDER = "nroOrder";
    }

    public static class FDataBase {
        public static final String ORDER_PURCHASE = "orderPurchase";
        public static final String ORDER_PICKING = "orderPicking";
        public static final String ORDER_DELIVERY = "orderDelivery";
        public static final String NRO_ORDER = "nroOrder";
        public static final String DETAILS = "details";
        public static final String CODE = "code";
    }

    public static class FStorage {
        public static final String SIGNATURE_FOLDER = "signature/";
    }

    public static class LocalStorage {
        public static final String SIGNATURE = "signature";
    }

    public static class Service {
        public static final String EXTRA_DELETE_FIREBASE_TOKEN = "extra_firebase_token";
    }
}
