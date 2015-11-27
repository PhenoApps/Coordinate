package org.wheatgenetics.coordinate.objects;

public class Optional {
    public String field;
    public String value;
    public boolean checked;
    public String hint;

    public Optional(String field, String value, String hint, boolean checked) {
        this.field = field;
        this.value = value;
        this.hint = hint;
        this.checked = checked;
    }

    @Override
    public String toString() {
        return field;
    }
}
