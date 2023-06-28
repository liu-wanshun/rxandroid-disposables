package com.liuwanshun.rxandroid.disposables;


import android.view.View;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @author liuwanshun
 */
public class ViewAttachDisposable {
    private ViewAttachDisposable() {
    }

    /**
     * 当View detachedFromWindow 时执行dispose
     *
     * @param view 关联这个view的生命周期, 在{@link View#onDetachedFromWindow()}之后dispose
     * @return CompositeDisposable
     */
    public static CompositeDisposable from(View view) {

        if (!view.isAttachedToWindow()) {
            throw new IllegalStateException("Can't access the View's Disposable "
                    + "before onAttachedToWindow() or after onDetachedFromWindow()");
        }

        ViewDisposableHolder viewDisposableHolder = (ViewDisposableHolder) view.getTag(R.id.view_attach_window_disposable);
        if (viewDisposableHolder == null) {
            CompositeDisposable disposable = new CompositeDisposable();
            viewDisposableHolder = new ViewDisposableHolder(disposable);
            view.addOnAttachStateChangeListener(viewDisposableHolder);
            view.setTag(R.id.view_attach_window_disposable, viewDisposableHolder);
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
            v.setTag(R.id.view_attach_window_disposable, null);
        }
    }
}
