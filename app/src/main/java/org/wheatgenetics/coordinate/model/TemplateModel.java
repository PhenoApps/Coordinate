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

    public TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering)
    {
        super(title, type, rows, cols);

        this.colNumbering = colNumbering;
        this.rowNumbering = rowNumbering;
    }

    public TemplateModel(final long id, final java.lang.String title, final int code,
    final int rows, final int cols, final java.lang.String excludeCells,
    final java.lang.String excludeRows, final java.lang.String excludeCols, final int colNumbering,
    final int rowNumbering, final java.lang.String optionalFields, final long timestamp)
    {
        super(id, title, code, rows, cols);

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

    public boolean getColNumbering()                           { return this.colNumbering        ; }
    public void    setColNumbering(final boolean colNumbering) { this.colNumbering = colNumbering; }

    public boolean getRowNumbering()                           { return this.rowNumbering        ; }
    public void    setRowNumbering(final boolean rowNumbering) { this.rowNumbering = rowNumbering; }
}