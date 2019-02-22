package com.finz.dialogFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.sudtechnologies.mainmayorizta.R;
import com.sudtechnologies.mainmayorizta.constant.ConstantsCore;
import com.sudtechnologies.mainmayorizta.rest.order.entity.Order;
import com.sudtechnologies.mainmayorizta.util.UtilCore;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDialogFragment extends BaseDialogFragment {

    public static final String TAG = OrderDialogFragment.class.getSimpleName();

    @BindView(R.id.order)
    TextView order;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.purchase_date)
    TextView emapurchaseDate;

    @BindView(R.id.delivery_date)
    TextView deliveryDate;

    @BindView(R.id.payment_method)
    TextView paymentMethod;

    @BindView(R.id.payment_status)
    TextView paymentStatus;

    @BindView(R.id.subtotal)
    TextView subtotal;

    @BindView(R.id.delivery)
    TextView delivery;

    @BindView(R.id.total)
    TextView total;

    private Order orderObject;

    public static OrderDialogFragment newInstance(Order order) {
        OrderDialogFragment frag = new OrderDialogFragment();
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
        order.setText(getString(R.string.blank_2_string_ns, getString(R.string.label_hashtag), orderObject.getCode()));
        name.setText(orderObject.getName());
        phone.setText(orderObject.getPhone());
        address.setText(orderObject.getAddress_delivery());
        email.setText(orderObject.getEmail());
        emapurchaseDate.setText(UtilCore.UtilDate.formatDateYear(Objects.requireNonNull(getContext()), orderObject.getShopping_date()));
        deliveryDate.setText(UtilCore.UtilDate.formatDateYear(getContext(), orderObject.getDelivery_date()));
        paymentMethod.setText(orderObject.getPayment_method());
        paymentStatus.setText(orderObject.getPayment_status());
        subtotal.setText(getString(R.string.blank_soles, orderObject.getSub_total()));
        delivery.setText(getString(R.string.blank_soles, orderObject.getShipping_price()));
        total.setText(getString(R.string.blank_soles, orderObject.getTotal()));
    }

    @OnClick(R.id.order_details)
    void onDetailsClick(){
        FragmentManager fm = getFragmentManager();
        OrderDetailDialogFragment dialogFragment = OrderDetailDialogFragment.newInstance(orderObject);
        dialogFragment.show(Objects.requireNonNull(fm), OrderDetailDialogFragment.TAG);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_fragment_order;
    }
}
