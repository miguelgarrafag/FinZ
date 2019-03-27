package com.finz.rest.utils;

import com.finz.rest.RestEmptyListener;
import com.finz.rest.RestListListener;
import com.finz.rest.RestListener;
import com.finz.rest.utils.entity.Evaluation;
import com.finz.rest.utils.entity.BankType;
import com.finz.rest.utils.entity.Param;

/**
 * @author SudTechnologies
 */
public interface RestUtil {

    void params(String token, final RestListener<Param> listener);

    void banks(String token, final RestListListener<BankType> listener);

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

    void loadType(String token, final RestListListener<BankType> listener);

    void sendEvaluation(String token,
                        String name,
                        String email,
                        String dni,
                        String phone,
                        long loanType,
                        Double amount,
                        final RestListener<Evaluation> listener);

}
