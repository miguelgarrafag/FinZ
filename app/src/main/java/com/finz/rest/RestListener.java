package com.finz.rest;

public interface RestListener<T> extends RestError {
    void onSuccess(T t);
}
