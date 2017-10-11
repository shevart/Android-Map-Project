package com.shevart.google_map.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.shevart.google_map.R;
import com.shevart.google_map.ui.mvp.BasePresenter;
import com.shevart.google_map.ui.mvp.BaseView;
import com.shevart.google_map.util.UiNotificationsUtils;

public abstract class AbsMVPActivity<P extends BasePresenter<V>, V extends BaseView>
        extends AbsActivity implements BaseView {
    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = obtainPresenter();
        presenter.attachView(obtainView());
    }

    protected abstract P obtainPresenter();

    protected abstract V obtainView();

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showNoInternetConnectionError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(R.string.attention)
                .setMessage(R.string.no_internet_connection)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }

    @Override
    public void showError(@NonNull String errorMessage) {
        UiNotificationsUtils.showEmptyToast(this, errorMessage);
    }
}