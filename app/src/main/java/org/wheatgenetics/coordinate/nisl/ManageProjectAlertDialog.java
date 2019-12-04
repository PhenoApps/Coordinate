package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 *
 * androidx.annotation.NonNull
 * androidx.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 */
class ManageProjectAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"}) interface Handler
    {
        public abstract long getProjectId();

        public abstract void loadProject  ();
        public abstract void clearProject ();
        public abstract void deleteProject();
    }

    // region Fields
    @androidx.annotation.NonNull private final
        org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog.Handler handler;

    private org.wheatgenetics.coordinate.database.ProjectsTable
        projectsTableInstance = null;                                                   // lazy load
    private android.content.DialogInterface.OnClickListener
        onClickListenerInstance = null;                                                 // lazy load

    private boolean includedLoadProjectItem, includedClearProjectItem;
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }

    private void loadProject  () { this.handler.loadProject  (); }
    private void clearProject () { this.handler.clearProject (); }
    private void deleteProject() { this.handler.deleteProject(); }

    private void handleCase0()
    {
        if (this.includedLoadProjectItem)
            this.loadProject();
        else
            if (this.includedClearProjectItem) this.clearProject(); else this.deleteProject();
    }

    private void handleCase1()
    {
        if (this.includedLoadProjectItem)
            if (this.includedClearProjectItem) this.clearProject(); else this.deleteProject();
        else
            this.deleteProject();
    }

    @androidx.annotation.NonNull
    private android.content.DialogInterface.OnClickListener onClickListener()
    {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
            new android.content.DialogInterface.OnClickListener()
            {
                @java.lang.Override
                public void onClick(final android.content.DialogInterface dialog, final int which)
                {
                    switch (which)
                    {
                        case 0: org.wheatgenetics.coordinate.nisl
                            .ManageProjectAlertDialog.this.handleCase0(); break;

                        case 1: org.wheatgenetics.coordinate.nisl
                            .ManageProjectAlertDialog.this.handleCase1(); break;

                        case 2: org.wheatgenetics.coordinate.nisl
                            .ManageProjectAlertDialog.this.deleteProject(); break;
                    }
                }
            };
        return this.onClickListenerInstance;
    }
    // endregion

    ManageProjectAlertDialog(final android.app.Activity activity, @androidx.annotation.NonNull final
        org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    { this.setTitle(org.wheatgenetics.coordinate.R.string.ManageProjectAlertDialogTitle); }

    void show(final boolean projectModelIsLoaded)
    {
        {
            @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @androidx.annotation.StringRes
            final int items[];
            {
                @java.lang.SuppressWarnings({"Convert2Diamond"})
                final java.util.ArrayList<java.lang.Integer> arrayList =
                    new java.util.ArrayList<java.lang.Integer>();
                {
                    final boolean projectsExists;
                    {
                        final org.wheatgenetics.coordinate.database.ProjectsTable projectsTable =
                            this.projectsTable();
                        projectsExists = projectsTable.exists();

                        if (projectsExists)
                            // noinspection SimplifiableConditionalExpression
                            this.includedLoadProjectItem = projectModelIsLoaded ?
                                projectsTable.existsExceptFor(this.handler.getProjectId()) : true;
                        else this.includedLoadProjectItem = false;
                    }

                    if (this.includedLoadProjectItem) arrayList.add(
                        org.wheatgenetics.coordinate.R.string.ManageProjectAlertDialogLoad);


                    this.includedClearProjectItem = projectModelIsLoaded;
                    if (this.includedClearProjectItem) arrayList.add(
                        org.wheatgenetics.coordinate.R.string.ManageProjectAlertDialogClear);


                    if (projectsExists) arrayList.add(
                        org.wheatgenetics.coordinate.R.string.ManageProjectAlertDialogDelete);
                }

                items = new int[arrayList.size()];
                int i = 0;
                for (final java.lang.Integer integer: arrayList) items[i++] = integer;
            }
            this.setItems(items, this.onClickListener());
        }
        this.createShow();
    }
}