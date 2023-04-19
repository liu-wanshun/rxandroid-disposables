package com.liuwanshun.androiddisposable;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.WeakHashMap;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @author liuwanshun
 */
class LifecycleDisposable {
    private LifecycleDisposable() {
    }

    private static final WeakHashMap<Lifecycle, DisposableHolder> lifecycleDisposableMap = new WeakHashMap<>();
    private static final Object object = new Object();

    static CompositeDisposable from(LifecycleOwner lifecycleOwner) {
        return from(lifecycleOwner.getLifecycle());
    }

    @SuppressLint("RestrictedApi")
    static CompositeDisposable from(Lifecycle lifecycle) {
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
            if (lifecycle.getCurrentState().compareTo(Lifecycle.State.DESTROYED) <= 0) {
                lifecycle.removeObserver(this);
                disposable.dispose();
            }
        }
    }
}
