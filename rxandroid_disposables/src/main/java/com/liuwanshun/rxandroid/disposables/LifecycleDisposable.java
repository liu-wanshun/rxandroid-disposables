package com.liuwanshun.rxandroid.disposables;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.WeakHashMap;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @author liuwanshun
 */
public class LifecycleDisposable {
    private LifecycleDisposable() {
    }

    private static final WeakHashMap<Lifecycle, DisposableHolder> lifecycleDisposableMap = new WeakHashMap<>();
    private static final Object object = new Object();

    /**
     * 当lifecycleOwner的Lifecycle到达DESTROYED状态时执行dispose
     *
     * @param lifecycleOwner 关联这个lifecycleOwner的生命周期。
     *                       <p>
     *                       如果lifecycleOwner是{@link Fragment#getViewLifecycleOwner()},在{@link Fragment#onDestroyView() }之前dispose。
     *                       <p>
     *                       如果LifecycleOwner是{@link Fragment},在{@link Fragment#onDestroy() }之前dispose。
     *                       <p>
     *                       如果LifecycleOwner是{@link FragmentActivity},在{@link FragmentActivity#onDestroy() }之前dispose。
     * @return CompositeDisposable
     */
    public static CompositeDisposable from(LifecycleOwner lifecycleOwner) {
        return from(lifecycleOwner.getLifecycle());
    }

    /**
     * 当Lifecycle到达DESTROYED状态时执行dispose
     *
     * @param lifecycle 关联这个lifecycle的生命周期,也可以看看{@link #from(LifecycleOwner)}}
     * @return CompositeDisposable
     */
    @SuppressLint("RestrictedApi")
    public static CompositeDisposable from(Lifecycle lifecycle) {
        synchronized (object) {
            DisposableHolder disposableHolder = (DisposableHolder) lifecycleDisposableMap.get(lifecycle);
            if (disposableHolder != null) {
                return disposableHolder.disposable;
            }

            disposableHolder = new DisposableHolder(lifecycle, new CompositeDisposable());
            lifecycleDisposableMap.put(lifecycle, disposableHolder);
            disposableHolder.register();
            return disposableHolder.disposable;
        }
    }

    private static class DisposableHolder implements LifecycleEventObserver {

        private final Lifecycle lifecycle;
        private final CompositeDisposable disposable;

        public DisposableHolder(Lifecycle lifecycle, CompositeDisposable disposable) {
            this.lifecycle = lifecycle;
            this.disposable = disposable;

            if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                disposable.dispose();
            }
        }

        void register() {
            if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.INITIALIZED)) {
                lifecycle.addObserver(this);
            } else {
                disposable.dispose();
            }
        }

        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            if (lifecycle.getCurrentState().compareTo(Lifecycle.State.DESTROYED) == 0) {
                lifecycle.removeObserver(this);
                disposable.dispose();
            }
        }
    }
}
