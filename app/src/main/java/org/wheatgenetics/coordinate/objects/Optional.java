package org.wheatgenetics.coordinate.objects;

public class Optional {
    public String  field, value, hint;
    public boolean checked = true;

    public Optional(final String field) {
        super();
        this.field = field;
        this.value = ""   ;
        this.hint  = ""   ;
    }

    public Optional(final String field, final String hint) {
        this(field);
        this.hint = hint;
    }

    public Optional(final String field, final String value, final String hint) {
        this(field, hint);
        this.value = value;
    }

    public Optional(final String field, final String value,
    final String hint, final boolean checked) {
        this(field, hint, value);
        this.checked = checked;
    }

    @Override
    public String toString() { return this.field; }
}