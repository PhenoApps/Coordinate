package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class GridModel extends Model {
    // region Fields
    @IntRange(from = 1)
    private final long templateId;
    @IntRange(from = 0)
    private long projectId;
    private final String person;
    @Nullable
    private final
    NonNullOptionalFields
            nonNullOptionalFieldsInstance;
    @IntRange(from = 0)
    private final long timestamp;
    @IntRange(from = 0)
    private int activeRow, activeCol;
    // endregion

    // region Constructors

    /**
     * Used by first JoinedGridModel constructor.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    GridModel(
            @IntRange(from = 1) final long templateId,
            @IntRange(from = 0) final long projectId,
            final String person,
            @Nullable final
            NonNullOptionalFields optionalFields,
            @NonNull final StringGetter stringGetter) {
        super(stringGetter);

        this.templateId =
                Model.valid(templateId, this.stringGetter());
        this.projectId =
                Model.illegal(projectId) ? 0 : projectId;
        this.person = person;

        this.activeRow = this.activeCol = 0;

        this.nonNullOptionalFieldsInstance = optionalFields;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Used by second JoinedGridModel constructor.
     */
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    GridModel(
            @IntRange(from = 1) final long id,
            @IntRange(from = 1) final long templateId,
            @IntRange(from = 0) final long projectId,
            final String person,
            @IntRange(from = 0) final int activeRow,
            @IntRange(from = 0) final int activeCol,
            @Nullable String optionalFields,
            @NonNull final StringGetter stringGetter,
            @IntRange(from = 0) final long timestamp) {
        super(id, stringGetter);

        this.templateId =
                Model.valid(templateId, this.stringGetter());
        this.projectId =
                Model.illegal(projectId) ? 0 : projectId;
        this.person = person;

        this.setActiveRow(activeRow);
        this.setActiveCol(activeCol);

        if (null != optionalFields) optionalFields = optionalFields.trim();
        this.nonNullOptionalFieldsInstance = null == optionalFields ? null :
                optionalFields.equals("") ? null : new
                        NonNullOptionalFields(
                        optionalFields, stringGetter, true);
        this.timestamp = timestamp;
    }
    // endregion

    // region Public Methods
    @IntRange(from = 1)
    public long getTemplateId() {
        return this.templateId;
    }

    @IntRange(from = 0)
    public long getProjectId() {
        return this.projectId;
    }

    public void setProjectId(long id) {
        this.projectId = id;
    }
    // endregion

    public String getPerson() {
        return this.person;
    }

    @IntRange(from = 0)
    public int getActiveRow() {
        return this.activeRow;
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setActiveRow(@IntRange(from = 0) final int activeRow) {
        this.activeRow = org.wheatgenetics.coordinate.Utils.valid(
                activeRow, 0, this.stringGetter());
    }

    @IntRange(from = 0)
    public int getActiveCol() {
        return this.activeCol;
    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    void setActiveCol(@IntRange(from = 0) final int activeCol) {
        this.activeCol = org.wheatgenetics.coordinate.Utils.valid(
                activeCol, 0, this.stringGetter());
    }

    @IntRange(from = 0)
    public long getTimestamp() {
        return this.timestamp;
    }

    public CharSequence getFormattedTimestamp() {
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.getDefault());
        return timeStamp.format(this.getTimestamp());
    }

    // region optionalFields Public Methods
    @Nullable
    public NonNullOptionalFields optionalFields() {
        return this.nonNullOptionalFieldsInstance;
    }

    @Nullable
    public String optionalFieldsAsJson() {
        return null == this.nonNullOptionalFieldsInstance ?
                null : this.nonNullOptionalFieldsInstance.toJson();
    }

    @Nullable
    public String getFirstOptionalFieldDatedValue() {
        return null == this.nonNullOptionalFieldsInstance ? null :
                this.nonNullOptionalFieldsInstance.getDatedFirstValue();
    }
    // endregion
    // endregion
}