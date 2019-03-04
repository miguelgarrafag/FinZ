package com.finz.rest.firebaseToken;

import com.finz.rest.RestEmptyListener;
import com.finz.rest.RestListener;
import com.finz.rest.firebaseToken.entity.FirebaseToken;

public interface RestFirebaseToken {

    void saveToken(String firebaseToken, String accessToken, final RestListener<FirebaseToken> listener);

    void deleteToken(long idToken, String accessToken, final RestEmptyListener listener);
}
