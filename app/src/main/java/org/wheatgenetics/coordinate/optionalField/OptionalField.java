package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.json.JSONException
 * org.json.JSONObject
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.BuildConfig
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public abstract class OptionalField extends java.lang.Object
{
    // region Constants
    // region Private Constants
    private static final java.lang.String
        NAME_JSON_NAME = "field", VALUE_JSON_NAME   = "value"  ,
        HINT_JSON_NAME = "hint" , CHECKED_JSON_NAME = "checked";
    // endregion

    static final java.lang.String DATE_HINT = "yyyy-mm-dd";
    // endregion

    // region Fields
    private java.lang.String name, value = "", hint = "";
    private boolean          checked = true             ;
    // endregion

    // region Private Methods
    private void setName(final java.lang.String name)
    {
        assert null != name;
        if (org.wheatgenetics.coordinate.BuildConfig.DEBUG && name.length() <= 0)
            throw new java.lang.AssertionError();
        this.name = name;
    }

    private void setHint(final java.lang.String hint)
    { this.hint = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(hint); }

    private boolean nameIsPerson() { return this.namesAreEqual("Person"); }
    // endregion

    // region Constructors
    OptionalField(final java.lang.String name) { super(); this.setName(name); }

    OptionalField(final java.lang.String name, final java.lang.String hint)
    { this(name); this.setHint(hint); }

    OptionalField(final org.json.JSONObject jsonObject) throws org.json.JSONException
    {
        super();

        assert null != jsonObject;
        this.setName(jsonObject.optString(
            org.wheatgenetics.coordinate.optionalField.OptionalField.NAME_JSON_NAME));
        this.setValue(jsonObject.optString(
            org.wheatgenetics.coordinate.optionalField.OptionalField.VALUE_JSON_NAME));
        this.setHint(jsonObject.optString(
            org.wheatgenetics.coordinate.optionalField.OptionalField.HINT_JSON_NAME));
        this.setChecked(jsonObject.getBoolean(                      // throws org.json.JSONException
            org.wheatgenetics.coordinate.optionalField.OptionalField.CHECKED_JSON_NAME));
    }
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
            if (o instanceof org.wheatgenetics.coordinate.optionalField.OptionalField)
            {
                final org.wheatgenetics.coordinate.optionalField.OptionalField f =
                    (org.wheatgenetics.coordinate.optionalField.OptionalField) o;
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

    org.json.JSONObject makeJSONObject() throws org.json.JSONException
    {
        final org.json.JSONObject result = new org.json.JSONObject();

        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.optionalField.OptionalField.NAME_JSON_NAME,
            this.getName()                                                         );
        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.optionalField.OptionalField.VALUE_JSON_NAME,
            this.getValue()                                                         );
        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.optionalField.OptionalField.HINT_JSON_NAME,
            this.getHint()                                                         );
        result.put(                                                 // throws org.json.JSONException
            org.wheatgenetics.coordinate.optionalField.OptionalField.CHECKED_JSON_NAME,
            this.getChecked()                                                         );

        return result;
    }
    // endregion

    // region Public Methods
    // region Getter and Setter Public Methods
    public java.lang.String getName() { return this.name; }

    public java.lang.String getValue() { return this.value; }
    public void             setValue(final java.lang.String value)
    { this.value = org.wheatgenetics.javalib.Utils.makeEmptyIfNull(value); }

    public java.lang.String getHint() { return this.hint; }

    public void setChecked(final boolean checked) { this.checked = checked; }
    // endregion

    public boolean isAPerson() { return this.nameIsPerson() || this.namesAreEqual("Name"); }
    // endregion
}