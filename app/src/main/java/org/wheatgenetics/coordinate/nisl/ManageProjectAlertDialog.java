package org.wheatgenetics.coordinate.nisl;

/**
 * Uses:
 * android.app.Activity
 * android.content.DialogInterface
 * android.content.DialogInterface.OnClickListener
 * android.support.annotation.StringRes
 *
 * org.wheatgenetics.androidlibrary.AlertDialog
 *
 * org.wheatgenetics.coordinate.R
 *
 * org.wheatgenetics.coordinate.database.ProjectsTable
 */
class ManageProjectAlertDialog extends org.wheatgenetics.androidlibrary.AlertDialog
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler
    {
        public abstract long getProjectId();

        public abstract void loadProject  ();
        public abstract void clearProject ();
        public abstract void deleteProject();
    }

    // region Fields
    private final org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog.Handler handler;

    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTableInstance   = null;
    private android.content.DialogInterface.OnClickListener     onClickListenerInstance = null;

    private boolean includedLoadProjectItem, includedClearProjectItem;
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.database.ProjectsTable projectsTable()
    {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
            new org.wheatgenetics.coordinate.database.ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }

    private void loadProject  () { assert null != this.handler; this.handler.loadProject  (); }
    private void clearProject () { assert null != this.handler; this.handler.clearProject (); }
    private void deleteProject() { assert null != this.handler; this.handler.deleteProject(); }

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

    ManageProjectAlertDialog(final android.app.Activity activity,
    final org.wheatgenetics.coordinate.nisl.ManageProjectAlertDialog.Handler handler)
    { super(activity); this.handler = handler; }

    @java.lang.Override public void configure()
    { this.setTitle(org.wheatgenetics.coordinate.R.string.ManageProjectAlertDialogTitle); }

    void show(final boolean projectModelIsLoaded)
    {
        {
            @android.support.annotation.StringRes final int items[];
            {
                final java.util.ArrayList<java.lang.Integer> arrayList =
                    new java.util.ArrayList<java.lang.Integer>();
                {
                    final boolean projectsExists = this.projectsTable().exists();

                    if (projectsExists)
                        if (projectModelIsLoaded)
                        {
                            assert null != this.handler;
                            this.includedLoadProjectItem =
                                this.projectsTable().existsExceptFor(this.handler.getProjectId());
                        }
                        else this.includedLoadProjectItem = true;
                    else this.includedLoadProjectItem = false;

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