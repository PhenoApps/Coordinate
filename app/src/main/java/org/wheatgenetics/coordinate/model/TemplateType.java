package org.wheatgenetics.coordinate.model;

public enum TemplateType
{
    SEED(0), DNA(1), DEFAULT(2);

    private final int code;

    private TemplateType(final int code) { this.code = code; }

    static org.wheatgenetics.coordinate.model.TemplateType get(final int code)
    {
        if (code == org.wheatgenetics.coordinate.model.TemplateType.SEED.code)
            return org.wheatgenetics.coordinate.model.TemplateType.SEED;
        else
            if (code == org.wheatgenetics.coordinate.model.TemplateType.DNA.code)
                return org.wheatgenetics.coordinate.model.TemplateType.DNA;
            else
                if (code == org.wheatgenetics.coordinate.model.TemplateType.DEFAULT.code)
                    return org.wheatgenetics.coordinate.model.TemplateType.DEFAULT;
                else
                    throw new java.lang.IndexOutOfBoundsException();
    }

    public int getCode() { return this.code; }
}