package com.finz.rest.history;

import com.finz.rest.RestEmptyListener;
import com.finz.rest.RestListener;
import com.finz.rest.history.entity.History;

public interface RestFirebaseToken {

    void saveToken(String firebaseToken, String accessToken, final RestListener<History> listener);

    void deleteToken(long idToken, String accessToken, final RestEmptyListener listener);
}
