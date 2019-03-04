package com.finz.rest;

public class RestConstant {
    public static final int TIMEOUT = 20000;

    static final String GRANT_TYPE = "password";
    static final String REFRESH_TOKEN = "refresh_token";
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";

    private static final String V1 = "/rest/v1";
    private static final String PUBLIC = V1 + "/public";

    public static final String ENDPOINT_TOKEN = "/oauth/token";
    public static final String ENDPOINT_SLIDER =PUBLIC + "/slider";


}
