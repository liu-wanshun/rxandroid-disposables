package com.liuwanshun.androiddisposable;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @author lws
 */
public class AndroidDisposable {

    /**
     * 当lifecycleOwner的Lifecycle到达DESTROYED状态时执行dispose
     *
     * @param lifecycleOwner 关联这个lifecycleOwner的生命周期。
     *                       <p>
     *                       如果lifecycleOwner是{@link Fragment#getViewLifecycleOwner()},在{@link Fragment#onDestroyView() }之前dispose。
     *                       <p>
     *                       如果LifecycleOwner是{@link Fragment},在{@link Fragment#onDestroy() }之前dispose。
     *                       <p>
     *                       如果LifecycleOwner是{@link FragmentActivity},在{@link FragmentActivity#onDestroy() }之前dispose。
     * @return CompositeDisposable
     */
    public static CompositeDisposable from(@NonNull LifecycleOwner lifecycleOwner) {
        return LifecycleDisposable.from(lifecycleOwner);
    }

    /**
     * 当Lifecycle到达DESTROYED状态时执行dispose
     *
     * @param lifecycle 关联这个lifecycle的生命周期,也可以看看{@link #from(LifecycleOwner)}}
     * @return CompositeDisposable
     */
    public static CompositeDisposable from(@NonNull Lifecycle lifecycle) {
        return LifecycleDisposable.from(lifecycle);
    }

    /**
     * 当viewModel clear时执行dispose
     *
     * @param viewModel 关联这个viewModel的生命周期,在{@link ViewModel#onCleared() }之前dispose
     * @return CompositeDisposable
     */
    public static CompositeDisposable from(@NonNull ViewModel viewModel) {
        return ViewModelDisposable.from(viewModel);
    }


    /**
     * 当View detachedFromWindow 时执行dispose
     *
     * @param view 关联这个view的生命周期, 在{@link View#onDetachedFromWindow()}之后dispose
     * @return CompositeDisposable
     */
    public static CompositeDisposable from(@NonNull View view) {
        return ViewDisposable.from(view);
    }
}
