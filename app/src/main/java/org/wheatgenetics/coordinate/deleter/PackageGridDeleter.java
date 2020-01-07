package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 *
 * org.wheatgenetics.coordinate.deleter.BaseGridDeleter
 */
class PackageGridDeleter extends org.wheatgenetics.coordinate.deleter.BaseGridDeleter
{
    PackageGridDeleter(@androidx.annotation.NonNull final android.content.Context context)
    { super(context); }

    boolean delete(@androidx.annotation.IntRange(from = 1) final long gridId)
    { this.entriesTable().deleteByGridId(gridId); return this.gridsTable().delete(gridId); }
}