package org.wheatgenetics.coordinate.nisl;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import org.wheatgenetics.androidlibrary.AlertDialog;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.database.ProjectsTable;

import java.util.ArrayList;

class ManageProjectAlertDialog extends AlertDialog {
    // region Fields
    @NonNull
    private final
    ManageProjectAlertDialog.Handler handler;
    private ProjectsTable
            projectsTableInstance = null;                                                   // lazy load
    private DialogInterface.OnClickListener
            onClickListenerInstance = null;                                                 // lazy load
    private boolean includedLoadProjectItem, includedClearProjectItem;

    ManageProjectAlertDialog(final Activity activity, @NonNull final
    ManageProjectAlertDialog.Handler handler) {
        super(activity);
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    @NonNull
    private ProjectsTable projectsTable() {
        if (null == this.projectsTableInstance) this.projectsTableInstance =
                new ProjectsTable(this.activity());
        return this.projectsTableInstance;
    }

    private void loadProject() {
        this.handler.loadProject();
    }

    private void clearProject() {
        this.handler.clearProject();
    }

    private void deleteProject() {
        this.handler.deleteProject();
    }

    private void handleCase0() {
        if (this.includedLoadProjectItem)
            this.loadProject();
        else if (this.includedClearProjectItem) this.clearProject();
        else this.deleteProject();
    }

    private void handleCase1() {
        if (this.includedLoadProjectItem)
            if (this.includedClearProjectItem) this.clearProject();
            else this.deleteProject();
        else
            this.deleteProject();
    }

    @NonNull
    private DialogInterface.OnClickListener onClickListener() {
        if (null == this.onClickListenerInstance) this.onClickListenerInstance =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        switch (which) {
                            case 0:
                                ManageProjectAlertDialog.this.handleCase0();
                                break;

                            case 1:
                                ManageProjectAlertDialog.this.handleCase1();
                                break;

                            case 2:
                                ManageProjectAlertDialog.this.deleteProject();
                                break;
                        }
                    }
                };
        return this.onClickListenerInstance;
    }
    // endregion

    @Override
    public void configure() {
        this.setTitle(R.string.ManageProjectAlertDialogTitle);
    }

    void show(final boolean projectModelIsLoaded) {
        {
            @SuppressWarnings({"CStyleArrayDeclaration"}) @StringRes final int items[];
            {
                @SuppressWarnings({"Convert2Diamond"}) final ArrayList<Integer> arrayList =
                        new ArrayList<Integer>();
                {
                    final boolean projectsExists;
                    {
                        final ProjectsTable projectsTable =
                                this.projectsTable();
                        projectsExists = projectsTable.exists();

                        if (projectsExists)
                            // noinspection SimplifiableConditionalExpression
                            this.includedLoadProjectItem = projectModelIsLoaded ?
                                    projectsTable.existsExceptFor(this.handler.getProjectId()) : true;
                        else this.includedLoadProjectItem = false;
                    }

                    if (this.includedLoadProjectItem) arrayList.add(
                            R.string.ManageProjectAlertDialogLoad);


                    this.includedClearProjectItem = projectModelIsLoaded;
                    if (this.includedClearProjectItem) arrayList.add(
                            R.string.ManageProjectAlertDialogClear);


                    if (projectsExists) arrayList.add(
                            R.string.ManageProjectAlertDialogDelete);
                }

                items = new int[arrayList.size()];
                int i = 0;
                for (final Integer integer : arrayList) items[i++] = integer;
            }
            this.setItems(items, this.onClickListener());
        }
        this.createShow();
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    interface Handler {
        public abstract long getProjectId();

        public abstract void loadProject();

        public abstract void clearProject();

        public abstract void deleteProject();
    }
}