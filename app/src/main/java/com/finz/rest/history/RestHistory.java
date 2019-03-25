package com.finz.rest.history;

import com.finz.rest.RestPageListener;
import com.finz.rest.history.entity.DispositionHistory;
import com.finz.rest.history.entity.EvaluationHistory;

public interface RestHistory {

    void evaluationHistory(String accessToken, int page, final RestPageListener<EvaluationHistory> listener);

    void dispositionHistory(String accessToken, int page, final RestPageListener<DispositionHistory> listener);
}
