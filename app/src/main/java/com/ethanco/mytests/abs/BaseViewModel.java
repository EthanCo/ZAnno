package com.ethanco.mytests.abs;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ethanco.zanno.ZAnnoInjector;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Description ViewModel基类
 * Created by EthanCo on 2016/6/13.
 */
public abstract class BaseViewModel<T> {

    protected static final String TAG = "Z-BaseViewModel";
    protected Reference<T> mViewRef; //View接口类型的弱引用

    @CallSuper
    public void attachView(T view) { //建立关联
        mViewRef = new WeakReference<T>(view);
    }

    @NonNull
    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @CallSuper
    public void detachView() {
        Log.i(TAG, "detachView1 : ");
        ZAnnoInjector.inject(this);

        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
