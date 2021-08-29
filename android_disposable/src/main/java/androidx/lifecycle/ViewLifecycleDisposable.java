package androidx.lifecycle;


import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * @author liuwanshun
 */
public class ViewLifecycleDisposable {
    private ViewLifecycleDisposable() {
    }

    public static CompositeDisposable from(Fragment fragment) {
        return LifecycleDisposable.from(fragment.getViewLifecycleOwner());
    }

}
