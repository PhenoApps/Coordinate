package org.wheatgenetics.coordinate.deleter;

import android.content.Context;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.model.BaseJoinedGridModels;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.model.Model;

abstract class CascadingDeleter extends Deleter
        implements BaseJoinedGridModels.Processor {
    // region Fields
    @StringRes
    private final int
            confirmationTitle, confirmationMessage, successToast, failToast;

    @IntRange(from = 1)
    private long id;

    private boolean atLeastOneGridWasDeleted;
    private PackageGridDeleter
            packageGridDeleterInstance = null;                                              // lazy load
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    CascadingDeleter(@NonNull final Context context,
                     @StringRes final int confirmationTitle,
                     @StringRes final int confirmationMessage,
                     @StringRes final int successToast,
                     @StringRes final int failToast) {
        super(context);

        this.confirmationTitle = confirmationTitle;
        this.confirmationMessage = confirmationMessage;
        this.successToast = successToast;
        this.failToast = failToast;
    }

    @NonNull
    private PackageGridDeleter packageGridDeleter() {
        if (null == this.packageGridDeleterInstance) this.packageGridDeleterInstance =
                new PackageGridDeleter(this.context());
        return this.packageGridDeleterInstance;
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @IntRange(from = 1)
    long id() {
        return this.id;
    }

    // region deleteStep3() Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    abstract boolean deleteMasterRecord();
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    boolean deleteStep3() {
        final boolean success = this.deleteMasterRecord();
        this.showLongToast(success ? this.successToast : this.failToast);
        return success;
    }

    // region deleteStep2() Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @Nullable
    abstract BaseJoinedGridModels loadDetailRecords();
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    boolean deleteStep2() {
        {
            final BaseJoinedGridModels baseJoinedGridModels =
                    this.loadDetailRecords();
            if (null != baseJoinedGridModels) {
                this.atLeastOneGridWasDeleted = false;
                baseJoinedGridModels.processAll(this);
                {
                    @StringRes final int text = this.atLeastOneGridWasDeleted ?
                            R.string.DeleterGridsSuccessToast :
                            R.string.DeleterGridsFailToast;
                    this.showShortToast(text);
                }
            }
        }
        this.deleteStep3();
        return this.atLeastOneGridWasDeleted;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    abstract boolean detailRecordsExists();
    // endregion

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void delete(@Nullable final Model model) {
        if (null != model) this.delete(model.getId());
    }

    // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
    @Override
    public void process(final JoinedGridModel joinedGridModel) {
        if (null != joinedGridModel) {
            final boolean gridWasDeleted =
                    this.packageGridDeleter().delete(joinedGridModel.getId());
            if (!this.atLeastOneGridWasDeleted && gridWasDeleted)
                this.atLeastOneGridWasDeleted = true;
        }
    }
    // endregion

    public void delete(@IntRange(from = 1) final long id) {
        this.id = id;
        if (this.detailRecordsExists())
            Utils.confirm(
                    /* context     => */ this.context(),
                    /* title       => */ this.confirmationTitle,
                    /* message     => */ this.confirmationMessage,
                    /* yesRunnable => */ new Runnable() {
                        @Override
                        public void run() {
                            CascadingDeleter.this.deleteStep2();
                        }
                    });
        else this.deleteStep3();
    }
}