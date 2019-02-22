package com.finz.dialogFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sudtechnologies.mainmayorizta.R;
import com.sudtechnologies.mainmayorizta.adapter.OrderInfoAdapter;
import com.sudtechnologies.mainmayorizta.constant.ConstantsCore;
import com.sudtechnologies.mainmayorizta.rest.order.entity.Order;

import butterknife.BindView;

public class OrderDetailDialogFragment extends BaseDialogFragment {

    public static final String TAG = OrderDetailDialogFragment.class.getSimpleName();

    @BindView(R.id.details)
    RecyclerView details;

    private Order orderObject;

    public static OrderDetailDialogFragment newInstance(Order order) {
        OrderDetailDialogFragment frag = new OrderDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantsCore.Bundle.ORDER, order);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments()!=null)
            orderObject = (Order) getArguments().getSerializable(ConstantsCore.Bundle.ORDER);

        setup();
    }

    private void setup() {
        OrderInfoAdapter adapter = new OrderInfoAdapter(getContext(), orderObject.getDetail());
        details.setAdapter(adapter);
        details.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_fragment_order_detail;
    }
}
