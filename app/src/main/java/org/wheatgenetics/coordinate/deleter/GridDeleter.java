package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.database.EntriesTable
 * org.wheatgenetics.coordinate.database.GridsTable
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class GridDeleter extends java.lang.Object
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void respondToDeletedGrid(); }

    // region Fields
    @androidx.annotation.NonNull private final android.content.Context context;
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.deleter.GridDeleter.Handler handler;

    // region Table Fields
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTableInstance = null;    // ll
    private org.wheatgenetics.coordinate.database.GridsTable   gridsTableInstance   = null;    // ll
    // endregion
    // endregion

    // region Private Methods
    // region Table Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.EntriesTable entriesTable()
    {
        if (null == this.entriesTableInstance) this.entriesTableInstance =
            new org.wheatgenetics.coordinate.database.EntriesTable(this.context);
        return this.entriesTableInstance;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.GridsTable gridsTable()
    {
        if (null == this.gridsTableInstance) this.gridsTableInstance =
            new org.wheatgenetics.coordinate.database.GridsTable(this.context);
        return this.gridsTableInstance;
    }
    // endregion

    // region Toast Private Methods
    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.context, text); }

    private void showLongToast(@androidx.annotation.StringRes final int text)
    { this.showLongToast(this.context.getString(text)); }
    // endregion

    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.context, text); }

    private void showShortToast(@androidx.annotation.StringRes final int text)
    { this.showShortToast(this.context.getString(text)); }
    // endregion
    // endregion
    // endregion

    public GridDeleter(@androidx.annotation.NonNull final android.content.Context context,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.deleter.GridDeleter.Handler
        handler)
    { super(); this.context = context; this.handler = handler; }

    // region Public Methods
    public void deleteWithoutConfirm(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        {
            @androidx.annotation.StringRes final int text =
                this.entriesTable().deleteByGridId(gridId) ?
                    org.wheatgenetics.coordinate.R.string.GridDeleterEntriesOfGridDeletedToast   :
                    org.wheatgenetics.coordinate.R.string.GridDeleterEntriesOfGridNotDeletedToast;
            this.showShortToast(text);
        }

        final boolean success = this.gridsTable().delete(gridId);
        {
            @androidx.annotation.StringRes final int text = success ?
                org.wheatgenetics.coordinate.R.string.GridDeleterGridDeletedToast   :
                org.wheatgenetics.coordinate.R.string.GridDeleterGridNotDeletedToast;
            this.showLongToast(text);
        }
        if (success) this.handler.respondToDeletedGrid();
    }

    public void deleteWithConfirm(@androidx.annotation.IntRange(from = 1) final long gridId)
    {
        org.wheatgenetics.coordinate.Utils.confirm(
            /* context     => */ this.context                                                 ,
            /* message     => */ org.wheatgenetics.coordinate.R.string.GridDeleterConfirmation,
            /* yesRunnable => */ new java.lang.Runnable()
                {
                    @java.lang.Override public void run()
                    {
                        org.wheatgenetics.coordinate.deleter.GridDeleter.this.deleteWithoutConfirm(
                            gridId);
                    }
                });
    }
    // endregion
}