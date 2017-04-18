package org.wheatgenetics.coordinate.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class OptionalField {
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
        this.setField(jsonObject.optString("field", ""));
        this.setValue(jsonObject.optString("value", ""));
        this.setHint (jsonObject.optString("hint" , ""));
        this.checked = jsonObject.getBoolean("checked");                     // throws JSONException
    }
    // endregion


    // region Public Overridden Methods
    @Override
    public String toString() { return this.field; }
    // endregion


    public JSONObject makeJSONObject() throws JSONException {
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put("field"  , this.field  );                             // throws JSONException
        jsonObject.put("value"  , this.value  );                             // throws JSONException
        jsonObject.put("hint"   , this.hint   );                             // throws JSONException
        jsonObject.put("checked", this.checked);                             // throws JSONException

        return jsonObject;
    }
    // endregion
}