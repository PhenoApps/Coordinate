package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * android.annotation.SuppressLint
 *
 * org.json.JSONArray
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.androidlibrary.Utils
 *
 * org.wheatgenetics.coordinate.model.JoinedGridModel
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class JoinedGridModelTest extends java.lang.Object
{
    @org.junit.Test @android.annotation.SuppressLint("DefaultLocale")
    public void nameSucceeds()
    {
        final java.lang.String person    = "testPerson", title = "testTitle";
        final int              rows      = 5           , cols  = 2          ;
        final long             timestamp = 123                              ;

        final java.lang.String expectedName = java.lang.String.format(
            "Person: %s\n Template: %s\n Size: (%d, %d) Date: %s\n", person, title,
            cols, rows, org.wheatgenetics.androidlibrary.Utils.formatDate(timestamp));
        final org.wheatgenetics.coordinate.model.JoinedGridModel joinedGridModel =
            new org.wheatgenetics.coordinate.model.JoinedGridModel(
                /* id                           => */ 5            ,
                /* person                       => */ person       ,
                /* activeRow                    => */ 0            ,
                /* activeCol                    => */ 0            ,
                /* optionalFields               => */ null         ,
                /* timestamp                    => */ timestamp    ,

                /* templateId                   => */ 6            ,
                /* title                        => */ title        ,
                /* code                         => */ 1            ,
                /* rows                         => */ rows         ,
                /* cols                         => */ cols         ,
                /* generatedExcludedCellsAmount => */ 0            ,
                /* initialExcludedCells                => */ null         ,
                /* excludedRows                 => */ null         ,
                /* excludedCols                 => */ null         ,
                /* colNumbering                 => */ 1            ,
                /* rowNumbering                 => */ 0            ,
                /* templateOptionalFields       => */ null         ,
                /* templateTimestamp            => */ 333          ,

                /* entryModels                  => */ null         );
        org.junit.Assert.assertEquals(expectedName, joinedGridModel.name());
    }
}