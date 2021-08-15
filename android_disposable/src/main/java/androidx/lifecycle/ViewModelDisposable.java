package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;

/**
 * liuwanshun
 */
public class ViewModelDisposable {

    private static final String JOB_KEY = "ViewModelCompositeDisposable.JOB_KEY";

    public static CompositeDisposable from(ViewModel viewModel) {

        DisposableHolder disposableHolder = viewModel.getTag(JOB_KEY);
        if (disposableHolder != null) {
            return disposableHolder.getDisposable();
        }
        disposableHolder = new DisposableHolder(new CompositeDisposable());
        return viewModel
                .setTagIfAbsent(JOB_KEY, disposableHolder)
                .getDisposable();
    }

    private static class DisposableHolder implements Closeable {
        private final CompositeDisposable disposable;

        public DisposableHolder(CompositeDisposable disposable) {
            this.disposable = disposable;
        }

        public CompositeDisposable getDisposable() {
            return disposable;
        }

        @Override
        public void close() throws IOException {
            disposable.dispose();
        }
    }
}
