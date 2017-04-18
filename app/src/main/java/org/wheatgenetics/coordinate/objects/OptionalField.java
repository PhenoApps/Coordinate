package org.wheatgenetics.coordinate.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class OptionalField {
    // region Private Constants
    private static final String FIELD_JSON_NAME   = "field"  ;
    private static final String VALUE_JSON_NAME   = "value"  ;
    private static final String HINT_JSON_NAME    = "hint"   ;
    private static final String CHECKED_JSON_NAME = "checked";
    // endregion


    // region Public Fields
    public String  field, value = "", hint = "";
    public boolean checked = true;
    // endregion


    // region Protected Methods
    protected void setField(final String field) {
        assert field          != null;
        assert field.length()  > 0   ;
        this.field = field;
    }

    protected void setValue(final String value) {
        assert value != null;
        this.value = value;
    }

    protected void setHint(final String hint) {
        assert hint != null;
        this.hint = hint;
    }
    // endregion


    // region Public Methods
    // region Public Constructor Methods
    public OptionalField(final String field) {
        super();
        this.setField(field);
    }

    public OptionalField(final String field, final String hint) {
        this(field);
        this.setHint(hint);
    }

    public OptionalField(final String field, final String value, final String hint) {
        this(field, hint);
        this.setValue(value);
    }

    public OptionalField(final JSONObject jsonObject) throws JSONException {
        super();

        assert jsonObject != null;
        this.setField(jsonObject.optString(FIELD_JSON_NAME));
        this.setValue(jsonObject.optString(VALUE_JSON_NAME));
        this.setHint (jsonObject.optString(HINT_JSON_NAME ));
        this.checked = jsonObject.getBoolean(CHECKED_JSON_NAME);             // throws JSONException
    }
    // endregion


    // region Public Overridden Methods
    @Override
    public String toString() { return this.field; }
    // endregion


    public JSONObject makeJSONObject() throws JSONException {
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put(FIELD_JSON_NAME  , this.field  );                     // throws JSONException
        jsonObject.put(VALUE_JSON_NAME  , this.value  );                     // throws JSONException
        jsonObject.put(HINT_JSON_NAME   , this.hint   );                     // throws JSONException
        jsonObject.put(CHECKED_JSON_NAME, this.checked);                     // throws JSONException

        return jsonObject;
    }
    // endregion
}