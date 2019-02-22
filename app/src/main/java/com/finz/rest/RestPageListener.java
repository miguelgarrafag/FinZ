package com.finz.rest;

/**
 * @author SudTechnologies
 */
public interface RestPageListener<T> extends RestError {

    void onSuccess(Pageable<T> result);
}
