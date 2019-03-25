package com.finz.rest.type;

import com.finz.rest.RestListListener;
import com.finz.rest.type.entity.Type;
import com.finz.rest.user.entity.User;

/**
 * @author SudTechnologies
 */
public interface RestType {
    void loadType(String token, final RestListListener<Type> listener);
}
