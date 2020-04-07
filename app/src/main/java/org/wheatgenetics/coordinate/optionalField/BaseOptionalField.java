package org.wheatgenetics.coordinate.optionalField;

/**
 * BaseOptionalField and OptionalField used to be one class that used org.json.JSONException and
 * org.json.JSONObject.  The one class was split into two in order to do as much local unit testing
 * as possible (BaseOptionalFieldTest) and as little instrumented testing as possible
 * (OptionalFieldTest).  (BaseOptionalField does not use org.json.JSONException or
 * org.json.JSONObject while OptionalField does.)
 *
 * Uses:
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.RestrictTo
 * androidx.annotation.RestrictTo.Scope
 * androidx.annotation.Size
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class BaseOptionalField extends java.lang.Object
{
    static final java.lang.String DATE_HINT = "yyyy-mm-dd";

    // region Fields
    // region Constructor Fields
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1) private final java.lang.String
        name;
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 0) private final java.lang.String
        hint;
    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.StringGetter
        stringGetter;
    // endregion

    @androidx.annotation.NonNull private java.lang.String value   = ""  ;
                                 private boolean          checked = true;
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull private static java.lang.String valid(
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1) final java.lang.String name)
    {
        // noinspection ConstantConditions
        if (null == name)
            throw new java.lang.AssertionError();
        else
        {
            final java.lang.String result = name.trim();
            if (result.length() <= 0)
                throw new java.lang.AssertionError();
            else
                return result;
        }
    }

    private boolean nameIsPerson()
    {
        return this.namesAreEqual(this.stringGetter.get(
            org.wheatgenetics.coordinate.R.string.BaseOptionalFieldPersonFieldName));
    }
    // endregion

    // region Constructors
    BaseOptionalField(@androidx.annotation.NonNull @androidx.annotation.Size(min = 1)
    final java.lang.String name, @androidx.annotation.Nullable final java.lang.String hint,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        super();

        this.name = org.wheatgenetics.coordinate.optionalField.BaseOptionalField.valid(name)       ;
        this.hint = org.wheatgenetics.javalib.Utils.makeEmptyIfNull                   (hint).trim();
        this.stringGetter = stringGetter;
    }

    BaseOptionalField(
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1) final java.lang.String name    ,
    @androidx.annotation.NonNull       final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { this(name,"", stringGetter); }
    // endregion

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull public java.lang.String toString()
    { return this.getName(); }

    @java.lang.Override public boolean equals(final java.lang.Object object)
    {
        if (object instanceof org.wheatgenetics.coordinate.optionalField.BaseOptionalField)
        {
            final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                baseOptionalField =
                    (org.wheatgenetics.coordinate.optionalField.BaseOptionalField) object;
            return
                this.getName   ().equals(baseOptionalField.getName   ()) &&
                this.getValue  ().equals(baseOptionalField.getValue  ()) &&
                this.getHint   ().equals(baseOptionalField.getHint   ()) &&
                this.getChecked()   ==   baseOptionalField.getChecked()   ;
        }
        else return false;
    }

    /** Overridden just to elevate from protected to public. */
    @java.lang.Override @androidx.annotation.NonNull public java.lang.Object clone()
    throws java.lang.CloneNotSupportedException { return super.clone(); }
    // endregion

    // region Package Methods
    @androidx.annotation.RestrictTo(androidx.annotation.RestrictTo.Scope.SUBCLASSES)
    @androidx.annotation.NonNull org.wheatgenetics.coordinate.StringGetter stringGetter()
    { return this.stringGetter; }

    boolean namesAreEqual(final java.lang.String name)
    { return this.getName().equalsIgnoreCase(name); }

    boolean nameIsIdentification()
    {
        return this.getName().equals(
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField.identificationFieldName(
                this.stringGetter));
    }

    @androidx.annotation.NonNull java.lang.String getSafeValue()
    {
        return this.nameIsPerson() ?
            this.getValue().replace(" ","_") : this.getValue();
    }

    boolean getChecked() { return this.checked; }
    // endregion

    // region Public Methods
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1)
    public static java.lang.String identificationFieldName(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        final java.lang.String result = stringGetter.get(
            org.wheatgenetics.coordinate.R.string.BaseOptionalFieldIdentificationFieldName);
        if (null == result)
            throw new NullPointerException();
        else
            return result;
    }

    // region Getter and Setter Public Methods
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1)
    public java.lang.String getName () { return this.name ; }

    @androidx.annotation.NonNull public java.lang.String getValue() { return this.value; }

    public void setValue(final java.lang.String value)
    { this.value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(value).trim(); }

    @androidx.annotation.NonNull @androidx.annotation.Size(min = 0)
    public java.lang.String getHint() { return this.hint; }

    public void setChecked(final boolean checked)
    { this.checked = this.nameIsIdentification() || checked; }
    // endregion

    public boolean isAPerson()
    {
        return this.nameIsPerson() || this.namesAreEqual(this.stringGetter.get(
            org.wheatgenetics.coordinate.R.string.BaseOptionalFieldNameFieldName));
    }
    // endregion
}