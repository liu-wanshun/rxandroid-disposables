package com.liuwanshun.rxandroid.disposables;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.disposables.Disposable;

public class TestFragment extends Fragment {

    TestFragment() {
        super(R.layout.activity_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Disposable disposable =  LifecycleDisposable.from(getViewLifecycleOwner());
    }
}
