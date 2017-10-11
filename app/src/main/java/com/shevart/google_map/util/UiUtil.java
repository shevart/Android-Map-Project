package com.shevart.google_map.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class UiUtil {
    public static View inflate(@NonNull ViewGroup parent, @LayoutRes int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    public static void disableKeyboardOpening(@NonNull final EditText editText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
        } else {
            try {
                @SuppressWarnings("RedundantArrayCreation")
                final Method method =
                        EditText.class.getMethod("setShowSoftInputOnFocus", new Class[]{boolean.class});
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static int getColor(@NonNull Context context, @ColorRes int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorId, context.getTheme());
        } else {
            return context.getResources().getColor(colorId);
        }
    }
}
