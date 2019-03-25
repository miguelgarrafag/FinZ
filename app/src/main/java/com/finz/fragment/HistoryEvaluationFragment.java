package com.finz.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.finz.R;
import com.finz.adapter.EvaluationHistoryAdapter;
import com.finz.listener.EndlessRecyclerViewScrollListener;
import com.finz.rest.Pageable;
import com.finz.rest.RestPageListener;
import com.finz.rest.history.RestHistory;
import com.finz.rest.history.entity.EvaluationHistory;
import com.finz.util.UtilCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;

public class HistoryEvaluationFragment extends BaseFragment{

    private static final String TAG = HistoryEvaluationFragment.class.getSimpleName();

    @Inject
    RestHistory restHistory;

    @BindView(R.id.histories)
    RecyclerView histories;

    @BindView(R.id.no_items)
    LinearLayout noItems;

    private Pageable pageable;
    private List<EvaluationHistory> items = new ArrayList<>();
    private EvaluationHistoryAdapter adapter;

    public HistoryEvaluationFragment() {
    }

    public static HistoryEvaluationFragment newInstance() {
        return new HistoryEvaluationFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidSupportInjection.inject(this);
        setHasOptionsMenu(true);

        init();
    }

    private void init() {
        adapter = new EvaluationHistoryAdapter(items, getContext());
        histories.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        histories.setLayoutManager(linearLayoutManager);
        histories.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {loadMoreProducts();}
        });

        RestItems(0);
    }

    private void loadMoreProducts() {
        if (pageable != null && !pageable.isLast())
            RestItems(pageable.getPage() + 1);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_history;
    }

    @SuppressWarnings("unchecked")
    public void RestItems(int page){
        if (!UtilCore.UtilNetwork.isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            showToastConnection();
            return;
        }
        restHistory.evaluationHistory(prefs.getToken().getAccessToken(), page, new RestPageListener<EvaluationHistory>() {
            @Override
            public void onSuccess(Pageable result) {
                if(result!=null && !result.getItems().isEmpty()){
                    pageable = result;
                    noItems.setVisibility(View.GONE);
                    items.addAll(result.getItems());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int statusCode, String message) {
                validateErrorResponse(TAG, statusCode, message,
                        null, null, null,
                        () -> RestItems(page), null);
            }
        });
    }
}
