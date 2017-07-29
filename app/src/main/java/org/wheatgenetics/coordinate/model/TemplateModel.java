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
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 */
public class TemplateModel extends org.wheatgenetics.coordinate.model.PartialTemplateModel
implements java.lang.Cloneable
{
    // region Types
    private static class Cells extends java.lang.Object implements java.lang.Cloneable
    {
        private static class Cell extends java.lang.Object implements java.lang.Cloneable
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

            // region Overridden Methods
            @java.lang.Override
            public String toString()
            { return null == this.point ? super.toString() : this.point.toString(); }

            @java.lang.Override @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
            public boolean equals(final java.lang.Object o)
            {
                if (null == o)
                    return false;
                else
                    if  (o instanceof org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell)
                    {
                        final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell c =
                            (org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell) o;

                        if (null == this.point && null != c.point)
                            return false;
                        else
                            if (null != this.point && null == c.point) return false;
                         return null == this.point ? true : this.point.equals(c.point.x, c.point.y);
                    }
                    else return false;
            }

            @java.lang.Override
            public int hashCode() { return this.toString().hashCode(); }

            @java.lang.Override
            protected java.lang.Object clone() throws java.lang.CloneNotSupportedException
            {
                return null == this.point ? super.clone() :
                    new org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell(
                        this.point.x, this.point.y);
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

            private org.json.JSONObject json() throws org.json.JSONException
            {
                if (null == this.point)
                    return null;
                else
                {
                    final org.json.JSONObject result = new org.json.JSONObject();

                    result.put("row", this.point.y);                // throws org.json.JSONException
                    result.put("col", this.point.x);                // throws org.json.JSONException

                    return result;
                }
            }
            // endregion
        }

        private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell>
            cellArrayListInstance = null;

        // region Internal Methods
        private static
        java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell> make()
        {
            return new
                java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell>();
        }

        private java.util.ArrayList<org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell>
        cellArrayList()
        {
            if (null == this.cellArrayListInstance) this.cellArrayListInstance =
                org.wheatgenetics.coordinate.model.TemplateModel.Cells.make();
            return this.cellArrayListInstance;
        }

        private boolean isPresent(@android.support.annotation.NonNull
        final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell candidateCell)
        {
            boolean result = false;

            if (null != this.cellArrayListInstance)
                for (final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell cell:
                this.cellArrayListInstance)
                    if (null != cell) if (cell.equals(candidateCell))
                    {
                        result = true;
                        break;
                    }

            return result;
        }
        // endregion

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
                    final int first = 0, last = length - 1;
                    for (int i = first; i <= last; i++)
                        try
                        {
                            final org.json.JSONObject jsonObject =
                                (org.json.JSONObject) jsonArray.get(i);
                            if (null != jsonObject) this.add(
                                jsonObject.getInt("col"),           // throws org.json.JSONException
                                jsonObject.getInt("row"));          // throws org.json.JSONException
                        }
                        catch (final org.json.JSONException e) { /* Skip this jsonObject. */ }
                }
            }
        }
        // endregion

        // region Overridden Methods
        @java.lang.Override
        public String toString()
        {
            if (null == this.cellArrayListInstance)
                return super.toString();
            else
                if (this.cellArrayListInstance.size() <= 0)
                    return super.toString();
                else
                {
                    java.lang.String result = null;
                    {
                        boolean firstCell = true;
                        for (final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell
                        cell: this.cellArrayListInstance) if (null != cell)
                            if (firstCell)
                            {
                                result    = cell.toString();
                                firstCell = false          ;
                            }
                            else result += '\n' + cell.toString();
                    }
                    return org.wheatgenetics.javalib.Utils.replaceIfNull(result, super.toString());
                }
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object o)
        {
            if (null == o)
                return false;
            else
                if (o instanceof org.wheatgenetics.coordinate.model.TemplateModel.Cells)
                {
                    final org.wheatgenetics.coordinate.model.TemplateModel.Cells cs =
                        (org.wheatgenetics.coordinate.model.TemplateModel.Cells) o;

                    if (null == this.cellArrayListInstance && null != cs.cellArrayListInstance)
                        return false;
                    else
                        if (null != this.cellArrayListInstance && null == cs.cellArrayListInstance)
                            return false;

                    if (null == this.cellArrayListInstance)
                        return true;
                    else
                        if (this.cellArrayListInstance.size() != cs.cellArrayListInstance.size())
                            return false;
                        else
                        {
                            {
                                int i = 0;

                                for (final
                                org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell cell:
                                this.cellArrayListInstance)
                                {
                                    final
                                        org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell
                                            c = cs.cellArrayListInstance.get(i++);

                                    if (null == cell && null != c)
                                        return false;
                                    else
                                        if (null != cell && null == c)
                                            return false;
                                        else if (null != cell) if (!cell.equals(c)) return false;
                                }
                            }
                            return true;
                        }
                }
                else return false;
        }

        @java.lang.Override
        public int hashCode() { return this.toString().hashCode(); }

        @java.lang.Override @java.lang.SuppressWarnings("CloneDoesntCallSuperClone")
        protected java.lang.Object clone() throws java.lang.CloneNotSupportedException
        {
            final org.wheatgenetics.coordinate.model.TemplateModel.Cells result =
                new org.wheatgenetics.coordinate.model.TemplateModel.Cells();

            if (null != this.cellArrayListInstance)
            {
                result.cellArrayListInstance =
                    org.wheatgenetics.coordinate.model.TemplateModel.Cells.make();
                for (final org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell cell:
                this.cellArrayListInstance)
                    if (null != cell) result.cellArrayListInstance.add(
                        (org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell) cell.clone());
            }

            return result;
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

                    do
                    {
                        org.wheatgenetics.coordinate.model.TemplateModel.Cells.Cell cell;
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

    private static class Coordinates extends java.lang.Object implements java.lang.Cloneable
    {
        private java.util.ArrayList<java.lang.Integer> integerArrayListInstance = null;

        // region Internal Methods
        private static java.util.ArrayList<java.lang.Integer> make()
        { return new java.util.ArrayList<java.lang.Integer>(); }

        private java.util.ArrayList<java.lang.Integer> integerArrayList()
        {
            if (null == this.integerArrayListInstance) this.integerArrayListInstance =
                org.wheatgenetics.coordinate.model.TemplateModel.Coordinates.make();
            return this.integerArrayListInstance;
        }
        // endregion

        // region Constructors
        Coordinates() { super(); }

        Coordinates(final java.lang.String json)
        {
            super();

            if (null != json) if (json.length() <= 0)
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
                    { return; /* Leave integerArrayListInstance == null. */ }
                }

                assert null != jsonArray;
                final int length = jsonArray.length();
                if (length > 0)
                {
                    final int first = 0, last = length - 1;
                    for (int i = first; i <= last; i++)
                        try { this.add(jsonArray.getInt(i) /* throws org.json.JSONException */); }
                        catch (final org.json.JSONException e) { /* Skip this jsonObject. */ }
                }
            }
        }
        // endregion

        // region Overridden Methods
        @java.lang.Override
        public java.lang.String toString()
        {
            if (null == this.integerArrayListInstance)
                return super.toString();
            else
                if (this.integerArrayList().size() <= 0)
                    return super.toString();
                else
                {
                    java.lang.String result = null;
                    {
                        boolean firstInteger = true;
                        for (final java.lang.Integer integer: this.integerArrayListInstance)
                            if (null != integer)
                                if (firstInteger)
                                {
                                    result       = integer.toString();
                                    firstInteger = false             ;
                                }
                                else result += '\n' + integer.toString();
                    }
                    return org.wheatgenetics.javalib.Utils.replaceIfNull(result, super.toString());
                }
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object o)
        {
            if (null == o)
                return false;
            else
                if (o instanceof org.wheatgenetics.coordinate.model.TemplateModel.Coordinates)
                {
                    final org.wheatgenetics.coordinate.model.TemplateModel.Coordinates cs =
                        (org.wheatgenetics.coordinate.model.TemplateModel.Coordinates) o;

                    if (null == this.integerArrayListInstance
                    &&  null != cs.integerArrayListInstance  )
                        return false;
                    else
                        if (null != this.integerArrayListInstance
                        &&  null == cs.integerArrayListInstance  )
                            return false;

                    if (null == this.integerArrayListInstance)
                        return true;
                    else
                        if (this.integerArrayListInstance.size()
                        !=  cs.integerArrayListInstance.size()  )
                            return false;
                        else
                        {
                            {
                                int i = 0;

                                for (final java.lang.Integer integer: this.integerArrayListInstance)
                                {
                                    final java.lang.Integer c =
                                        cs.integerArrayListInstance.get(i++);

                                    if (null == integer && null != c)
                                        return false;
                                    else
                                        if (null != integer && null == c)
                                            return false;
                                        else
                                            if (null != integer)
                                                if (!integer.equals(c)) return false;
                                }
                            }
                            return true;
                        }
                }
                else return false;
        }

        @java.lang.Override
        public int hashCode() { return this.toString().hashCode(); }

        @java.lang.Override @java.lang.SuppressWarnings(
            {"CloneDoesntCallSuperClone", "UnnecessaryBoxing", "UnnecessaryUnboxing"})
        protected java.lang.Object clone() throws java.lang.CloneNotSupportedException
        {
            final org.wheatgenetics.coordinate.model.TemplateModel.Coordinates result =
                new org.wheatgenetics.coordinate.model.TemplateModel.Coordinates();

            if (null != this.integerArrayListInstance)
            {
                result.integerArrayListInstance =
                    org.wheatgenetics.coordinate.model.TemplateModel.Coordinates.make();
                for (final java.lang.Integer integer: this.integerArrayListInstance)
                    result.integerArrayListInstance.add(new java.lang.Integer(integer.intValue()));
            }

            return result;
        }
        // endregion

        // region External Methods
        private void add(final int integer) { this.integerArrayList().add(integer); }

        private java.lang.String json()
        {
            if (null == this.integerArrayListInstance)
                return null;
            else
            {
                final java.util.ArrayList<java.lang.Integer> integerArrayList =
                    this.integerArrayList();

                if (integerArrayList.size() <= 0)
                    return null;
                else
                {
                    final org.json.JSONArray jsonArray = new org.json.JSONArray();

                    for (final java.lang.Integer integer: integerArrayList)
                        if (null != integer) jsonArray.put(integer.intValue());

                    return jsonArray.toString();
                }
            }
        }

        private void clear()
        { if (null != this.integerArrayListInstance) this.integerArrayList().clear(); }

        private boolean isPresent(final int candidateInteger)
        {
            boolean result = false;

            if (null != this.integerArrayListInstance)
                for (final java.lang.Integer integer: this.integerArrayListInstance)
                    if (null != integer) if (integer.intValue() == candidateInteger)
                    {
                        result = true;
                        break;
                    }

            return result;
        }
        // endregion
    }
    // endregion

    // region Fields
    private org.wheatgenetics.coordinate.model.TemplateModel.Cells excludeCellsInstance = null;
    private org.wheatgenetics.coordinate.model.TemplateModel.Coordinates
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

    private org.wheatgenetics.coordinate.model.TemplateModel.Coordinates excludeRows()
    {
        if (null == this.excludeRowsInstance) this.excludeRowsInstance =
            new org.wheatgenetics.coordinate.model.TemplateModel.Coordinates();
        return this.excludeRowsInstance;
    }

    private org.wheatgenetics.coordinate.model.TemplateModel.Coordinates excludeCols()
    {
        if (null == this.excludeColsInstance) this.excludeColsInstance =
            new org.wheatgenetics.coordinate.model.TemplateModel.Coordinates();
        return this.excludeColsInstance;
    }

    private static boolean valid(final int numbering)
    {
        if (numbering < 0 || numbering > 1)
            throw new java.lang.IllegalArgumentException();
        else
            return 1 == numbering;
    }
    // endregion

    // region Constructors
    public TemplateModel(final java.lang.String title,
    final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
    final boolean colNumbering, final boolean rowNumbering)
    {
        super(title, type, rows, cols);

        this.setColNumbering(colNumbering);
        this.setRowNumbering(rowNumbering);
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

        this.excludeRowsInstance =
            new org.wheatgenetics.coordinate.model.TemplateModel.Coordinates(excludeRows);
        this.excludeColsInstance =
            new org.wheatgenetics.coordinate.model.TemplateModel.Coordinates(excludeCols);

        this.setColNumbering(colNumbering);
        this.setRowNumbering(rowNumbering);

        try
        {
            this.optionalFields = new
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(optionalFields);
        }
        catch (final org.json.JSONException e) { this.optionalFields = null; }

        this.timestamp = timestamp;
    }
    // endregion

    // region Overridden Methods
    // region toString() Overridden Methods
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
            this.excludeColsInstance, this.optionalFields      , this.getColNumbering()  ,
            this.getRowNumbering()  , this.getTimestamp()                                );
    }
    // endregion

    @java.lang.Override
    public boolean equals(final java.lang.Object o)
    {
        if (super.equals(o))
            if (o instanceof org.wheatgenetics.coordinate.model.TemplateModel)
            {
                final org.wheatgenetics.coordinate.model.TemplateModel t =
                    (org.wheatgenetics.coordinate.model.TemplateModel) o;

                if (null == this.excludeCellsInstance && null != t.excludeCellsInstance)
                    return false;
                else
                    if (null != this.excludeCellsInstance && null == t.excludeCellsInstance)
                        return false;
                if (null != this.excludeCellsInstance)
                    if (!this.excludeCellsInstance.equals(t.excludeCellsInstance)) return false;

                if (null == this.excludeRowsInstance && null != t.excludeRowsInstance)
                    return false;
                else
                    if (null != this.excludeRowsInstance && null == t.excludeRowsInstance)
                        return false;
                if (null != this.excludeRowsInstance)
                    if (!this.excludeRowsInstance.equals(t.excludeRowsInstance)) return false;

                if (null == this.excludeColsInstance && null != t.excludeColsInstance)
                    return false;
                else
                    if (null != this.excludeColsInstance && null == t.excludeColsInstance)
                        return false;
                if (null != this.excludeColsInstance)
                    if (!this.excludeColsInstance.equals(t.excludeColsInstance)) return false;

                if (this.getColNumbering() != t.getColNumbering()) return false;
                if (this.getRowNumbering() != t.getRowNumbering()) return false;

                if (null == this.optionalFields && null != t.optionalFields)
                    return false;
                else
                    if (null != this.optionalFields && null == t.optionalFields) return false;
                if (null != this.optionalFields)
                    if (!this.optionalFields.equals(t.optionalFields)) return false;

                return this.getTimestamp() == t.getTimestamp();
            }
            else return false;
        else return false;
    }

    @java.lang.Override
    public int hashCode() { return this.toString().hashCode(); }

    @java.lang.Override @java.lang.SuppressWarnings("CloneDoesntCallSuperClone")
    protected java.lang.Object clone() throws java.lang.CloneNotSupportedException
    {
        final org.wheatgenetics.coordinate.model.TemplateModel result =
            new org.wheatgenetics.coordinate.model.TemplateModel(this.getTitle(), this.getType(),
                this.getRows(), this.getCols(), this.getColNumbering(), this.getRowNumbering());

        if (null != this.excludeCellsInstance)
            result.excludeCellsInstance = (org.wheatgenetics.coordinate.model.TemplateModel.Cells)
                this.excludeCellsInstance.clone();

        if (null != this.excludeRowsInstance) result.excludeRowsInstance =
            (org.wheatgenetics.coordinate.model.TemplateModel.Coordinates)
                this.excludeRowsInstance.clone();
        if (null != this.excludeColsInstance) result.excludeColsInstance =
            (org.wheatgenetics.coordinate.model.TemplateModel.Coordinates)
                this.excludeColsInstance.clone();

        if (null != this.optionalFields) result.optionalFields =
            (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                this.optionalFields.clone();

        result.timestamp = this.getTimestamp();

        return result;
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

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedCell(final int col, final int row)
    { return null == this.excludeCellsInstance ? false : this.excludeCells().isPresent(col, row); }
    // endregion

    // region excludeRows, excludeCols Public Methods
    public void addExcludeRow(final int row) { this.excludeRows().add(row); }
    public void addExcludeCol(final int col) { this.excludeCols().add(col); }

    public java.lang.String getExcludeRowsAsJson()
    { return null == this.excludeRowsInstance ? null : this.excludeRows().json(); }

    public java.lang.String getExcludeColsAsJson()
    { return null == this.excludeColsInstance ? null : this.excludeCols().json(); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedRow(final int integer)
    { return null == this.excludeRowsInstance ? false : this.excludeRows().isPresent(integer); }

    @java.lang.SuppressWarnings("SimplifiableConditionalExpression")
    public boolean isExcludedCol(final int integer)
    { return null == this.excludeColsInstance ? false : this.excludeCols().isPresent(integer); }
    // endregion

    // region colNumbering, rowNumbering Public Methods
    public boolean getColNumbering()                           { return this.colNumbering        ; }
    public void    setColNumbering(final boolean colNumbering) { this.colNumbering = colNumbering; }
    public void    setColNumbering(final int     colNumbering)
    { this.setColNumbering(org.wheatgenetics.coordinate.model.TemplateModel.valid(colNumbering)); }

    public boolean getRowNumbering()                           { return this.rowNumbering        ; }
    public void    setRowNumbering(final boolean rowNumbering) { this.rowNumbering = rowNumbering; }
    public void    setRowNumbering(final int     rowNumbering)
    { this.setRowNumbering(org.wheatgenetics.coordinate.model.TemplateModel.valid(rowNumbering)); }
    // endregion

    public java.lang.String getOptionalFields() throws org.json.JSONException
    { return null == this.optionalFields ? null : this.optionalFields.toJson(); }

    public long getTimestamp() { return this.timestamp; }

    public void clearExcludes()
    {
        if (null != this.excludeCellsInstance) this.excludeCells().clear();
        if (null != this.excludeRowsInstance ) this.excludeRows ().clear();
        if (null != this.excludeColsInstance ) this.excludeCols ().clear();
    }

    public android.graphics.Point nextFreeCell(final android.graphics.Point currentPoint)
    {
        if (null == currentPoint)
            return null;
        else
        {
            final android.graphics.Point result = null;
            {
                int col, row = currentPoint.y;
                {
                    final int lastCol = this.getCols();
                    for (col = currentPoint.x; col <= lastCol; col++) if (!this.isExcludedCol(col))
                    {
                        final int lastRow = this.getRows();
                        for (row = currentPoint.y; row <= lastRow; row++)
                            if (!this.isExcludedRow(row)) if (!this.isExcludedCell(row, col)) break;
                    }
                }
                currentPoint.x = col;
                currentPoint.y = row;
            }
            return result;
        }
    }

    // region Make Public Methods
    public static org.wheatgenetics.coordinate.model.TemplateModel makeInitial()
    {
        return
            new org.wheatgenetics.coordinate.model.TemplateModel(
            /* title        => */ ""                                                  ,
            /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
            /* rows         => */ 20                                                  ,
            /* cols         => */ 10                                                  ,
            /* colNumbering => */ true                                                ,
            /* rowNumbering => */ false                                               );
    }

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

        final org.wheatgenetics.coordinate.model.TemplateModel.Coordinates excludeRows =
            result.excludeRows();
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