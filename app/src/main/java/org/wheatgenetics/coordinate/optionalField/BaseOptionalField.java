package org.wheatgenetics.coordinate.optionalField;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.Size;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.phenoapps.androidlibrary.Utils;

public abstract class BaseOptionalField {
    static final String TIMESTAMP_HINT = "yyyy-MM-dd-hh-mm-ss";

    // region Fields
    // region Constructor Fields
    @NonNull
    @Size(min = 1)
    private final String
            name;
    @NonNull
    @Size(min = 0)
    private String
            hint;
    @NonNull
    private final StringGetter
            stringGetter;
    // endregion

    @NonNull
    private String value = "";
    private boolean checked = true;
    // endregion

    // region Constructors
    BaseOptionalField(@NonNull @Size(min = 1) final String name, @Nullable final String hint,
                      @NonNull final StringGetter stringGetter) {
        super();

        this.name = BaseOptionalField.valid(name);
        this.hint = Utils.makeEmptyIfNull(hint).trim();
        this.stringGetter = stringGetter;
    }

    BaseOptionalField(
            @NonNull @Size(min = 1) final String name,
            @NonNull final StringGetter stringGetter) {
        this(name, "", stringGetter);
    }
    // endregion

    // region Private Methods
    @NonNull
    private static String valid(
            @NonNull @Size(min = 1) final String name) {
        // noinspection ConstantConditions
        if (null == name)
            throw new AssertionError();
        else {
            final String result = name.trim();
            if (result.length() <= 0)
                throw new AssertionError();
            else
                return result;
        }
    }

    // region Public Methods
    @NonNull
    @Size(min = 1)
    public static String identificationFieldName(
            @NonNull final StringGetter stringGetter) {
        final String result = stringGetter.get(
                R.string.BaseOptionalFieldIdentificationFieldName);
        if (null == result)
            throw new NullPointerException();
        else
            return result;
    }
    // endregion

    private boolean nameIsPerson() {
        return this.namesAreEqual(this.stringGetter.get(
                R.string.BaseOptionalFieldPersonFieldName));
    }

    // region Overridden Methods
    @Override
    @NonNull
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof BaseOptionalField) {
            final BaseOptionalField
                    baseOptionalField =
                    (BaseOptionalField) object;
            return
                    this.getName().equals(baseOptionalField.getName()) &&
                            this.getValue().equals(baseOptionalField.getValue()) &&
                            this.getHint().equals(baseOptionalField.getHint()) &&
                            this.getChecked() == baseOptionalField.getChecked();
        } else return false;
    }
    // endregion

    /**
     * Overridden just to elevate from protected to public.
     */
    @Override
    @NonNull
    public Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    // region Package Methods
    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    @NonNull
    StringGetter stringGetter() {
        return this.stringGetter;
    }

    boolean namesAreEqual(final String name) {
        return this.getName().equalsIgnoreCase(name);
    }

    boolean nameIsIdentification() {
        return this.getName().equals(
                BaseOptionalField.identificationFieldName(
                        this.stringGetter));
    }

    @NonNull
    String getSafeValue() {
        return this.nameIsPerson() ?
                this.getValue().replace(" ", "_") : this.getValue();
    }
    // endregion

    boolean getChecked() {
        return this.checked;
    }

    public void setChecked(final boolean checked) {
        this.checked = this.nameIsIdentification() || checked;
    }

    // region Getter and Setter Public Methods
    @NonNull
    @Size(min = 1)
    public String getName() {
        return this.name;
    }

    @NonNull
    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = Utils.makeEmptyIfNull(value).trim();
    }

    public void setHint(final String hint) {
        this.hint  = hint;
    }

    @NonNull
    @Size(min = 0)
    public String getHint() {
        return this.hint;
    }
    // endregion

    public boolean isAPerson() {
        return this.nameIsPerson() || this.namesAreEqual(this.stringGetter.get(
                R.string.BaseOptionalFieldNameFieldName));
    }
    // endregion
}