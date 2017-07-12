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
    private static class Cells extends java.lang.Object
    {
        private static class Cell extends java.lang.Object
        {
            private final android.graphics.Point point;

            // region Internal Method
            private static int random(final int bound)
            {
                return new java.util.Random(
                    java.lang.System.currentTimeMillis()).nextInt(bound - 1) + 1;
            }
            // endregion

            // region Constructor
            private Cell(final int x, final int y)
            {
                super();
                this.point = new android.graphics.Point(x, y);
            }
            // endregion

            // region External Methods
            private static org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell random(
            final int xBound, final int yBound)
            {
                return new org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell(
                    org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell.random(xBound),
                    org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell.random(yBound));
            }

            private boolean sameContents(
            final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell cell)
            {
                if (null == cell)
                    return false;
                else
                    if (null == cell.point || null == this.point)
                        return false;
                    else
                        return this.point.equals(cell.point.x, cell.point.y);
            }

            private org.json.JSONObject json() throws org.json.JSONException
            {
                if (null == this.point)
                    return null;
                else
                {
                    final org.json.JSONObject jsonObject = new org.json.JSONObject();

                    jsonObject.put("row", this.point.y);            // throws org.json.JSONException
                    jsonObject.put("col", this.point.x);            // throws org.json.JSONException

                    return jsonObject;
                }
            }
            // endregion
        }

        private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell>
            cellArrayListInstance = null;

        // region Internal Methods
        private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell>
        cellArrayList()
        {
            if (null == this.cellArrayListInstance)
                this.cellArrayListInstance = new java.util.ArrayList<
                    org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell>();
            return this.cellArrayListInstance;
        }

        private boolean isPresent(@android.support.annotation.NonNull
        final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell candidateCell)
        {
            boolean result = false;

            if (null != this.cellArrayListInstance && null != candidateCell)
                for (final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell cell:
                this.cellArrayListInstance)
                    if (null != cell) if (cell.sameContents(candidateCell))
                    {
                        result = true;
                        break;
                    }

            return result;
        }
        // endregion

        @java.lang.Override
        public String toString()
        {
            return null == this.cellArrayListInstance ?
                super.toString() : this.cellArrayList().toString();
        }

        // region Constructors
        private Cells() { super(); }

        private Cells(final java.lang.String json)
        {
            super();

            if (null != json) if (json.length() > 0)
            {
                org.json.JSONArray jsonArray;
                {
                    final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
                    try
                    {
                        jsonArray = (org.json.JSONArray)
                            jsonTokener.nextValue();                // throws org.json.JSONException
                    }
                    catch (final org.json.JSONException e)
                    { return; /* Leave cellArrayListInstance == null. */ }
                }

                assert null != jsonArray;
                final int length = jsonArray.length();
                if (length > 0)
                {
                    final int                 first = 0, last = length - 1;
                          org.json.JSONObject jsonObject                  ;
                    for (int i = first; i <= last; i++)
                        try
                        {
                            jsonObject = (org.json.JSONObject) jsonArray.get(i);
                            if (null != jsonObject) this.add(
                                jsonObject.getInt("col"),           // throws org.json.JSONException
                                jsonObject.getInt("row"));          // throws org.json.JSONException
                        }
                        catch (final org.json.JSONException e) { /* Skip this jsonObject. */ }
                }
            }
        }
        // endregion

        // region External Methods
        private void makeOneRandomCell(final int xBound, final int yBound)
        {
            final java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell>
                cellArrayList = this.cellArrayList();
            cellArrayList.clear();
            cellArrayList.add(
                org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell.random(xBound, yBound));
        }

        private void makeRandomCells(int amount, final int xBound, final int yBound)
        {
            if (1 == amount)
                this.makeOneRandomCell(xBound, yBound);
            else
                if (amount > 1)
                {
                    final java.util.ArrayList<
                        org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell> cellArrayList =
                           this.cellArrayList();
                    cellArrayList.clear();

                    org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell cell;
                    do
                    {
                        do
                            cell =
                                org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell.random(
                                    xBound, yBound);
                        while (this.isPresent(cell));
                        cellArrayList.add(cell);
                    }
                    while (--amount > 0);
                }
        }

        private java.lang.String json()
        {
            if (null == this.cellArrayListInstance)
                return null;
            else
            {
                final java.util.ArrayList<
                    org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell> cellArrayList =
                        this.cellArrayList();

                if (cellArrayList.size() <= 0)
                    return null;
                else
                {
                    final org.json.JSONArray jsonArray = new org.json.JSONArray();

                    for (final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell cell:
                    cellArrayList)
                        if (null != cell)
                            try                                    { jsonArray.put(cell.json()); }
                            catch (final org.json.JSONException e) { /* Skip this JSONObject. */ }

                    return jsonArray.toString();
                }
            }
        }

        private void clear()
        { if (null != this.cellArrayListInstance) this.cellArrayList().clear(); }

        private boolean isPresent(final int x, final int y)
        {
            return this.isPresent(
                new org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell(x, y));
        }

        private void add(final int x, final int y)
        {
            this.cellArrayList().add(
                new org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell(x, y));
        }
        // endregion
    }

    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel.Cells excludeCellsInstance = null;
    private java.util.ArrayList<java.lang.Integer>
        excludeRowsInstance = null, excludeColsInstance = null;

    private boolean colNumbering, rowNumbering;

    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields optionalFields;

    private long timestamp;
    // endregion

    // region Private Methods
    private org.wheatgenetics.coordinate.model.TemplateModel.Cells excludeCells()
    {
        if (null == this.excludeCellsInstance) this.excludeCellsInstance =
            new org.wheatgenetics.coordinate.model.TemplateModel.Cells();
        return this.excludeCellsInstance;
    }

    private java.util.ArrayList<java.lang.Integer> excludeRows()
    {
        if (null == this.excludeRowsInstance)
            this.excludeRowsInstance = new java.util.ArrayList<java.lang.Integer>();
        return this.excludeRowsInstance;
    }

    private java.util.ArrayList<java.lang.Integer> excludeCols()
    {
        if (null == this.excludeColsInstance)
            this.excludeColsInstance = new java.util.ArrayList<java.lang.Integer>();
        return this.excludeColsInstance;
    }

    private static java.util.ArrayList<java.lang.Integer>
    jsonToIntegerArrayList(final java.lang.String json) throws org.json.JSONException
    {
        if (null == json)
            return null;
        else
            if (json.length() <= 0)
                return null;
            else
            {
                final java.util.ArrayList<java.lang.Integer> integerArrayList =
                    new java.util.ArrayList<java.lang.Integer>();
                {
                    org.json.JSONArray jsonArray;
                    {
                        final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
                        jsonArray = (org.json.JSONArray)
                            jsonTokener.nextValue();                // throws org.json.JSONException
                    }
                    assert null != jsonArray;
                    for (int i = 0; i < jsonArray.length(); i++)
                        integerArrayList.add(jsonArray.getInt(i));  // throws org.json.JSONException
                }
                return integerArrayList;
            }
    }
    // endregion

    // region Constructors
    public TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering)
    {
        super(title, type, rows, cols);

        this.colNumbering = colNumbering;
        this.rowNumbering = rowNumbering;
    }

    private TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering,
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields)
    {
        this(title, type, rows, cols, colNumbering, rowNumbering);
        this.optionalFields = nonNullOptionalFields;
    }

    public TemplateModel(final long id, final java.lang.String title, final int code,
    final int rows, final int cols, final java.lang.String excludeCells,
    final java.lang.String excludeRows, final java.lang.String excludeCols, final int colNumbering,
    final int rowNumbering, final java.lang.String optionalFields, final long timestamp)
    {
        super(id, title, code, rows, cols);

        this.excludeCellsInstance =
            new org.wheatgenetics.coordinate.model.TemplateModel.Cells(excludeCells);

        try
        {
            this.excludeRowsInstance =
                org.wheatgenetics.coordinate.model.TemplateModel.jsonToIntegerArrayList(
                    excludeRows);
        }
        catch (final org.json.JSONException e) { this.excludeRowsInstance = null; }
        try
        {
            this.excludeColsInstance =
                org.wheatgenetics.coordinate.model.TemplateModel.jsonToIntegerArrayList(
                    excludeCols);
        }
        catch (final org.json.JSONException e) { this.excludeColsInstance = null; }

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
            "TemplateModel"         , this.excludeCellsInstance, this.excludeRowsInstance,
            this.excludeColsInstance, this.optionalFields      , this.colNumbering       ,
            this.rowNumbering       , this.timestamp                                     );
    }
    // endregion

    // region Public Methods
    // region excludeCells Public Methods
    public void addExcludedCell(final int col, final int row) { this.excludeCells().add(col, row); }

    public void makeOneRandomCell()
    { this.excludeCells().makeOneRandomCell(this.getCols(), this.getRows()); }

    public void makeRandomCells(final int amount)
    { this.excludeCells().makeRandomCells(amount, this.getCols(), this.getRows()); }

    public java.lang.String getExcludeCellsAsJson()
    { return null == this.excludeCellsInstance ? null : this.excludeCells().json(); }

    public boolean isExcludedCell(final int col, final int row)
    { return null == this.excludeCellsInstance ? false : this.excludeCells().isPresent(col, row); }
    // endregion

    // region excludeRows, excludeCols Public Methods
    public java.util.ArrayList<java.lang.Integer> getExcludeRows()
    { return this.excludeRowsInstance; }

    public java.util.ArrayList<java.lang.Integer> getExcludeCols()
    { return this.excludeColsInstance; }

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

    public void clearExcludes()
    {
        if (null != this.excludeCellsInstance) this.excludeCells().clear();
        if (null != this.excludeRowsInstance ) this.excludeRows ().clear();
        if (null != this.excludeColsInstance ) this.excludeCols ().clear();
    }

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

        final java.util.ArrayList<java.lang.Integer> excludeRows = result.excludeRows();
        excludeRows.add(2);
        excludeRows.add(5);

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