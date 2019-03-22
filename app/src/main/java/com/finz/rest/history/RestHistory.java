package com.finz.rest.history;

import com.finz.rest.RestListListener;
import com.finz.rest.history.entity.DispositionHistory;
import com.finz.rest.history.entity.EvaluationHistory;

public interface RestHistory {

    void evaluationHistory(String accessToken, final RestListListener<EvaluationHistory> listener);

    void dispositionHistory(String accessToken, final RestListListener<DispositionHistory> listener);
}
