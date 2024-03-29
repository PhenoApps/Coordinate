package org.wheatgenetics.coordinate;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

public interface StringGetter {
    @Nullable
    String get(@StringRes int resId);

    @NonNull
    String getQuantity(
            @PluralsRes int resId,
            @IntRange(from = 0) int quantity,
            @Nullable Object... formatArgs)
            throws Resources.NotFoundException;
}