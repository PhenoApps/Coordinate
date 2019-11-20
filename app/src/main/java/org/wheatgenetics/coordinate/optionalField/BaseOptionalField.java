package org.wheatgenetics.coordinate.optionalField;

/**
 * BaseOptionalField and OptionalField used to be one class that used org.json.JSONException and
 * org.json.JSONObject.  The one class was split into two in order to do as much local unit testing
 * as possible (BaseOptionalFieldTest) and as little instrumented testing as possible
 * (OptionalFieldTest).  (BaseOptionalField does not use org.json.JSONException or
 * org.json.JSONObject while OptionalField does.)
 *
 * Uses:
 * android.support.annotation.NonNull
 *
 * org.wheatgenetics.javalib.Utils
 */
@java.lang.SuppressWarnings({"ClassExplicitlyExtendsObject"})
public abstract class BaseOptionalField extends java.lang.Object
{
    static final java.lang.String DATE_HINT = "yyyy-mm-dd";

    // region Fields
    @android.support.annotation.NonNull private final java.lang.String name, hint    ;
    @android.support.annotation.NonNull private       java.lang.String value   = ""  ;
                                        private       boolean          checked = true;
    // endregion

    // region Private Methods
    @android.support.annotation.NonNull
    private static java.lang.String valid(final java.lang.String name)
    {
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

    private boolean nameIsPerson() { return this.namesAreEqual("Person"); }
    // endregion

    // region Constructors
    BaseOptionalField(@android.support.annotation.NonNull final java.lang.String name,
    final java.lang.String hint)
    {
        super();

        this.name = org.wheatgenetics.coordinate.optionalField.BaseOptionalField.valid(name)       ;
        this.hint = org.wheatgenetics.javalib.Utils.makeEmptyIfNull                   (hint).trim();
    }

    BaseOptionalField(@android.support.annotation.NonNull final java.lang.String name)
    { this(name,""); }
    // endregion

    // region Overridden Methods
    @java.lang.Override @android.support.annotation.NonNull public java.lang.String toString()
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
    @java.lang.Override @android.support.annotation.NonNull public java.lang.Object clone()
    throws java.lang.CloneNotSupportedException { return super.clone(); }
    // endregion

    // region Package Methods
    boolean namesAreEqual(final java.lang.String name)
    { return this.getName().equalsIgnoreCase(name); }

    java.lang.String getSafeValue()
    {
        return this.nameIsPerson() ?
            this.getValue().replace(" ","_") : this.getValue();
    }

    boolean getChecked() { return this.checked; }
    // endregion

    // region Public Methods
    // region Getter and Setter Public Methods
    @android.support.annotation.NonNull public java.lang.String getName() { return this.name; }

    @android.support.annotation.NonNull public java.lang.String getValue() { return this.value; }

    public void setValue(final java.lang.String value)
    { this.value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(value).trim(); }

    @android.support.annotation.NonNull public java.lang.String getHint() { return this.hint; }

    public void setChecked(final boolean checked) { this.checked = checked; }
    // endregion

    public boolean isAPerson() { return this.nameIsPerson() || this.namesAreEqual("Name"); }
    // endregion
}