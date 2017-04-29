package org.wheatgenetics.coordinate.model;

public class TemplateModel extends org.wheatgenetics.coordinate.model.Model
{
    private java.lang.String                                title;
    private org.wheatgenetics.coordinate.model.TemplateType type ;
    private int                                             rows ;
    private int                                             cols ;

    private java.lang.String excludeCells;
    private java.lang.String excludeRows ;
    private java.lang.String excludeCols ;

    private int colNumbering;
    private int rowNumbering;

    private java.lang.String optionalFields;

    private long timestamp;


//    public TemplateModel(final org.wheatgenetics.coordinate.model.TemplateModel templateModel) { super(); }

    public TemplateModel() { super(); }

    public TemplateModel(final long id)
    {
        this();
        this.setId(id);
    }

    public java.lang.String getTitle() { return this.title; }
}