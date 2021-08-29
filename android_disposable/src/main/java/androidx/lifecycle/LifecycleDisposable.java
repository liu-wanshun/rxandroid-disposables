package androidx.lifecycle;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @author liuwanshun
 */
public class LifecycleDisposable {
    private LifecycleDisposable() {
    }

    public static CompositeDisposable from(LifecycleOwner lifecycleOwner) {
        return from(lifecycleOwner.getLifecycle());
    }

    @SuppressLint("RestrictedApi")
    public static CompositeDisposable from(Lifecycle lifecycle) {
        while (true) {
            DisposableHolder disposableHolder = (DisposableHolder) lifecycle.mInternalScopeRef.get();
            if (disposableHolder != null) {
                return disposableHolder.disposable;
            }

            disposableHolder = new DisposableHolder(lifecycle, new CompositeDisposable());

            if (lifecycle.mInternalScopeRef.compareAndSet(null, disposableHolder)) {
                disposableHolder.register();
                return disposableHolder.disposable;
            }
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
