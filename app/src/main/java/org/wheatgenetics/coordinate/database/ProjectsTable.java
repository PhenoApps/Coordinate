package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.coordinate.model.Model
 * new org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.database.Table
 */
public class ProjectsTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Constants
    private static final java.lang.String TABLE_NAME       = "projects"                            ;
    private static final java.lang.String TITLE_FIELD_NAME = "title"   , STAMP_FIELD_NAME = "stamp";
    // endregion

    public ProjectsTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                       ,
            /* tableName => */ org.wheatgenetics.coordinate.database.ProjectsTable.TABLE_NAME,
            /* tag       => */ "ProjectsTable"                                               );
    }

    // region Overridden Methods
    @java.lang.Override
    org.wheatgenetics.coordinate.model.Model make(final android.database.Cursor cursor)
    {
        return null == cursor ? null : new org.wheatgenetics.coordinate.model.ProjectModel(
            /* id => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME)),
            /* title => */ cursor.getString(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.ProjectsTable.TITLE_FIELD_NAME)),
            /* timestamp => */ cursor.getLong(cursor.getColumnIndex(
                org.wheatgenetics.coordinate.database.ProjectsTable.STAMP_FIELD_NAME)));
    }

    @java.lang.Override
    android.content.ContentValues getContentValuesForInsert(
    final org.wheatgenetics.coordinate.model.Model model)
    {
        final android.content.ContentValues result = super.getContentValuesForInsert(model);
        {
            final org.wheatgenetics.coordinate.model.ProjectModel projectModel =
                (org.wheatgenetics.coordinate.model.ProjectModel) model;

            assert null != projectModel;
            result.put(org.wheatgenetics.coordinate.database.ProjectsTable.TITLE_FIELD_NAME,
                projectModel.getTitle());
            result.put(org.wheatgenetics.coordinate.database.ProjectsTable.STAMP_FIELD_NAME,
                projectModel.getTimestamp());
        }
        return result;
    }
    // endregion

    public boolean exists()
    {
        return org.wheatgenetics.coordinate.database.Table.exists(this.rawQuery(
            "SELECT ALL * FROM " + org.wheatgenetics.coordinate.database.ProjectsTable.TABLE_NAME));
    }
}