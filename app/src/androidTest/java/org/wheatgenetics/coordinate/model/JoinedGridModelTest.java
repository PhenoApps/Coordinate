package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class JoinedGridModelTest extends java.lang.Object
{
    @org.junit.Test @android.annotation.SuppressLint("DefaultLocale")
    public void nameSucceeds()
    {
        final java.lang.String gridTitle = "testGridTitle", templateTitle = "testTemplateTitle";
        final int              rows      = 5              , cols          = 2                  ;
        final long             timestamp = 123                                                 ;

        final java.lang.String expectedName = java.lang.String.format(
            "Grid: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", gridTitle, templateTitle,
            cols, rows, org.wheatgenetics.androidlibrary.Utils.formatDate(timestamp));
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                => */ 5            ,
                /* title             => */ gridTitle    ,
                /* timestamp         => */ timestamp    ,
                /* templateId        => */ 6            ,
                /* templateTitle     => */ templateTitle,
                /* code              => */ 1            ,
                /* rows              => */ rows         ,
                /* cols              => */ cols         ,
                /* excludeCells      => */ null         ,
                /* excludeRows       => */ null         ,
                /* excludeCols       => */ null         ,
                /* colNumbering      => */ 1            ,
                /* rowNumbering      => */ 0            ,
                /* optionalFields    => */ null         ,
                /* templateTimestamp => */ 333          );
        org.junit.Assert.assertEquals(expectedName, joinedGridModel.name());
    }

    @org.junit.Test
    public void populateSucceeds()
    {
        final long             templateId            = 55555                                   ;
        final java.lang.String templateTitle = "testTemplateTitle"                             ;
        final int              code = 0, rows = 5, cols = 2, colNumbering = 1, rowNumbering = 0;
        final long             timestamp = 123                                                 ;

        final org.wheatgenetics.coordinate.model.TemplateModel
            expectedTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ templateId   ,
                /* title          => */ templateTitle,
                /* code           => */ code         ,
                /* rows           => */ rows         ,
                /* cols           => */ cols         ,
                /* excludeCells   => */ null         ,
                /* excludeRows    => */ null         ,
                /* excludeCols    => */ null         ,
                /* colNumbering   => */ colNumbering ,
                /* rowNumbering   => */ rowNumbering ,
                /* optionalFields => */ null         ,
                /* timestamp      => */ timestamp    ),
            actualTemplateModel = new org.wheatgenetics.coordinate.model.TemplateModel(
                /* id             => */ templateId ,
                /* title          => */ "different",
                /* code           => */ 1          ,
                /* rows           => */ 50         ,
                /* cols           => */ 20         ,
                /* excludeCells   => */ null       ,
                /* excludeRows    => */ null       ,
                /* excludeCols    => */ null       ,
                /* colNumbering   => */ 0          ,
                /* rowNumbering   => */ 1          ,
                /* optionalFields => */ null       ,
                /* timestamp      => */ timestamp  );
        org.junit.Assert.assertFalse(expectedTemplateModel.equals(actualTemplateModel));

        {
            final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
                new org.wheatgenetics.coordinate.model.JoinedGridModel(
                    /* id                => */ 5            ,
                    /* title             => */ "gridTitle"  ,
                    /* timestamp         => */ 456          ,
                    /* templateId        => */ templateId   ,
                    /* templateTitle     => */ templateTitle,
                    /* code              => */ code         ,
                    /* rows              => */ rows         ,
                    /* cols              => */ cols         ,
                    /* excludeCells      => */ null         ,
                    /* excludeRows       => */ null         ,
                    /* excludeCols       => */ null         ,
                    /* colNumbering      => */ colNumbering ,
                    /* rowNumbering      => */ rowNumbering ,
                    /* optionalFields    => */ null         ,
                    /* templateTimestamp => */ 333          );
            joinedGridModel.populate(actualTemplateModel);
        }
        org.junit.Assert.assertTrue(expectedTemplateModel.equals(actualTemplateModel));
    }
}