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

    public static class FStorage {
        public static final String SIGNATURE_FOLDER = "signature/";
        public static final String DNI_FOLDER = "dni/";
    }

    public static class LocalStorage {
        public static final String SIGNATURE = "signature";
        public static final String DNI = "dni";
    }
}
