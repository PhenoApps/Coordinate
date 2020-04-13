package org.wheatgenetics.coordinate.ti;

/**
 * Uses:
 * android.app.Activity
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.javalib.Dir.PermissionException
 * org.wheatgenetics.javalib.Dir.PermissionRequestedException
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.StringGetter
 * org.wheatgenetics.coordinate.TemplatesDir
 * org.wheatgenetics.coordinate.Utils
 *
 * org.wheatgenetics.coordinate.database.TemplatesTable
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class TemplateImporter
extends java.lang.Object implements org.wheatgenetics.coordinate.StringGetter
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) public interface Adapter
    { public abstract void notifyDataSetChanged(); }

    // region Fields
    @androidx.annotation.NonNull  private final android.app.Activity activity   ;
                                  private final int                  requestCode;
    @androidx.annotation.Nullable private final
        org.wheatgenetics.coordinate.ti.TemplateImporter.Adapter adapter;

    private java.lang.String                                     fileName                     ;
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTableInstance = null;// ll
    // endregion

    // region Private Methods
    private void showLongToast(final java.lang.String text)
    { org.wheatgenetics.androidlibrary.Utils.showLongToast(this.activity, text); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.TemplatesTable templatesTable()
    {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
            new org.wheatgenetics.coordinate.database.TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }

    private void notifyDataSetChanged()
    { if (null != this.adapter) this.adapter.notifyDataSetChanged(); }
    // endregion

    // region Constructors
    public TemplateImporter(
    @androidx.annotation.NonNull  final android.app.Activity                            activity   ,
                                  final int                                             requestCode,
    @androidx.annotation.Nullable final org.wheatgenetics.coordinate.ti.TemplateImporter.Adapter
        adapter)
    { super(); this.activity = activity; this.requestCode = requestCode; this.adapter = adapter; }

    public TemplateImporter(@androidx.annotation.NonNull final android.app.Activity activity   ,
                                                         final int                  requestCode)
    { this(activity, requestCode,null); }
    // endregion

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @java.lang.Override @androidx.annotation.Nullable public java.lang.String get(
    @androidx.annotation.StringRes final int resId) { return this.activity.getString(resId); }

    @java.lang.Override @androidx.annotation.NonNull public java.lang.String getQuantity(
    @androidx.annotation.PluralsRes         final int                 resId     ,
    @androidx.annotation.IntRange(from = 0) final int                 quantity  ,
    @androidx.annotation.Nullable           final java.lang.Object... formatArgs)
    throws android.content.res.Resources.NotFoundException
    { return this.activity.getResources().getQuantityString(resId, quantity, formatArgs); }
    // endregion

    // region Public Methods
    public void importTemplate()
    {
        java.io.File file;
        try
        {
            final org.wheatgenetics.coordinate.TemplatesDir templatesDir =
                org.wheatgenetics.coordinate.Utils.templatesDir(             // throws IOException,
                    this.activity, this.requestCode);                        //  PermissionException
            file = templatesDir.makeFile(this.fileName);  // throws IOException, PermissionException
        }
        catch (final java.io.IOException | org.wheatgenetics.javalib.Dir.PermissionException e)
        {
            if (!(e instanceof org.wheatgenetics.javalib.Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
            file = null;
        }

        if (null != file)
        {
            final boolean templateImported = this.templatesTable().insert(
                org.wheatgenetics.coordinate.model.TemplateModel.makeUserDefined(
                    file,this)) > 0;
            if (templateImported) this.notifyDataSetChanged();
        }
    }

    public void importTemplate(final java.lang.String fileName)
    { this.fileName = fileName; this.importTemplate(); }
    // endregion
}