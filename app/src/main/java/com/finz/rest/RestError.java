package com.finz.rest;

public interface RestError {

    void onError(int statusCode, String message);
}
