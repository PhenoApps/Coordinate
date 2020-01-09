package org.wheatgenetics.coordinate.deleter;

/**
 * Uses:
 * android.content.Context
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels
 * org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.Model
 *
 * org.wheatgenetics.coordinate.deleter.Deleter
 * org.wheatgenetics.coordinate.deleter.PackageGridDeleter
 */
abstract class CascadingDeleter extends org.wheatgenetics.coordinate.deleter.Deleter
implements org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor
{
    // region Fields
    @androidx.annotation.StringRes private final int
        confirmationTitle, confirmationMessage, successToast, failToast;

    @androidx.annotation.IntRange(from = 1) private long id;

    private boolean                                                 atLeastOneGridWasDeleted;
    private org.wheatgenetics.coordinate.deleter.PackageGridDeleter
        packageGridDeleterInstance = null;                                              // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.deleter.PackageGridDeleter packageGridDeleter()
    {
        if (null == this.packageGridDeleterInstance) this.packageGridDeleterInstance =
            new org.wheatgenetics.coordinate.deleter.PackageGridDeleter(this.context());
        return this.packageGridDeleterInstance;
    }

    // region Toast Private Methods
    // region Short Toast Private Methods
    private void showShortToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showShortToast(this.context(), text); }

    private void showShortToast(@androidx.annotation.StringRes final int text)
    { this.showShortToast(this.context().getString(text)); }
    // endregion

    // region Long Toast Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.context(), text); }

    private void showLongToast(@androidx.annotation.StringRes final int text)
    { this.showLongToast(this.context().getString(text)); }
    // endregion
    // endregion
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.IntRange(from = 1) long id() { return this.id; }

    // region deleteStep3() Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    abstract boolean deleteMasterRecord();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    boolean deleteStep3()
    {
        final boolean success = this.deleteMasterRecord();
        this.showLongToast(success ? this.successToast : this.failToast);
        return success;
    }
    // endregion

    // region deleteStep2() Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.Nullable
    abstract org.wheatgenetics.coordinate.model.BaseJoinedGridModels loadDetailRecords();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    boolean deleteStep2()
    {
        {
            final org.wheatgenetics.coordinate.model.BaseJoinedGridModels baseJoinedGridModels =
                this.loadDetailRecords();
            if (null != baseJoinedGridModels)
            {
                this.atLeastOneGridWasDeleted = false;
                baseJoinedGridModels.processAll(this);
                {
                    @androidx.annotation.StringRes final int text = this.atLeastOneGridWasDeleted ?
                        org.wheatgenetics.coordinate.R.string.DeleterGridsSuccessToast :
                        org.wheatgenetics.coordinate.R.string.DeleterGridsFailToast    ;
                    this.showShortToast(text);
                }
            }
        }
        this.deleteStep3();
        return this.atLeastOneGridWasDeleted;
    }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    abstract boolean detailRecordsExists();

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    void delete(@androidx.annotation.Nullable final org.wheatgenetics.coordinate.model.Model model)
    { if (null != model) this.delete(model.getId()); }
    // endregion

    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    CascadingDeleter(@androidx.annotation.NonNull final android.content.Context context            ,
                   @androidx.annotation.StringRes final int                     confirmationTitle  ,
                   @androidx.annotation.StringRes final int                     confirmationMessage,
                   @androidx.annotation.StringRes final int                     successToast       ,
                   @androidx.annotation.StringRes final int                     failToast          )
    {
        super(context);

        this.confirmationTitle = confirmationTitle; this.confirmationMessage = confirmationMessage;
        this.successToast      = successToast     ; this.failToast           = failToast          ;
    }

    // region org.wheatgenetics.coordinate.model.BaseJoinedGridModels.Processor Overridden Method
    @java.lang.Override
    public void process(final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel)
    {
        if (null != joinedGridModel)
        {
            final boolean gridWasDeleted =
                this.packageGridDeleter().delete(joinedGridModel.getId());
            if (!this.atLeastOneGridWasDeleted && gridWasDeleted)
                this.atLeastOneGridWasDeleted = true;
        }
    }
    // endregion

    public void delete(@androidx.annotation.IntRange(from = 1) final long id)
    {
        this.id = id;
        if (this.detailRecordsExists())
            org.wheatgenetics.coordinate.Utils.confirm(
                /* context     => */ this.context()          ,
                /* title       => */ this.confirmationTitle  ,
                /* message     => */ this.confirmationMessage,
                /* yesRunnable => */ new java.lang.Runnable()
                    {
                        @java.lang.Override public void run()
                        {
                            org.wheatgenetics.coordinate.deleter
                                .CascadingDeleter.this.deleteStep2();
                        }
                    });
        else this.deleteStep3();
    }
}