package com.finz.rest;

import java.util.List;

public interface RestListListener<T> extends RestError {

    void onSuccess(List<T> result);

}
