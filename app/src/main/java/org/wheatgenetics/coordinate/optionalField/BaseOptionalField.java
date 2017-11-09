package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.wheatgenetics.javalib.Utils
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
abstract class BaseOptionalField extends java.lang.Object
{
    static final java.lang.String DATE_HINT = "yyyy-mm-dd";

    // region Fields
    private final java.lang.String name, hint    ;
    private       java.lang.String value   = ""  ;
    private       boolean          checked = true;
    // endregion

    // region Private Methods
    private static java.lang.String valid(final java.lang.String s)
    {
        assert null != s; if (s.length() <= 0) throw new java.lang.AssertionError();
        return s;
    }

    private boolean nameIsPerson() { return this.namesAreEqual("Person"); }
    // endregion

    // region Constructors
    BaseOptionalField(java.lang.String name, java.lang.String hint)
    {
        super();

        if (null != name) name = name.trim();
        this.name = org.wheatgenetics.coordinate.optionalField.BaseOptionalField.valid(name);

        if (null != hint) hint = hint.trim();
        this.hint = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(hint);
    }

    BaseOptionalField(final java.lang.String name) { this(name, ""); }
    // endregion

    // region Overridden Methods
    @java.lang.Override
    public java.lang.String toString()
    { return org.wheatgenetics.javalib.Utils.replaceIfNull(this.getName(), super.toString()); }

    @java.lang.Override
    public boolean equals(final java.lang.Object o)
    {
        if (null == o)
            return false;
        else
            if (o instanceof org.wheatgenetics.coordinate.optionalField.BaseOptionalField)
            {
                final org.wheatgenetics.coordinate.optionalField.BaseOptionalField f =
                    (org.wheatgenetics.coordinate.optionalField.BaseOptionalField) o;
                return
                    this.getName().equals(f.getName()) && this.getValue().equals(f.getValue()) &&
                    this.getHint().equals(f.getHint()) && this.getChecked() == f.getChecked()   ;
            }
            else return false;
    }

    /** Overridden just to elevate from protected to public. */
    @java.lang.Override
    public java.lang.Object clone() throws java.lang.CloneNotSupportedException
    { return super.clone(); }
    // endregion

    // region Package Methods
    boolean namesAreEqual(final java.lang.String name)
    { return this.getName().equalsIgnoreCase(name); }

    java.lang.String getSafeValue()
    { return this.nameIsPerson() ? this.getValue().replace(" ", "_") : this.getValue(); }

    boolean getChecked() { return this.checked; }
    // endregion

    // region Public Methods
    // region Getter and Setter Public Methods
    public java.lang.String getName() { return this.name; }

    public java.lang.String getValue() { return this.value; }
    public void             setValue(java.lang.String value)
    {
        if (null != value) value = value.trim();
        this.value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(value);
    }

    public java.lang.String getHint() { return this.hint; }

    public void setChecked(final boolean checked) { this.checked = checked; }
    // endregion

    public boolean isAPerson() { return this.nameIsPerson() || this.namesAreEqual("Name"); }
    // endregion

}