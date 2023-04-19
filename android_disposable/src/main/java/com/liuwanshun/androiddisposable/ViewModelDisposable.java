package com.liuwanshun.androiddisposable;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelDelegate;

import java.io.Closeable;
import java.io.IOException;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @author liuwanshun
 */
class ViewModelDisposable {
    private static final String JOB_KEY = "ViewModelCompositeDisposable.JOB_KEY";

    private ViewModelDisposable() {
    }

    static CompositeDisposable from(ViewModel viewModel) {

        DisposableHolder disposableHolder = ViewModelDelegate.getTag(viewModel, JOB_KEY);
        if (disposableHolder == null) {
            disposableHolder = ViewModelDelegate.setTagIfAbsent(viewModel, JOB_KEY, new DisposableHolder(new CompositeDisposable()));
        }
        return disposableHolder.getDisposable();
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
