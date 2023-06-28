package com.liuwanshun.rxandroid.disposables;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ViewModelProvider(this).get(TestViewModel.class);

        CompositeDisposable composite = LifecycleDisposable.from(this);

        Single.just(1)
                .subscribe(s -> {
                    Log.e("sss", "成功结果: " + s);
                }, throwable -> {
                    Log.e("sss", "失败: ", throwable);
                }, composite);
    }
}
