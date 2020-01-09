package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.deleter.BaseGridDeleter
 */
public class GridDeleter extends org.wheatgenetics.coordinate.deleter.BaseGridDeleter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Handler
    { public abstract void respondToDeletedGrid(); }

    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.deleter.GridDeleter.Handler handler;

    public GridDeleter(@androidx.annotation.NonNull final android.content.Context context,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.deleter.GridDeleter.Handler
        handler) { super(context); this.handler = handler; }

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
            /* context     => */ this.context()                                               ,
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