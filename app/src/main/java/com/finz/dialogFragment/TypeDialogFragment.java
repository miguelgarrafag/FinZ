package com.finz.dialogFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.finz.R;
import com.finz.adapter.ListTypeAdapter;
import com.finz.rest.type.entity.Type;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class TypeDialogFragment extends BaseDialogFragment {

    public static final String TAG = TypeDialogFragment.class.getSimpleName();

    @BindView(R.id.recyclerType)
    RecyclerView recyclerType;

    private callBack listener;
    RecyclerView.LayoutManager layoutManager;
    ListTypeAdapter recyclerAdapter;
    private static List<Type> typeListm = new ArrayList<>();

    public static TypeDialogFragment newInstance(List<Type> listType) {
        fillTypes(listType);
        return new TypeDialogFragment();
    }

    private static void fillTypes(List<Type> typeList) {
        typeListm.clear();
        typeListm.addAll(typeList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerAdapter = new ListTypeAdapter(typeListm);
        recyclerType.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerType.setLayoutManager(layoutManager);
        recyclerType.setAdapter(recyclerAdapter);

        recyclerAdapter.setAddCallback(position -> {
            listener.onClose(typeListm.get(position));
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_fragment_type;
    }

    public interface callBack {
        void onClose(Type type);
    }

    public void setListener(callBack listener) {
        this.listener = listener;
    }
}
