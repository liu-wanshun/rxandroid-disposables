package com.liuwanshun.rxandroid.disposables;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.Disposable;

public class TestViewModel extends ViewModel {

    void test(){
        Disposable disposable =  ViewModelDisposable.from(this);
    }
}
