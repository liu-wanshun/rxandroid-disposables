package com.liuwanshun.androiddisposable;


import android.view.View;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @author liuwanshun
 */
class ViewDisposable {
    private ViewDisposable() {
    }

    static CompositeDisposable from(View view) {

        if (!view.isAttachedToWindow()) {
            throw new IllegalStateException("Can't access the View's Disposable "
                    + "before onAttachedToWindow() or after onDetachedFromWindow()");
        }

        ViewDisposableHolder viewDisposableHolder = (ViewDisposableHolder) view.getTag(R.id.view_disposable);
        if (viewDisposableHolder == null) {
            CompositeDisposable disposable = new CompositeDisposable();
            viewDisposableHolder = new ViewDisposableHolder(disposable);
            view.addOnAttachStateChangeListener(viewDisposableHolder);
            view.setTag(R.id.view_disposable, viewDisposableHolder);
        }
        return viewDisposableHolder.disposable;

    }

    private static class ViewDisposableHolder implements View.OnAttachStateChangeListener {

        private final CompositeDisposable disposable;

        public ViewDisposableHolder(CompositeDisposable disposable) {
            this.disposable = disposable;
        }

        @Override
        public void onViewAttachedToWindow(View v) {
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            disposable.dispose();
            v.removeOnAttachStateChangeListener(this);
            v.setTag(R.id.view_disposable, null);
        }
    }
}
