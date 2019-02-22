package com.finz.rest;

public class RestConstant {
    public static final int TIMEOUT = 20000;

    static final String GRANT_TYPE = "password";
    static final String REFRESH_TOKEN = "refresh_token";
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";

    private static final String V1 = "/v1";

    public static final String ENDPOINT_TOKEN = V1 + "/oauth/token";
    public static final String ENDPOINT_ORDERS = V1 + "/mobile/orders";
    public static final String ENDPOINT_ORDERS_DELIVERY = V1 + "/mobile/orders_delivery";
    public static final String ENDPOINT_PICKINGS_PROVIDER = V1 + "/mobile/pickings_provider";
    public static final String ENDPOINT_COMMENT = V1 + "/mobile/comments";
    public static final String ENDPOINT_QUANTITY = V1 + "/mobile/delivery_quantity";
    public static final String ENDPOINT_DELIVERY = V1 + "/mobile/delivery";
    public static final String ENDPOINT_FINISH = V1 + "/mobile/finish";
    public static final String ENDPOINT_PRICE_PRODUCT_LIST = V1 + "/mobile/raise_prices";
    public static final String ENDPOINT_PRICE_QUANTITY = V1 + "/mobile/prices_quantities";
    public static final String ENDPOINT_CHANGE_PRICE = V1 + "/mobile/raise_prices";
    public static final String ENDPOINT_DELIVERY_COMMENT = V1 + "/mobile/delivery_comments";
    public static final String ENDPOINT_PACKING_CHECK = V1 + "/mobile/packing_check";
    public static final String ENDPOINT_RAISE_PRICES_PICKER = V1 + "/mobile/raise_prices_picker";

    private static final String PAR_QPRODUCT = "qproduct=";
    private static final String PAR_PAGE = "page=";

    public static String pathPriceProductList(String query, int page) {
        if(query==null)
            return ENDPOINT_PRICE_PRODUCT_LIST + "?" + PAR_PAGE + page;
        else
            return ENDPOINT_PRICE_PRODUCT_LIST + "?" + PAR_QPRODUCT + query.replace(" ", "%20") + "&"  + PAR_PAGE + page;
    }

    public static String patPickingList(String query) {
        if(query==null)
            return ENDPOINT_PICKINGS_PROVIDER;
        else
            return ENDPOINT_PICKINGS_PROVIDER + "?" + PAR_QPRODUCT + query.replace(" ", "%20");
    }

}
