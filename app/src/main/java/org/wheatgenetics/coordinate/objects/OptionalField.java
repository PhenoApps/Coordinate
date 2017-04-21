package org.wheatgenetics.coordinate.objects;

public class OptionalField extends java.lang.Object
{
    // region Package Type
    static class WrongClass extends java.lang.Exception { WrongClass() { super(); } }
    // endregion


    // region Private Constants
    private static final java.lang.String
        NAME_JSON_NAME = "field", VALUE_JSON_NAME   = "value"  ,
        HINT_JSON_NAME = "hint" , CHECKED_JSON_NAME = "checked";
    // endregion


    // region Protected Constant
    protected static final java.lang.String DATE_HINT = "yyyy-mm-dd";
    // endregion


    // region Protected Fields
    protected java.lang.String  name, value = "", hint = "";
    protected boolean           checked = true             ;
    // endregion


    // region Protected Methods
    protected OptionalField() { super(); }

    protected void set(final org.json.JSONObject jsonObject) throws org.json.JSONException
    {
        assert jsonObject != null;
        this.setName(jsonObject.optString(
            org.wheatgenetics.coordinate.objects.OptionalField.NAME_JSON_NAME));
        this.setValue(jsonObject.optString(
            org.wheatgenetics.coordinate.objects.OptionalField.VALUE_JSON_NAME));
        this.setHint(jsonObject.optString(
            org.wheatgenetics.coordinate.objects.OptionalField.HINT_JSON_NAME));
        this.setChecked(jsonObject.getBoolean(                                        // throws org.
            org.wheatgenetics.coordinate.objects.OptionalField.CHECKED_JSON_NAME));   //  json.JSON-
    }                                                                                  //  Exception

    protected void setName(final java.lang.String name)
    {
        assert name          != null;
        assert name.length()  > 0   ;
        this.name = name;
    }

    protected void setHint(final java.lang.String hint)
    {
        assert hint != null;
        this.hint = hint;
    }
    // endregion


    // region Package Methods
    // region Constructor Package Methods
    OptionalField(final java.lang.String name)
    {
        this();
        this.setName(name);
    }

    OptionalField(final java.lang.String name, final java.lang.String hint)
    {
        this(name);
        this.setHint(hint);
    }

    OptionalField(final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        this(name, hint);
        this.setValue(value);
    }

    OptionalField(final org.json.JSONObject jsonObject)
    throws org.json.JSONException, org.wheatgenetics.coordinate.objects.OptionalField.WrongClass
    {
        this();
        this.set(jsonObject);
        if (this.getHint().equals(org.wheatgenetics.coordinate.objects.OptionalField.DATE_HINT))
            throw new org.wheatgenetics.coordinate.objects.OptionalField.WrongClass();
    }
    // endregion


    org.json.JSONObject makeJSONObject() throws org.json.JSONException
    {
        final org.json.JSONObject jsonObject = new org.json.JSONObject();

        jsonObject.put(                                             // throws org.json.JSONException
            org.wheatgenetics.coordinate.objects.OptionalField.NAME_JSON_NAME, this.getName());
        jsonObject.put(                                             // throws org.json.JSONException
            org.wheatgenetics.coordinate.objects.OptionalField.VALUE_JSON_NAME, this.getValue());
        jsonObject.put(                                             // throws org.json.JSONException
            org.wheatgenetics.coordinate.objects.OptionalField.HINT_JSON_NAME, this.getHint());
        jsonObject.put(                                             // throws org.json.JSONException
            org.wheatgenetics.coordinate.objects.OptionalField.CHECKED_JSON_NAME,
            this.getChecked());

        return jsonObject;
    }
    // endregion


    // region Public Methods
    // region Overridden Public Method
    @Override
    public java.lang.String toString() { return this.getName(); }
    // endregion


    // region Getter and Setter Public Methods
    public java.lang.String getName() { return this.name; }

    public java.lang.String getValue() { return this.value; }
    public void             setValue(final java.lang.String value)
    {
        assert value != null;
        this.value = value;
    }

    public java.lang.String getHint() { return this.hint; }

    public boolean getChecked()                      { return this.checked   ; }
    public void    setChecked(final boolean checked) { this.checked = checked; }
    // endregion


    public boolean nameEqualsIgnoreCase(final java.lang.String string)
    {
        final java.lang.String name = this.getName();
        assert name != null;
        return name.equalsIgnoreCase(string);
    }
    // endregion
}