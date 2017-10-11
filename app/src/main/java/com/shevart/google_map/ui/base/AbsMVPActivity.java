package com.shevart.google_map.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shevart.google_map.ui.mvp.BasePresenter;
import com.shevart.google_map.ui.mvp.BaseView;

public abstract class AbsMVPActivity<P extends BasePresenter<V>, V extends BaseView> extends AbsActivity {
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = obtainPresenter();
    }

    protected abstract P obtainPresenter();

    public P getPresenter() {
        return presenter;
    }
}