package com.finz.rest;

public class RestConstant {
    public static final int TIMEOUT = 20000;

    static final String GRANT_TYPE = "password";
    static final String REFRESH_TOKEN = "refresh_token";
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
    public static final String BASIC = "Basic ";
    public static final String FORMAT = "%s:%s";
    public static final String CONTENT_TYPE = "Content-Type";

    public static final String ALT = "?alt=media";
    public static final String SLIDERS = "sliders%2F";

    private static final String V1 = "/rest/v1";
    private static final String PUBLIC = V1 + "/public";
    private static final String SECURED = V1 + "/secured";


    public static final String ENDPOINT_TOKEN = "/oauth/token";
    public static final String ENDPOINT_SLIDER =PUBLIC + "/slider";
    private static final String ENDPOINT_USER = SECURED + "/user";
    public static final String ENDPOINT_PARAM = SECURED + "/param";
    public static final String ENDPOINT_BANK = SECURED + "/corebank";
    public static final String ENDPOINT_DISPOSITION = SECURED + "/opdisposition";
    public static final String ENDPOINT_OPEVALUATION = SECURED + "/op_evaluation";
    private static final String ENDPOINT_USER_PUBLIC = PUBLIC + "/user";
    public static final String ENDPOINT_USER_SIGNUP = ENDPOINT_USER_PUBLIC + "/signup";
    public static final String ENDPOINT_USER_INFO = ENDPOINT_USER + "/me";
    public static final String ENDPOINT_USER_RECOVER_PASS = PUBLIC + "/user/recoverpass";
    public static final String ENDPOINT_USER_CHANGE_PASS = ENDPOINT_USER + "/changepass";
    public static final String ENDPOINT_LOAN_TYPE = SECURED + "/loan_type";
    private static final String ENDPOINT_HISTORY_EVALUATION = ENDPOINT_OPEVALUATION + "/me";
    private static final String ENDPOINT_HISTORY_DISPOSITION = ENDPOINT_DISPOSITION + "/me";

    private static final String PAR_PAGE = "page=";

    public static String pathPageHistoryEvaluation(int page) {
        return ENDPOINT_HISTORY_EVALUATION + "?" + PAR_PAGE + page;
    }

    public static String pathPageHistoryDisposition(int page) {
        return ENDPOINT_HISTORY_DISPOSITION + "?" + PAR_PAGE + page;
    }

}
