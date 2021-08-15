package androidx.lifecycle;


import androidx.fragment.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;

/**
 * liuwanshun
 */
public class ViewLifecycleDisposable {

    public static CompositeDisposable from(Fragment fragment) {
        return LifecycleDisposable.from(fragment.getViewLifecycleOwner());
    }

}
