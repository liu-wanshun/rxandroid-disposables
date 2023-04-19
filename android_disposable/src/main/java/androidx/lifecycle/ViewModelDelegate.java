package androidx.lifecycle;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;

import androidx.annotation.RestrictTo;

@RestrictTo(LIBRARY_GROUP_PREFIX)
public class ViewModelDelegate {

    public static <T> T setTagIfAbsent(ViewModel viewModel, String key, T newValue) {
        return viewModel.setTagIfAbsent(key, newValue);
    }

    public static <T> T getTag(ViewModel viewModel, String key) {
        return viewModel.getTag(key);
    }
}
