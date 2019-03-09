package com.finz.rest.user;

import com.finz.rest.RestEmptyListener;
import com.finz.rest.RestListener;
import com.finz.rest.user.entity.User;

/**
 * @author SudTechnologies
 */
public interface RestUser {

    void register(String email, String password, String name, String lastName,
                  String phone, String dni, final RestListener<User> listener);

    void userInfo(String token, final RestListener<User> listener);

    void recoverPwd(String email, final RestEmptyListener listener);

    void changePwd(String oldPass, String newPass, String token,
                   final RestEmptyListener listener);
}
