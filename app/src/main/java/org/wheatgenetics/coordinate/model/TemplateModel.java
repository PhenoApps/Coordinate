package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.graphics.Point
 *
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 * org.wheatgenetics.coordinate.utils.Utils
 */

public class TemplateModel extends org.wheatgenetics.coordinate.model.PartialTemplateModel
{
    private java.util.List<android.graphics.Point> excludeCells            ;
    private java.util.List<java.lang.Integer     > excludeRows, excludeCols;

    private boolean colNumbering, rowNumbering;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields;

    private long timestamp;


    public TemplateModel() { super(); }  // TODO: Remove?

    public TemplateModel(final long id) { super(id); }  // TODO: Remove?

    public TemplateModel(final long id, final java.lang.String title, final int type,
    final int rows, final int cols, final java.lang.String excludeCells,
    final java.lang.String excludeRows, final java.lang.String excludeCols, final int colNumbering,
    final int rowNumbering, final java.lang.String optionalFields, final long timestamp)
    {
        super(id, title, type, rows, cols);

        try
        {
            this.excludeCells =
                org.wheatgenetics.coordinate.utils.Utils.jsonToPointList(excludeCells);
        }
        catch (final org.json.JSONException e) { this.excludeCells = null; }

        try
        {
            this.excludeRows =
                org.wheatgenetics.coordinate.utils.Utils.jsonToIntegerList(excludeRows);
        }
        catch (final org.json.JSONException e) { this.excludeRows = null; }
        try
        {
            this.excludeCols =
                org.wheatgenetics.coordinate.utils.Utils.jsonToIntegerList(excludeCols);
        }
        catch (final org.json.JSONException e) { this.excludeCols = null; }

        this.colNumbering = colNumbering == 1;
        this.rowNumbering = rowNumbering == 1;

        try
        {
            this.optionalFields = new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);
        }
        catch (final org.json.JSONException e) { this.optionalFields = null; }

        this.timestamp = timestamp;
    }

//    public java.lang.String getTitle() { return this.title; }
}