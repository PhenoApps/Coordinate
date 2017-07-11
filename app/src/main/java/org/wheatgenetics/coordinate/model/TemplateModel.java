package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.graphics.Point
 * android.support.annotation.NonNull
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONObject
 * org.json.JSONTokener
 *
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 * org.wheatgenetics.coordinate.utils.Utils
 */

public class TemplateModel extends org.wheatgenetics.coordinate.model.PartialTemplateModel
{
    // region Fields
    private java.util.List<android.graphics.Point> excludeCells            ;
    private java.util.List<java.lang.Integer     > excludeRows, excludeCols;

    private boolean colNumbering, rowNumbering;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields;

    private long timestamp;
    // endregion

    // region Private Methods
    private static java.util.List<android.graphics.Point> jsonToPointList(
    final java.lang.String json) throws org.json.JSONException
    {
        final java.util.List<android.graphics.Point> pointList =
            new java.util.ArrayList<android.graphics.Point>();
        {
            final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
            final org.json.JSONArray   jsonArray   =
                (org.json.JSONArray) jsonTokener.nextValue();       // throws org.json.JSONException

            assert null != jsonArray;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                final org.json.JSONObject jsonObject = (org.json.JSONObject) jsonArray.get(i);

                assert null != jsonObject;
                pointList.add(new android.graphics.Point(
                    jsonObject.getInt("col"),                       // throws org.json.JSONException
                    jsonObject.getInt("row")));                     // throws org.json.JSONException
            }
        }
        return pointList;
    }

    private static java.util.List<java.lang.Integer> jsonToIntegerList(final java.lang.String json)
    throws org.json.JSONException
    {
        final java.util.List<java.lang.Integer> integerList =
            new java.util.ArrayList<java.lang.Integer>();
        {
            final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
            final org.json.JSONArray   jsonArray   =
                (org.json.JSONArray) jsonTokener.nextValue();       // throws org.json.JSONException

            assert null != jsonArray;
            for (int i = 0; i < jsonArray.length(); i++)
                integerList.add(jsonArray.getInt(i));               // throws org.json.JSONException
        }
        return integerList;
    }

    private static java.lang.String pointListToJson(
    final java.util.List<android.graphics.Point> pointList) throws org.json.JSONException
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();

        assert null != pointList;
        for (final android.graphics.Point point: pointList) if (null != point)
        {
            final org.json.JSONObject jsonObject = new org.json.JSONObject();
            jsonObject.put("row", point.y);                         // throws org.json.JSONException
            jsonObject.put("col", point.x);                         // throws org.json.JSONException

            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    // region Cell Private Methods
    static private int randomCoordinate(final int bound)
    { return new java.util.Random(java.lang.System.currentTimeMillis()).nextInt(bound - 1) + 1; }

    private android.graphics.Point randomCell()
    {
        return new android.graphics.Point(
            org.wheatgenetics.coordinate.model.TemplateModel.randomCoordinate(this.getCols()),
            org.wheatgenetics.coordinate.model.TemplateModel.randomCoordinate(this.getRows()));
    }

    private boolean isAlreadyExcluded(
    @android.support.annotation.NonNull final android.graphics.Point candidateCell)
    {
        assert null != candidateCell    ;
        assert null != this.excludeCells;

        boolean result = false;

        for (final android.graphics.Point excludedCell: this.excludeCells)
        {
            assert null != excludedCell;
            if (excludedCell.equals(candidateCell.x, candidateCell.y))
            {
                result = true;
                break;
            }
        }

        return result;
    }
    // endregion
    // endregion

    // region Constructors
    public TemplateModel(             ) { super(  ); }  // TODO: Remove?
    public TemplateModel(final long id) { super(id); }  // TODO: Remove?

    public TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering)
    {
        super(title, type, rows, cols);

        this.colNumbering = colNumbering;
        this.rowNumbering = rowNumbering;

        this.excludeCells = new java.util.ArrayList<android.graphics.Point>();
    }

    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields)
    {
        this(title, type, rows, cols, colNumbering, rowNumbering);

        this.excludeRows = new java.util.ArrayList<java.lang.Integer>();
        this.excludeCols = new java.util.ArrayList<java.lang.Integer>();

        this.optionalFields = nonNullOptionalFields;
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
                org.wheatgenetics.coordinate.model.TemplateModel.jsonToPointList(excludeCells);
        }
        catch (final org.json.JSONException e) { this.excludeCells = null; }

        try
        {
            this.excludeRows =
                org.wheatgenetics.coordinate.model.TemplateModel.jsonToIntegerList(excludeRows);
        }
        catch (final org.json.JSONException e) { this.excludeRows = null; }
        try
        {
            this.excludeCols =
                org.wheatgenetics.coordinate.model.TemplateModel.jsonToIntegerList(excludeCols);
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
    // endregion

    // region toString()
    @java.lang.Override
    java.lang.String formatString()
    {
        return super.formatString() + ", excludeCells=%s, excludeRows=%s" +
            ", excludeCols=%s, options=%s, colNumbering=%b, rowNumbering=%b, stamp=%d]";
    }

    @java.lang.Override
    public java.lang.String toString()
    {
        return java.lang.String.format(this.formatString(),
            "TemplateModel"    , this.excludeCells, this.excludeRows , this.excludeCols,
            this.optionalFields, this.colNumbering, this.rowNumbering, this.timestamp  );
    }
    // endregion

    // region Public Methods
    // region excludeCells Public Methods
    public java.util.List<android.graphics.Point> getExcludeCells() { return this.excludeCells; }

    public java.lang.String getExcludeCellsAsJson() throws org.json.JSONException
    {
        return org.wheatgenetics.coordinate.model.TemplateModel.pointListToJson(
            this.getExcludeCells());
    }

    public void setExcludeCells(final java.util.List<android.graphics.Point> excludeCells)
    { this.excludeCells = new java.util.ArrayList<android.graphics.Point>(excludeCells); }
    // endregion

    // region excludeRows, excludeCols Public Methods
    public java.util.List<java.lang.Integer> getExcludeRows() { return this.excludeRows; }
    public java.util.List<java.lang.Integer> getExcludeCols() { return this.excludeCols; }

    public java.lang.String getExcludeRowsAsJson()
    { return org.wheatgenetics.coordinate.utils.Utils.integerListToJson(this.getExcludeRows()); }

    public java.lang.String getExcludeColsAsJson()
    { return org.wheatgenetics.coordinate.utils.Utils.integerListToJson(this.getExcludeCols()); }
    // endregion

    // region colNumbering, rowNumbering Public Methods
    public boolean getColNumbering()                           { return this.colNumbering        ; }
    public void    setColNumbering(final boolean colNumbering) { this.colNumbering = colNumbering; }

    public boolean getRowNumbering()                           { return this.rowNumbering        ; }
    public void    setRowNumbering(final boolean rowNumbering) { this.rowNumbering = rowNumbering; }
    // endregion

    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields getOptionalFields()
    { return this.optionalFields; }

    public long getTimestamp() { return this.timestamp; }

    // region Cell Public Methods
    public void makeOneRandomCell()
    {
        assert null != this.excludeCells;
        this.excludeCells.clear();
        this.excludeCells.add(this.randomCell());
    }

    public void makeRandomCells(int amount)
    {
        if (1 == amount)
            this.makeOneRandomCell();
        else
        {
            assert null != this.excludeCells;
            this.excludeCells.clear();
            if (amount > 1)
            {
                android.graphics.Point cell;
                do
                {
                    do
                        cell = this.randomCell();
                    while (this.isAlreadyExcluded(cell));
                    this.excludeCells.add(cell);
                }
                while (--amount > 0);
            }
        }
    }
    // endregion

    // region Default Public Methods
    static org.wheatgenetics.coordinate.model.TemplateModel makeSeedDefault()
    {
        final org.wheatgenetics.coordinate.model.TemplateModel result =
            new org.wheatgenetics.coordinate.model.TemplateModel(
                /* title          => */ "Seed Tray"                                         ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows           => */  6                                                  ,
                /* cols           => */ 20                                                  ,
                /* colNumbering   => */ true                                                ,
                /* rowNumbering   => */ true                                                ,
                /* optionalFields => */ org.wheatgenetics.coordinate.optionalField.
                    NonNullOptionalFields.makeSeedDefault());

        result.excludeRows.add(2);
        result.excludeRows.add(5);

        return result;
    }

    static org.wheatgenetics.coordinate.model.TemplateModel makeDNADefault()
    {
        return new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title          => */ "DNA Plate"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */  8                                                 ,
            /* cols           => */ 12                                                 ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField.
                NonNullOptionalFields.makeDNADefault());
    }
    // endregion
    // endregion
}