package org.wheatgenetics.coordinate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.model.ProjectModels;
import org.wheatgenetics.coordinate.Utils;

public class ProjectsTable extends Table {
    // region Constants
    private static final String TABLE_NAME = "projects";
    private static final String TITLE_FIELD_NAME = "title", STAMP_FIELD_NAME = "stamp";
    // endregion

    public ProjectsTable(final Context context) {
        super(
                /* context   => */ context,
                /* tableName => */ ProjectsTable.TABLE_NAME,
                /* tag       => */"ProjectsTable");
    }

    // region Private Methods
    private Cursor query(final long id) {
        return this.queryAll(
                /* selection  => */ ProjectsTable.whereClause(),
                /* selectionArgs => */ Utils.stringArray(id));
    }

    private Cursor exceptForQuery(final long id) {
        return this.queryAll(
                /* selection => */
                ProjectsTable.ID_FIELD_NAME +
                        " <> ?",
                /* selectionArgs => */ Utils.stringArray(id));
    }
    // endregion

    @Nullable
    private ProjectModels
    makeProjectModels(@Nullable final Cursor cursor) {
        final ProjectModels result;
        if (null == cursor)
            result = null;
        else
            try {
                if (cursor.getCount() <= 0)
                    result = null;
                else {
                    result = new ProjectModels();
                    while (cursor.moveToNext()) result.add(
                            (ProjectModel) this.make(cursor));
                }
            } finally {
                cursor.close();
            }
        return result;
    }

    // region Overridden Methods
    @Override
    Model make(final Cursor cursor) {
        return null == cursor ? null : new ProjectModel(
                /* id => */ cursor.getLong(cursor.getColumnIndex(
                ProjectsTable.ID_FIELD_NAME)),
                /* title => */ cursor.getString(cursor.getColumnIndex(
                ProjectsTable.TITLE_FIELD_NAME)),
                /* timestamp => */ cursor.getLong(cursor.getColumnIndex(
                ProjectsTable.STAMP_FIELD_NAME)),
                /* stringGetter => */this);
    }

    @Override
    @NonNull
    ContentValues getContentValuesForInsert(
            @NonNull final Model model) {
        final ContentValues result = super.getContentValuesForInsert(model);
        {
            final ProjectModel projectModel =
                    (ProjectModel) model;

            result.put(ProjectsTable.TITLE_FIELD_NAME,
                    projectModel.getTitle());
            result.put(ProjectsTable.STAMP_FIELD_NAME,
                    projectModel.getTimestamp());
        }
        return result;
    }
    // endregion

    // region Public Methods
    public boolean exists(final long id) {
        return ProjectsTable.exists(this.query(id));
    }

    @Nullable
    public ProjectModel get(final long id) {
        return (ProjectModel) this.makeFromFirst(this.query(id));
    }

    public boolean exists() {
        return ProjectsTable.exists(this.rawQuery(
                "SELECT ALL * FROM " +
                        ProjectsTable.TABLE_NAME));
    }

    public boolean existsExceptFor(final long id) {
        if (Model.illegal(id))
            return this.exists();
        else
            return ProjectsTable.exists(
                    this.exceptForQuery(id));
    }

    @Nullable
    public ProjectModels load() {
        return this.makeProjectModels(this.queryAll(/* orderBy => */
                ProjectsTable.TITLE_FIELD_NAME +
                        " ASC, " +
                        ProjectsTable.ID_FIELD_NAME + " ASC"));
    }

    @Nullable
    public ProjectModels loadExceptFor(final long id) {
        if (Model.illegal(id))
            return this.load();
        else
            return this.makeProjectModels(this.exceptForQuery(id));
    }

    @Nullable
    public ProjectModels loadProjectsWithGrids() {
        return this.makeProjectModels(this.queryDistinct(/* selection => */
                ProjectsTable.ID_FIELD_NAME +
                        " IN (SELECT DISTINCT projectId FROM grids WH" +
                        "ERE projectId IS NOT NULL AND projectId > 0)"));
    }

    public boolean deleteAll() {
        return super.deleteAll();
    }
    // endregion
}