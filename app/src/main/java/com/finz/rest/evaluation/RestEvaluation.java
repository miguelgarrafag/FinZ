package com.finz.rest.evaluation;

import com.finz.rest.RestListListener;
import com.finz.rest.RestListener;
import com.finz.rest.evaluation.entity.Evaluation;
import com.finz.rest.type.entity.Type;

/**
 * @author SudTechnologies
 */
public interface RestEvaluation {
    void sendEvaluation(String token, String name, String email, String dni, String phone, Integer loanType,
                        Double amount,final RestListener<Evaluation> listener);
}
