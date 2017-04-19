package org.wheatgenetics.coordinate.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class OptionalField {
    // region Public Type
    public static class WrongClass extends java.lang.Exception { WrongClass() { super(); } }
    // endregion


    // region Private Constants
    private static final String NAME_JSON_NAME    = "field"  ;
    private static final String VALUE_JSON_NAME   = "value"  ;
    private static final String HINT_JSON_NAME    = "hint"   ;
    private static final String CHECKED_JSON_NAME = "checked";
    // endregion


    // region Protected Constant
    protected static final String DATE_HINT = "yyyy-mm-dd";
    // endregion


    // region Protected Fields
    protected String  name, value = "", hint = "";
    protected boolean checked = true;
    // endregion


    // region Protected Methods
    protected OptionalField() { super(); }

    protected void set(final JSONObject jsonObject) throws JSONException {
        assert jsonObject != null;
        this.setName   (jsonObject.optString(NAME_JSON_NAME )   );
        this.setValue  (jsonObject.optString(VALUE_JSON_NAME)   );
        this.setHint   (jsonObject.optString(HINT_JSON_NAME )   );
        this.setChecked(jsonObject.getBoolean(CHECKED_JSON_NAME));           // throws JSONException
    }

    protected void setName(final String name) {
        assert name          != null;
        assert name.length()  > 0   ;
        this.name = name;
    }

    protected void setHint(final String hint) {
        assert hint != null;
        this.hint = hint;
    }
    // endregion


    // region Public Methods
    // region Public Constructor Methods
    public OptionalField(final String name) {
        this();
        this.setName(name);
    }

    public OptionalField(final String name, final String hint) {
        this(name);
        this.setHint(hint);
    }

    public OptionalField(final String name, final String value, final String hint) {
        this(name, hint);
        this.setValue(value);
    }

    public OptionalField(final JSONObject jsonObject) throws JSONException, WrongClass {
        this();
        this.set(jsonObject);
        if (this.getHint().equals(DATE_HINT)) throw new WrongClass();
    }
    // endregion


    // region Public Overridden Method
    @Override
    public String toString() { return this.getName(); }
    // endregion


    // region Public Getter and Setter Methods
    public String getName() { return this.name; }

    public String getValue() { return this.value; }
    public void   setValue(final String value) {
        assert value != null;
        this.value = value;
    }

    public String getHint() { return this.hint; }

    public boolean getChecked()                      { return this.checked   ; }
    public void    setChecked(final boolean checked) { this.checked = checked; }
    // endregion


    public JSONObject makeJSONObject() throws JSONException {
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put(NAME_JSON_NAME   , this.getName()   );                // throws JSONException
        jsonObject.put(VALUE_JSON_NAME  , this.getValue()  );                // throws JSONException
        jsonObject.put(HINT_JSON_NAME   , this.getHint()   );                // throws JSONException
        jsonObject.put(CHECKED_JSON_NAME, this.getChecked());                // throws JSONException

        return jsonObject;
    }

    public boolean nameEqualsIgnoreCase(final String string) {
        final String name = this.getName();
        assert name != null;
        return name.equalsIgnoreCase(string);
    }
    // endregion
}