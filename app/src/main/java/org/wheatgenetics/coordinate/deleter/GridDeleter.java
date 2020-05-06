package org.wheatgenetics.coordinate.deleter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Utils;

public class GridDeleter extends BaseGridDeleter {
    @NonNull
    private final
    GridDeleter.Handler handler;

    public GridDeleter(@NonNull final Context context,
                       @NonNull final GridDeleter.Handler
                               handler) {
        super(context);
        this.handler = handler;
    }

    // region Public Methods
    public void deleteWithoutConfirm(@IntRange(from = 1) final long gridId) {
        {
            @StringRes final int text =
                    this.entriesTable().deleteByGridId(gridId) ?
                            R.string.GridDeleterEntriesOfGridDeletedToast :
                            R.string.GridDeleterEntriesOfGridNotDeletedToast;
            this.showShortToast(text);
        }

        final boolean success = this.gridsTable().delete(gridId);
        {
            @StringRes final int text = success ?
                    R.string.GridDeleterGridDeletedToast :
                    R.string.GridDeleterGridNotDeletedToast;
            this.showLongToast(text);
        }
        if (success) this.handler.respondToDeletedGrid();
    }

    public void deleteWithConfirm(@IntRange(from = 1) final long gridId) {
        Utils.confirm(
                /* context     => */ this.context(),
                /* message     => */ R.string.GridDeleterConfirmation,
                /* yesRunnable => */ new Runnable() {
                    @Override
                    public void run() {
                        GridDeleter.this.deleteWithoutConfirm(
                                gridId);
                    }
                });
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void respondToDeletedGrid();
    }
    // endregion
}