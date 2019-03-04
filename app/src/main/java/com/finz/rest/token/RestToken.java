package com.finz.rest.token;

import com.finz.rest.RestListener;
import com.finz.rest.token.entity.Token;

public interface RestToken {

    void refreshToken(String refreshToken, final RestListener<Token> listener);

    void requestAccessToken(String email, String password, final RestListener<Token> listener);
}
