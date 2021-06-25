package org.wheatgenetics.coordinate.deleter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

class PackageGridDeleter extends BaseGridDeleter {
    PackageGridDeleter(@NonNull final Context context) {
        super(context);
    }

    boolean delete(@IntRange(from = 1) final long gridId) {
        this.entriesTable().deleteByGridId(gridId);
        return this.gridsTable().delete(gridId);
    }
}