package androidx.lifecycle;


import android.view.View;

import com.liuwanshun.android_disposable.R;

import io.reactivex.disposables.CompositeDisposable;

/**
 * liuwanshun
 */
public class ViewDisposable {

    public static CompositeDisposable from(View view) {

        if (!view.isAttachedToWindow()) {
            throw new IllegalStateException("Can't access the View's Disposable "
                    + "before onAttachedToWindow() or after onDetachedFromWindow()");
        }

        ViewDisposableHolder viewDisposableHolder = (ViewDisposableHolder) view.getTag(

                R.id.view_disposable);
        if (viewDisposableHolder != null) {
            return viewDisposableHolder.disposable;
        }

        CompositeDisposable disposable = new CompositeDisposable();
        viewDisposableHolder = new ViewDisposableHolder(disposable);
        view.addOnAttachStateChangeListener(viewDisposableHolder);
        view.setTag(R.id.view_disposable, viewDisposableHolder);

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
