package org.wheatgenetics.coordinate.database;

/**
 * Uses:
 * android.content.ContentValues
 * android.content.Context
 * android.database.Cursor
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.Model
 * org.wheatgenetics.coordinate.model.ProjectModel
 * org.wheatgenetics.coordinate.model.ProjectModels
 *
 * org.wheatgenetics.coordinate.database.Table
 */
public class ProjectsTable extends org.wheatgenetics.coordinate.database.Table
{
    // region Constants
    private static final java.lang.String TABLE_NAME       = "projects"                            ;
    private static final java.lang.String TITLE_FIELD_NAME = "title"   , STAMP_FIELD_NAME = "stamp";
    // endregion

    // region Private Methods
    private android.database.Cursor query(final long id)
    {
        return this.queryAll(
            /* selection     => */ org.wheatgenetics.coordinate.database.Table.whereClause(),
            /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(id)          );
    }

    private android.database.Cursor exceptForQuery(final long id)
    {
        return this.queryAll(
            /* selection => */org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME +
                " <> ?",
            /* selectionArgs => */ org.wheatgenetics.javalib.Utils.stringArray(id));
    }

    private org.wheatgenetics.coordinate.model.ProjectModels makeProjectModels(
    final android.database.Cursor cursor)
    {
        final org.wheatgenetics.coordinate.model.ProjectModels result;
        if (null == cursor)
            result = null;
        else
            try
            {
                if (cursor.getCount() <= 0)
                    result = null;
                else
                {
                    result = new org.wheatgenetics.coordinate.model.ProjectModels();
                    while (cursor.moveToNext()) result.add(
                        (org.wheatgenetics.coordinate.model.ProjectModel) this.make(cursor));
                }
            }
            finally { cursor.close(); }
        return result;
    }
    // endregion

    public ProjectsTable(final android.content.Context context)
    {
        super(
            /* context   => */ context                                                       ,
            /* tableName => */ org.wheatgenetics.coordinate.database.ProjectsTable.TABLE_NAME,
            /* tag       => */"ProjectsTable");
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

    @java.lang.Override android.content.ContentValues getContentValuesForInsert(
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

    // region Public Methods
    public boolean exists(final long id)
    { return org.wheatgenetics.coordinate.database.Table.exists(this.query(id)); }

    public org.wheatgenetics.coordinate.model.ProjectModel get(final long id)
    { return (org.wheatgenetics.coordinate.model.ProjectModel) this.makeFromFirst(this.query(id)); }

    public boolean exists()
    {
        return org.wheatgenetics.coordinate.database.Table.exists(this.rawQuery(
            "SELECT ALL * FROM " +
                org.wheatgenetics.coordinate.database.ProjectsTable.TABLE_NAME));
    }

    public boolean existsExceptFor(final long id)
    {
        if (org.wheatgenetics.coordinate.model.Model.illegal(id))
            return this.exists();
        else
            return org.wheatgenetics.coordinate.database.Table.exists(this.exceptForQuery(id));
    }

    public org.wheatgenetics.coordinate.model.ProjectModels load()
    {
        return this.makeProjectModels(this.queryAll(/* orderBy => */
            org.wheatgenetics.coordinate.database.ProjectsTable.TITLE_FIELD_NAME +
                " ASC, " +
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME + " ASC"));
    }

    public org.wheatgenetics.coordinate.model.ProjectModels loadExceptFor(final long id)
    {
        if (org.wheatgenetics.coordinate.model.Model.illegal(id))
            return this.load();
        else
            return this.makeProjectModels(this.exceptForQuery(id));
    }

    public org.wheatgenetics.coordinate.model.ProjectModels loadProjectsWithGrids()
    {
        return this.makeProjectModels(this.queryDistinct(/* selection => */
            org.wheatgenetics.coordinate.database.Table.ID_FIELD_NAME +
                " IN (SELECT DISTINCT projectId FROM grids WH" +
                "ERE projectId IS NOT NULL AND projectId > 0)"));
    }
    // endregion
}