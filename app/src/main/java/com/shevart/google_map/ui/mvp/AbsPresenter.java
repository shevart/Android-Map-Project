package com.shevart.google_map.ui.mvp;

import android.support.annotation.NonNull;

import static com.shevart.google_map.util.Util.checkNonNull;

public class AbsPresenter<V extends BaseView> implements BasePresenter<V> {
    private V view;

    @Override
    public void attachView(@NonNull V view) {
        checkNonNull(view);
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    protected boolean isViewAttach() {
        return view != null;
    }

    public V getView() {
        return view;
    }
}
