package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.StringGetter;

import java.util.Locale;

abstract class BaseTemplateModel extends Model {
    // region Fields
    @IntRange(from = 0)
    private final long timestamp;

    private String title;
    private TemplateType type;

    @IntRange(from = 1)
    private int rows, cols;
    @IntRange(from = 0)
    private int generatedExcludedCellsAmount;

    private boolean colNumbering, rowNumbering;
    private String entryLabel;
    // endregion

    /**
     * Called by first and fourth DisplayTemplateModel constructors.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    BaseTemplateModel(@IntRange(from = 1) final long id,
                      final String title, final TemplateType type,
                      @IntRange(from = 1) final int rows,
                      @IntRange(from = 1) final int cols,
                      @IntRange(from = 0) final int generatedExcludedCellsAmount,
                      final boolean colNumbering, final boolean rowNumbering,
                      @IntRange(from = 0) final long timestamp,
                      @NonNull final StringGetter stringGetter) {
        super(id, stringGetter);
        this.assign(title, type, rows, cols,
                generatedExcludedCellsAmount, colNumbering, rowNumbering);
        this.timestamp = timestamp;
    }

    /**
     * Called by second DisplayTemplateModel constructor.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    BaseTemplateModel(final String title,
                      final TemplateType type,
                      @IntRange(from = 1) final int rows,
                      @IntRange(from = 1) final int cols,
                      @IntRange(from = 0) final int generatedExcludedCellsAmount,
                      final boolean colNumbering, final boolean rowNumbering,
                      @IntRange(from = 0) final long timestamp,
                      @NonNull final StringGetter stringGetter) {
        super(stringGetter);
        this.assign(title, type, rows, cols,
                generatedExcludedCellsAmount, colNumbering, rowNumbering);
        this.timestamp = timestamp;
    }


    /**
     * Called by third DisplayTemplateModel constructor.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    BaseTemplateModel(@NonNull final StringGetter stringGetter) {
        super(stringGetter);

        this.type = TemplateType.USERDEFINED;
        this.timestamp = System.currentTimeMillis();
    }

    @Nullable
    private static String[] items(
            @IntRange(from = 1) final int length, final String label) {
        if (length <= 0)
            return null;
        else {
            // noinspection CStyleArrayDeclaration
            final String result[] = new String[length];
            for (int i = 0; i < length; i++)
                result[i] = String.format(
                        Locale.getDefault(), "%s %d", label, i + 1);
            return result;
        }
    }
    // endregion

    // region Constructors

    /**
     * Called by first and second constructor.
     */
    private void assign(final String title,
                        final TemplateType type,
                        @IntRange(from = 1) final int rows,
                        @IntRange(from = 1) final int cols,
                        @IntRange(from = 0) final int generatedExcludedCellsAmount,
                        final boolean colNumbering, final boolean rowNumbering) {
        this.setTitle(title);
        this.setType(type);
        this.setRows(rows);
        this.setCols(cols);
        this.setGeneratedExcludedCellsAmount(generatedExcludedCellsAmount);
        this.setColNumbering(colNumbering);
        this.setRowNumbering(rowNumbering);
    }

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        return String.format(this.formatString(), "BaseTemplateModel") + "]";
    }

    @Override
    public boolean equals(final Object object) {
        if (super.equals(object))
            if (object instanceof BaseTemplateModel) {
                final BaseTemplateModel baseTemplateModel =
                        (BaseTemplateModel) object;

                {
                    final String
                            myTitle = this.getTitle(), yourTitle = baseTemplateModel.getTitle();
                    if (null == myTitle && null != yourTitle)
                        return false;
                    else if (null != myTitle && null == yourTitle)
                        return false;
                    else if (null != myTitle) if (!myTitle.equals(yourTitle)) return false;
                }

                {
                    final TemplateType
                            myTemplateType = this.getType(),
                            yourTemplateType = baseTemplateModel.getType();
                    if (null == myTemplateType && null != yourTemplateType)
                        return false;
                    else if (null != myTemplateType && null == yourTemplateType)
                        return false;
                    else if (null != myTemplateType)
                        if (myTemplateType.getCode() != yourTemplateType.getCode())
                            return false;
                }

                if (this.getRows() != baseTemplateModel.getRows()
                        || this.getCols() != baseTemplateModel.getCols()) return false;

                if (this.getGeneratedExcludedCellsAmount()
                        != baseTemplateModel.getGeneratedExcludedCellsAmount())
                    return false;

                if (this.getColNumbering() != baseTemplateModel.getColNumbering()) return false;
                if (this.getRowNumbering() != baseTemplateModel.getRowNumbering()) return false;

                if (this.getTimestamp() != baseTemplateModel.getTimestamp()) return false;

                {
                    final String
                            myEntryLabel = this.getEntryLabel(),
                            yourEntryLabel = baseTemplateModel.getEntryLabel();
                    if (null == myEntryLabel && null != yourEntryLabel)
                        return false;
                    else if (null != myEntryLabel && null == yourEntryLabel)
                        return false;
                    else
                        // noinspection SimplifiableConditionalExpression
                        return null == myEntryLabel ? true :
                                myEntryLabel.equals(yourEntryLabel);
                }
            } else return false;
        else return false;
    }
    // endregion

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    String formatString() {
        return "%s" + String.format(Locale.getDefault(),
                " [%s, title=%s, type=%d, rows=%d, cols=%d, generatedExcludedCellsAmount=%d, " +
                        "colNumbering=%b, rowNumbering=%b, entryLabel=%s, stamp=%d", super.toString(),
                this.getTitle(), this.getType().getCode(), this.getRows(), this.getCols(),
                this.getGeneratedExcludedCellsAmount(), this.getColNumbering(), this.getRowNumbering(),
                this.getEntryLabel(), this.getTimestamp());
    }

    boolean entryLabelIsNotNull() {
        return null != this.getEntryLabel();
    }
    // endregion

    // region Public Methods
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public TemplateType getType() {
        return this.type;
    }

    public void setType(final TemplateType templateType) {
        this.type = templateType;
    }

    @IntRange(from = 1)
    public int getRows() {
        return this.rows;
    }

    // region Private Methods
    private void setRows(@IntRange(from = 1) final int rows) {
        this.rows = org.wheatgenetics.coordinate.Utils.valid(rows, 1, this.stringGetter());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setRows(final String rows) {
        this.setRows(Integer.parseInt(rows));
    }

    @IntRange(from = 1)
    public int getCols() {
        return this.cols;
    }
    // endregion

    private void setCols(@IntRange(from = 1) final int cols) {
        this.cols = org.wheatgenetics.coordinate.Utils.valid(cols, 1, this.stringGetter());
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setCols(final String cols) {
        this.setCols(Integer.parseInt(cols));
    }

    @IntRange(from = 0)
    public int getGeneratedExcludedCellsAmount() {
        return this.generatedExcludedCellsAmount;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setGeneratedExcludedCellsAmount(final String generatedExcludedCellsAmount) {
        this.setGeneratedExcludedCellsAmount(
                Integer.parseInt(generatedExcludedCellsAmount));
    }

    public void setGeneratedExcludedCellsAmount(
            @IntRange(from = 0) final int amount) {
        this.generatedExcludedCellsAmount =
                org.wheatgenetics.coordinate.Utils.valid(amount, 0, this.stringGetter());
    }

    public boolean getColNumbering() {
        return this.colNumbering;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setColNumbering(final String colNumbering) {
        this.setColNumbering(Boolean.parseBoolean(colNumbering));
    }

    public void setColNumbering(final boolean colNumbering) {
        this.colNumbering = colNumbering;
    }

    public boolean getRowNumbering() {
        return this.rowNumbering;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setRowNumbering(final String rowNumbering) {
        this.setRowNumbering(Boolean.parseBoolean(rowNumbering));
    }

    public void setRowNumbering(final boolean rowNumbering) {
        this.rowNumbering = rowNumbering;
    }

    public String getEntryLabel() {
        return this.entryLabel;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setEntryLabel(final String entryLabel) {
        this.entryLabel = entryLabel;
    }

    @IntRange(from = 0)
    public long getTimestamp() {
        return this.timestamp;
    }

    @Nullable
    public CharSequence getFormattedTimestamp() {
        final long timestamp = this.getTimestamp();
        return timestamp < 1 ? null : Utils.formatDate(timestamp);
    }


    public void assign(final String title,
                       @IntRange(from = 1) final int rows,
                       @IntRange(from = 1) final int cols) {
        this.setTitle(title);
        this.setRows(rows);
        this.setCols(cols);
        this.setType(TemplateType.USERDEFINED);
    }


    public boolean isDefaultTemplate() {
        return this.getType().isDefaultTemplate();
    }


    @Nullable
    public String[] rowItems(final String label) {
        return BaseTemplateModel.items(this.getRows(), label);
    }

    @Nullable
    public String[] colItems(final String label) {
        return BaseTemplateModel.items(this.getCols(), label);
    }
    // endregion
}