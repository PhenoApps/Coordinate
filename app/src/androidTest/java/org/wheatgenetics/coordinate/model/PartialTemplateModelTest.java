package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.TemplateType
 *
 * org.wheatgenetics.coordinate.model.PartialTemplateModel
 *
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class PartialTemplateModelTest extends java.lang.Object
{
    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidIdFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* id             => */ 0          ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 5          ,
            /* cols           => */ 2          ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidCodeFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 3          ,
            /* rows           => */ 5          ,
            /* cols           => */ 2          ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowsFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 0          ,
            /* cols           => */ 2          ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColsFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */  1         ,
            /* rows           => */  5         ,
            /* cols           => */ -2         ,
            /* colNumbering   => */  1         ,
            /* rowNumbering   => */  0         ,
            /* optionalFields => */ null       );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColNumberingFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 5          ,
            /* cols           => */ 2          ,
            /* colNumbering   => */ 3          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowNumberingFirstConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 30         ,
            /* cols           => */ 2          ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 56         ,
            /* optionalFields => */ null       );
    }

    @org.junit.Test
    public void firstConstructorSucceeds()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* id             => */ 5          ,
            /* title          => */ "testTitle",
            /* code           => */ 1          ,
            /* rows           => */ 5          ,
            /* cols           => */ 2          ,
            /* colNumbering   => */ 1          ,
            /* rowNumbering   => */ 0          ,
            /* optionalFields => */ null       );
    }
    // endregion

    // region Third Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidRowsThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 0                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ null                                               );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void invalidColsThirdConstructorFails()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */  5                                                 ,
            /* cols           => */ -2                                                 ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ null                                               );
    }

    @org.junit.Test
    public void thirdConstructorSucceeds()
    {
        new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ null                                               );
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void formatStringSucceeds()
    {
        final java.lang.String expectedFormatString = "%s [id: 99, title=testTitle, " +
            "type=1, rows=5, cols=2, colNumbering=true, rowNumbering=false, options=";
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel;
        {
            final long id = 99;
            partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* id             => */ id         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       );
            org.junit.Assert.assertEquals(expectedFormatString,
                partialTemplateModel.formatString());

            partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
            partialTemplateModel.setId(id);
        }
        org.junit.Assert.assertEquals(expectedFormatString, partialTemplateModel.formatString());
    }

    @org.junit.Test
    public void toStringSucceeds()
    {
        final java.lang.String expectedString = "PartialTemplateModel [id: 09, title=testTitle, " +
            "type=1, rows=5, cols=2, colNumbering=true, rowNumbering=false, options=]";
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel;
        {
            final long id = 9;
            partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* id             => */ id         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ ""         );
            org.junit.Assert.assertEquals(expectedString, partialTemplateModel.toString());

            partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
            partialTemplateModel.setId(id);
        }
        org.junit.Assert.assertEquals(expectedString, partialTemplateModel.toString());
    }

    @org.junit.Test
    public void equalsSucceedsAndFails()
    {
        final org.wheatgenetics.coordinate.model.PartialTemplateModel firstPartialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        {
            final org.wheatgenetics.coordinate.model.PartialTemplateModel
                secondPartialTemplateModel =
                    new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                        /* title          => */ "testTitle"                                        ,
                        /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                        /* rows           => */ 5    ,
                        /* cols           => */ 2    ,
                        /* colNumbering   => */ true ,
                        /* rowNumbering   => */ false,
                        /* optionalFields => */ null );
            org.junit.Assert.assertTrue(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));

            secondPartialTemplateModel.setTitle("different");
            org.junit.Assert.assertFalse(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));
            secondPartialTemplateModel.setTitle("testTitle");
            org.junit.Assert.assertTrue(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));

            secondPartialTemplateModel.setType(
                org.wheatgenetics.coordinate.model.TemplateType.SEED);
            org.junit.Assert.assertFalse(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));
            secondPartialTemplateModel.setType(org.wheatgenetics.coordinate.model.TemplateType.DNA);
            org.junit.Assert.assertTrue(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));

            secondPartialTemplateModel.setColNumbering(false);
            org.junit.Assert.assertFalse(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));
            secondPartialTemplateModel.setColNumbering(true);
            org.junit.Assert.assertTrue(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));

            secondPartialTemplateModel.setRowNumbering(true);
            org.junit.Assert.assertFalse(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));
            secondPartialTemplateModel.setRowNumbering(false);
            org.junit.Assert.assertTrue(
                firstPartialTemplateModel.equals(secondPartialTemplateModel));
        }

        final long id = 6;
        firstPartialTemplateModel.setId(id);
        org.wheatgenetics.coordinate.model.PartialTemplateModel thirdPartialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* id             => */ id         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       );
        org.junit.Assert.assertTrue(firstPartialTemplateModel.equals(thirdPartialTemplateModel));

        thirdPartialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* id             => */ id         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ ""         );
        org.junit.Assert.assertTrue(firstPartialTemplateModel.equals(thirdPartialTemplateModel));

        thirdPartialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* id             => */ id         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ "   "      );
        org.junit.Assert.assertTrue(firstPartialTemplateModel.equals(thirdPartialTemplateModel));
    }

    @org.junit.Test
    public void hashCodeSucceeds()
    {
        final org.wheatgenetics.coordinate.model.PartialTemplateModel firstPartialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        {
            final org.wheatgenetics.coordinate.model.PartialTemplateModel
                secondPartialTemplateModel =
                    new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                        /* title          => */ "testTitle"                                        ,
                        /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                        /* rows           => */ 5                                                  ,
                        /* cols           => */ 2                                                  ,
                        /* colNumbering   => */ true                                               ,
                        /* rowNumbering   => */ false                                              ,
                        /* optionalFields => */ null                                              );
            org.junit.Assert.assertEquals(
                firstPartialTemplateModel.hashCode(), secondPartialTemplateModel.hashCode());
        }

        final long id = 11111;
        firstPartialTemplateModel.setId(id);
        final org.wheatgenetics.coordinate.model.PartialTemplateModel thirdPartialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* id             => */ id         ,
                /* title          => */ "testTitle",
                /* code           => */ 1          ,
                /* rows           => */ 5          ,
                /* cols           => */ 2          ,
                /* colNumbering   => */ 1          ,
                /* rowNumbering   => */ 0          ,
                /* optionalFields => */ null       );
        org.junit.Assert.assertEquals(
            firstPartialTemplateModel.hashCode(), thirdPartialTemplateModel.hashCode());
    }

    @org.junit.Test
    public void cloneSucceeds()
    {
        final org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        final org.wheatgenetics.coordinate.model.PartialTemplateModel clonedPartialTemplateModel =
            (org.wheatgenetics.coordinate.model.PartialTemplateModel)
                partialTemplateModel.clone();
        org.junit.Assert.assertTrue(partialTemplateModel.equals(clonedPartialTemplateModel));
    }
    // endregion

    // region Package Methods Tests
    @org.junit.Test
    public void assignSucceedsAndFails()
    {
        final java.lang.String                                testTitle        = "testTitle";
        final org.wheatgenetics.coordinate.model.TemplateType testTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        final int     testRows         = 15  , testCols         = 1    ;
        final boolean testColNumbering = true, testRowNumbering = false;

        final org.wheatgenetics.coordinate.model.PartialTemplateModel firstPartialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ testTitle       ,
                /* type           => */ testTemplateType,
                /* rows           => */ testRows        ,
                /* cols           => */ testCols        ,
                /* colNumbering   => */ testColNumbering,
                /* rowNumbering   => */ testRowNumbering,
                /* optionalFields => */ null            );
        final org.wheatgenetics.coordinate.model.PartialTemplateModel secondPartialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ testTitle       ,
                /* type           => */ testTemplateType,
                /* rows           => */ testRows        ,
                /* cols           => */ testCols        ,
                /* colNumbering   => */ testColNumbering,
                /* rowNumbering   => */ testRowNumbering,
                /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
                    .NonNullOptionalFieldsTest.makeNonNullOptionalFields());

        org.junit.Assert.assertFalse(firstPartialTemplateModel.equals(secondPartialTemplateModel));

        secondPartialTemplateModel.assign(firstPartialTemplateModel);
        org.junit.Assert.assertTrue(firstPartialTemplateModel.equals(secondPartialTemplateModel));
    }

    @org.junit.Test
    public void makeOptionalFieldsNew()
    {
        final org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        final org.wheatgenetics.coordinate.model.PartialTemplateModel clonedPartialTemplateModel =
            (org.wheatgenetics.coordinate.model.PartialTemplateModel) partialTemplateModel.clone();
        org.junit.Assert.assertTrue(partialTemplateModel.equals(clonedPartialTemplateModel));

        assert null != clonedPartialTemplateModel;
        clonedPartialTemplateModel.makeOptionalFieldsNew();
        org.junit.Assert.assertFalse(partialTemplateModel.equals(clonedPartialTemplateModel));
    }

    @org.junit.Test
    public void firstOptionalFieldValuesSucceeds()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertNull(partialTemplateModel.optionalFieldValues());

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertArrayEquals(partialTemplateModel.optionalFieldValues(),
            org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields().values());
    }

    @org.junit.Test
    public void secondOptionalFieldValuesSucceeds()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        final java.lang.String[] name1 = org.wheatgenetics.javalib.Utils.stringArray("Name1");
        org.junit.Assert.assertNull(partialTemplateModel.optionalFieldValues(name1));

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertArrayEquals(partialTemplateModel.optionalFieldValues(name1),
            org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields().values(name1));
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test
    public void nonNullOptionalFieldsSucceeds()
    {
        final org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertNull(partialTemplateModel.optionalFields());

        partialTemplateModel.addOptionalField("name", "value");
        org.junit.Assert.assertNotNull(partialTemplateModel.optionalFields());
    }

    @org.junit.Test
    public void getOptionalFieldsSucceeds()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertNull(partialTemplateModel.optionalFieldsAsJson());

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertEquals(partialTemplateModel.optionalFieldsAsJson(),
            org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields().toJson());
    }

    @org.junit.Test
    public void optionalFieldsCloneSucceeds()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertNull(partialTemplateModel.optionalFieldsClone());

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertEquals(partialTemplateModel.optionalFieldsClone(),
            org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
    }

    @org.junit.Test
    public void optionalFieldsIsEmptySucceeds()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertTrue(partialTemplateModel.optionalFieldsIsEmpty());

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertFalse(partialTemplateModel.optionalFieldsIsEmpty());
    }

    @org.junit.Test
    public void optionalFieldNamesSucceeds()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertNull(partialTemplateModel.optionalFieldNames());

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertArrayEquals(partialTemplateModel.optionalFieldNames(),
            org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields().names());
    }

    @org.junit.Test
    public void optionalFieldChecks()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertNull(partialTemplateModel.optionalFieldChecks());

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
               .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertArrayEquals(partialTemplateModel.optionalFieldChecks(),
            org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields().checks());
    }

    @org.junit.Test
    public void addOptionalFieldSucceeds()
    {
        final org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        final java.lang.String testValue = "Val U";
        partialTemplateModel.addOptionalField("name", testValue);
        org.junit.Assert.assertArrayEquals(partialTemplateModel.optionalFieldValues(),
            org.wheatgenetics.javalib.Utils.stringArray(testValue));
    }

    @org.junit.Test
    public void getFirstOptionalFieldValueSucceeds()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertNull(partialTemplateModel.getFirstOptionalFieldValue());

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
            .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertEquals(partialTemplateModel.getFirstOptionalFieldValue(),
            org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields().getFirstValue());
    }

    @org.junit.Test
    public void getFirstOptionalFieldDatedValueSucceeds()
    {
        org.wheatgenetics.coordinate.model.PartialTemplateModel partialTemplateModel =
            new org.wheatgenetics.coordinate.model.PartialTemplateModel(
                /* title          => */ "testTitle"                                        ,
                /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows           => */ 5                                                  ,
                /* cols           => */ 2                                                  ,
                /* colNumbering   => */ true                                               ,
                /* rowNumbering   => */ false                                              ,
                /* optionalFields => */ null                                               );
        org.junit.Assert.assertNull(partialTemplateModel.getFirstOptionalFieldDatedValue());

        partialTemplateModel = new org.wheatgenetics.coordinate.model.PartialTemplateModel(
            /* title          => */ "testTitle"                                        ,
            /* type           => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows           => */ 5                                                  ,
            /* cols           => */ 2                                                  ,
            /* colNumbering   => */ true                                               ,
            /* rowNumbering   => */ false                                              ,
            /* optionalFields => */ org.wheatgenetics.coordinate.optionalField
            .NonNullOptionalFieldsTest.makeNonNullOptionalFields());
        org.junit.Assert.assertEquals(partialTemplateModel.getFirstOptionalFieldDatedValue(),
            org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields().getDatedFirstValue());
    }
    // endregion
}