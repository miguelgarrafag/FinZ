package com.finz.rest.utils;

import com.finz.rest.RestEmptyListener;
import com.finz.rest.RestListListener;
import com.finz.rest.RestListener;
import com.finz.rest.utils.entity.Bank;
import com.finz.rest.utils.entity.Param;

/**
 * @author SudTechnologies
 */
public interface RestUtil {

    void params(String token, final RestListener<Param> listener);

    void banks(String token, final RestListListener<Bank> listener);

    void disposition(String token,String tokenC, String nameOnCard,
                     String email,
                     String phone,
                     String accountType,
                     String nroDestiny,
                     long bank,
                     String photoSignature,
                     String photoDni,
                     double amount,
                     final RestEmptyListener listener);

}
