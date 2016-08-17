package com.ethanco.mytests;

import android.util.Log;

import com.ethanco.mytests.abs.BaseViewModel;
import com.ethanco.zanno.AutoDestory;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by EthanCo on 2016/8/17.
 */
public class MyViewModel extends BaseViewModel<MyView> {

    /**
     * @AutoDestory 自动调用unsubscribe，无需再手动调用
     * 在需要调用unsubscribe的地方添加 ZAnnoInjector.inject(this); 即可
     * 注意: Subscription的修饰符不能是private
     */
    @AutoDestory
    Subscription subscription;

    /**
     * 加载数据
     */
    public void loadData() {
        getView().show("开始加载数据");
        subscription = Observable.just("加载数据成功:qwerbsdfgfg")
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        getView().show(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        getView().show("加载数据失败" + throwable.getMessage());
                    }
                }, new Action0() {

                    @Override
                    public void call() {
                        Log.i(TAG, "Completed");
                    }
                });
    }
}
