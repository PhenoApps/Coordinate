package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.BasePartialTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class BasePartialTemplateModelTest extends java.lang.Object
{
    /**
     * This class was defined in order to test BasePartialTemplateModel.  Why not just test
     * BasePartialTemplateModel directly?  Because BasePartialTemplateModel is abstract.  Why does
     * that matter?  Because I can't instantiate an abstract class.  If I can't instantiate it I
     * can't test it.
     */
    private static class ConcreteBasePartialTemplateModel
    extends org.wheatgenetics.coordinate.model.BasePartialTemplateModel
    {
        private ConcreteBasePartialTemplateModel(final long id, final java.lang.String title,
        final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
        final boolean colNumbering, final boolean rowNumbering)
        { super(id, title, type, rows, cols, colNumbering, rowNumbering); }

        private ConcreteBasePartialTemplateModel(final java.lang.String title,
        final org.wheatgenetics.coordinate.model.TemplateType type, final int rows, final int cols,
        final boolean colNumbering, final boolean rowNumbering)
        { super(title, type, rows, cols, colNumbering, rowNumbering); }
    }

    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void firstConstructorRowsParameterCausesFail()
    {
        new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel(
                /* id           => */ 5                                                          ,
                /* title        => */ "testTitle"                                                ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
                /* rows         => */ 0                                                          ,
                /* cols         => */ 2                                                          ,
                /* colNumbering => */ false                                                      ,
                /* rowNumbering => */ true                                                       );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void firstConstructorColsParameterCausesFail()
    {
        new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel(
                /* id           => */ 5                                                          ,
                /* title        => */ "testTitle"                                                ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
                /* rows         => */ 2                                                          ,
                /* cols         => */ -5                                                         ,
                /* colNumbering => */ false                                                      ,
                /* rowNumbering => */ false                                                      );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void firstConstructorRowsAndColsParametersCauseFail()
    {
        new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel(
                /* id           => */ 5                                                  ,
                /* title        => */ "testTitle"                                        ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows         => */ -999                                               ,
                /* cols         => */ 0                                                  ,
                /* colNumbering => */ true                                               ,
                /* rowNumbering => */ true                                               );
    }

    @org.junit.Test
    public void firstConstructorSucceeds()
    {
        new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel(
                /* id           => */ 88                                                  ,
                /* title        => */ "testTitle"                                         ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows         => */ 99                                                  ,
                /* cols         => */ 4                                                   ,
                /* colNumbering => */ true                                                ,
                /* rowNumbering => */ false                                               );
    }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void secondConstructorRowsParameterCausesFail()
    {
        new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel(
                /* title        => */ "testTitle"                                                ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
                /* rows         => */ 0                                                          ,
                /* cols         => */ 2                                                          ,
                /* colNumbering => */ false                                                      ,
                /* rowNumbering => */ true                                                       );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void secondConstructorColsParameterCausesFail()
    {
        new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel(
                /* title        => */ "testTitle"                                                ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
                /* rows         => */ 2                                                          ,
                /* cols         => */ -5                                                         ,
                /* colNumbering => */ false                                                      ,
                /* rowNumbering => */ false                                                      );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void secondConstructorRowsAndColsParametersCauseFail()
    {
        new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel(
                /* title        => */ "testTitle"                                        ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                /* rows         => */ -999                                               ,
                /* cols         => */ 0                                                  ,
                /* colNumbering => */ true                                               ,
                /* rowNumbering => */ true                                               );
    }

    @org.junit.Test
    public void secondConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* title        => */ "testTitle"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 99                                                  ,
                        /* cols         => */ 4                                                   ,
                        /* colNumbering => */ true                                                ,
                        /* rowNumbering => */ false                                               );
        org.junit.Assert.assertEquals(concreteBasePartialTemplateModel.getId(), 0);
    }
    // endregion

    // region Getter and Setter Public Method Tests
    @org.junit.Test
    public void getAndSetTitleSucceed()
    {
        final java.lang.String firstTestTitle = "firstTestTitle";
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                                                  ,
                        /* title        => */ firstTestTitle                                     ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                        /* rows         => */ 9                                                  ,
                        /* cols         => */ 20                                                 ,
                        /* colNumbering => */ false                                              ,
                        /* rowNumbering => */ true                                               );
        org.junit.Assert.assertEquals(concreteBasePartialTemplateModel.getTitle(), firstTestTitle);

        final java.lang.String secondTestTitle = "secondTestTitle";
        concreteBasePartialTemplateModel.setTitle(secondTestTitle);
        org.junit.Assert.assertNotEquals(
            concreteBasePartialTemplateModel.getTitle(), firstTestTitle);
        org.junit.Assert.assertEquals(concreteBasePartialTemplateModel.getTitle(), secondTestTitle);
    }

    @org.junit.Test
    public void getAndSetTypeSucceed()
    {
        final org.wheatgenetics.coordinate.model.TemplateType firstTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.SEED;
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                ,
                        /* title        => */ "testTitle"      ,
                        /* type         => */ firstTemplateType,
                        /* rows         => */ 9                ,
                        /* cols         => */ 20               ,
                        /* colNumbering => */ false            ,
                        /* rowNumbering => */ true             );
        org.junit.Assert.assertEquals(
            concreteBasePartialTemplateModel.getType(), firstTemplateType);

        final org.wheatgenetics.coordinate.model.TemplateType secondTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        concreteBasePartialTemplateModel.setType(secondTemplateType);
        org.junit.Assert.assertNotEquals(
            concreteBasePartialTemplateModel.getType(), firstTemplateType);
        org.junit.Assert.assertEquals(
            concreteBasePartialTemplateModel.getType(), secondTemplateType);
    }

    @org.junit.Test
    public void getRowsAndGetColsSucceed()
    {
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                                                   ,
                        /* title        => */ "testTitle"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 9                                                   ,
                        /* cols         => */ 20                                                  ,
                        /* colNumbering => */ false                                               ,
                        /* rowNumbering => */ true                                                );
        org.junit.Assert.assertEquals(concreteBasePartialTemplateModel.getRows(),  9);
        org.junit.Assert.assertEquals(concreteBasePartialTemplateModel.getCols(), 20);
    }

    @org.junit.Test
    public void getAndSetColNumberingSucceed()
    {
        final boolean firstColNumbering = false;
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                                                   ,
                        /* title        => */ "testTitle"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 9                                                   ,
                        /* cols         => */ 20                                                  ,
                        /* colNumbering => */ firstColNumbering                                   ,
                        /* rowNumbering => */ true                                                );
        org.junit.Assert.assertEquals(
            concreteBasePartialTemplateModel.getColNumbering(), firstColNumbering);

        final boolean secondColNumbering = true;
        concreteBasePartialTemplateModel.setColNumbering(secondColNumbering);
        org.junit.Assert.assertNotEquals(
            concreteBasePartialTemplateModel.getColNumbering(), firstColNumbering);
        org.junit.Assert.assertEquals(
            concreteBasePartialTemplateModel.getColNumbering(), secondColNumbering);
    }

    @org.junit.Test
    public void getAndSetRowNumberingSucceed()
    {
        final boolean firstRowNumbering = false;
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                                                   ,
                        /* title        => */ "testTitle"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 9                                                   ,
                        /* cols         => */ 20                                                  ,
                        /* colNumbering => */ false                                               ,
                        /* rowNumbering => */ firstRowNumbering                                   );
        org.junit.Assert.assertEquals(
            concreteBasePartialTemplateModel.getRowNumbering(), firstRowNumbering);

        final boolean secondRowNumbering = true;
        concreteBasePartialTemplateModel.setRowNumbering(secondRowNumbering);
        org.junit.Assert.assertNotEquals(
            concreteBasePartialTemplateModel.getRowNumbering(), firstRowNumbering);
        org.junit.Assert.assertEquals(
            concreteBasePartialTemplateModel.getRowNumbering(), secondRowNumbering);
    }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringSucceeds()
    {
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                                                   ,
                        /* title        => */ "testTitle"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 9                                                   ,
                        /* cols         => */ 20                                                  ,
                        /* colNumbering => */ false                                               ,
                        /* rowNumbering => */ false                                               );
        final java.lang.String expectedString = "BasePartialTemplateModel [id: 05, " +
            "title=testTitle, type=0, rows=9, cols=20, colNumbering=false, rowNumbering=false]";
        org.junit.Assert.assertEquals(concreteBasePartialTemplateModel.toString(), expectedString);
    }

    @org.junit.Test
    public void equalsAndHashCodeSucceedAndFail()
    {
        final long                                            testId           = 67         ;
        final java.lang.String                                testTitle        = "testTitle";
        final org.wheatgenetics.coordinate.model.TemplateType testTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        final int     testRows         = 15  , testCols         = 1    ;
        final boolean testColNumbering = true, testRowNumbering = false;

        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel firstConcreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ testId          ,
                        /* title        => */ testTitle       ,
                        /* type         => */ testTemplateType,
                        /* rows         => */ testRows        ,
                        /* cols         => */ testCols        ,
                        /* colNumbering => */ testColNumbering,
                        /* rowNumbering => */ testRowNumbering);
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel secondConcreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ testId          ,
                        /* title        => */ testTitle       ,
                        /* type         => */ testTemplateType,
                        /* rows         => */ testRows        ,
                        /* cols         => */ testCols        ,
                        /* colNumbering => */ testColNumbering,
                        /* rowNumbering => */ testRowNumbering);

        org.junit.Assert.assertTrue(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());

        secondConcreteBasePartialTemplateModel.setTitle("different");
        org.junit.Assert.assertFalse(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());
        secondConcreteBasePartialTemplateModel.setTitle(testTitle);
        org.junit.Assert.assertTrue(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());

        secondConcreteBasePartialTemplateModel.setType(
            org.wheatgenetics.coordinate.model.TemplateType.SEED);
        org.junit.Assert.assertFalse(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());
        secondConcreteBasePartialTemplateModel.setType(testTemplateType);
        org.junit.Assert.assertTrue(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());

        secondConcreteBasePartialTemplateModel.setColNumbering(false);
        org.junit.Assert.assertFalse(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());
        secondConcreteBasePartialTemplateModel.setColNumbering(testColNumbering);
        org.junit.Assert.assertTrue(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());

        secondConcreteBasePartialTemplateModel.setRowNumbering(true);
        org.junit.Assert.assertFalse(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());
        secondConcreteBasePartialTemplateModel.setRowNumbering(testRowNumbering);
        org.junit.Assert.assertTrue(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());
    }
    // endregion

    // region Package Method Tests
    @org.junit.Test
    public void formatStringSucceeds()
    {
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                                                   ,
                        /* title        => */ "testTitle"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 9                                                   ,
                        /* cols         => */ 20                                                  ,
                        /* colNumbering => */ false                                               ,
                        /* rowNumbering => */ false                                               );
        final java.lang.String expectedFormatString = "%s [id: 05, " +
            "title=testTitle, type=0, rows=9, cols=20, colNumbering=false, rowNumbering=false";
        org.junit.Assert.assertEquals(
            concreteBasePartialTemplateModel.formatString(), expectedFormatString);
    }

    @org.junit.Test
    public void assignSucceedsAndFails()
    {
        final long                                            testId           = 67         ;
        final java.lang.String                                testTitle        = "testTitle";
        final org.wheatgenetics.coordinate.model.TemplateType testTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        final int     testRows         = 15  , testCols         = 1    ;
        final boolean testColNumbering = true, testRowNumbering = false;

        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel firstConcreteBasePartialTemplateModel =
            new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                .ConcreteBasePartialTemplateModel(
                        /* id           => */ testId          ,
                        /* title        => */ testTitle       ,
                        /* type         => */ testTemplateType,
                        /* rows         => */ testRows        ,
                        /* cols         => */ testCols        ,
                        /* colNumbering => */ testColNumbering,
                        /* rowNumbering => */ testRowNumbering);
        final org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel secondConcreteBasePartialTemplateModel =
            new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                .ConcreteBasePartialTemplateModel(
                        /* id           => */ testId                                              ,
                        /* title        => */ "different"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 3                                                   ,
                        /* cols         => */ 9                                                   ,
                        /* colNumbering => */ false                                               ,
                        /* rowNumbering => */ true                                                );

        org.junit.Assert.assertFalse(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));

        secondConcreteBasePartialTemplateModel.assign(
            /* title        => */ testTitle       ,
            /* type         => */ testTemplateType,
            /* rows         => */ testRows        ,
            /* cols         => */ testCols        ,
            /* colNumbering => */ testColNumbering,
            /* rowNumbering => */ testRowNumbering);
        org.junit.Assert.assertTrue(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
    }
    // endregion

    // region Other Public Method Tests
    @org.junit.Test
    public void rowItemsSucceeds()
    {
        org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
                new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                    .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                                                   ,
                        /* title        => */ "testTitle"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 1                                                   ,
                        /* cols         => */ 20                                                  ,
                        /* colNumbering => */ false                                               ,
                        /* rowNumbering => */ false                                               );
        org.junit.Assert.assertArrayEquals(concreteBasePartialTemplateModel.rowItems("Row"),
            org.wheatgenetics.javalib.Utils.stringArray("Row 1"));

        concreteBasePartialTemplateModel = new org.wheatgenetics.coordinate.model
            .BasePartialTemplateModelTest.ConcreteBasePartialTemplateModel(
                /* id           => */ 5                                                   ,
                /* title        => */ "testTitle"                                         ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows         => */ 3                                                   ,
                /* cols         => */ 20                                                  ,
                /* colNumbering => */ false                                               ,
                /* rowNumbering => */ false                                               );
        org.junit.Assert.assertArrayEquals(concreteBasePartialTemplateModel.rowItems("Row"),
            new java.lang.String[] {"Row 1", "Row 2", "Row 3"} );
    }

    @org.junit.Test
    public void colItemsSucceeds()
    {
        org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
            .ConcreteBasePartialTemplateModel concreteBasePartialTemplateModel =
            new org.wheatgenetics.coordinate.model.BasePartialTemplateModelTest
                .ConcreteBasePartialTemplateModel(
                        /* id           => */ 5                                                   ,
                        /* title        => */ "testTitle"                                         ,
                        /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows         => */ 1                                                   ,
                        /* cols         => */ 1                                                   ,
                        /* colNumbering => */ false                                               ,
                        /* rowNumbering => */ false                                               );
        org.junit.Assert.assertArrayEquals(concreteBasePartialTemplateModel.colItems("Column"),
            org.wheatgenetics.javalib.Utils.stringArray("Column 1"));

        concreteBasePartialTemplateModel = new org.wheatgenetics.coordinate.model
            .BasePartialTemplateModelTest.ConcreteBasePartialTemplateModel(
                /* id           => */ 5                                                   ,
                /* title        => */ "testTitle"                                         ,
                /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows         => */ 3                                                   ,
                /* cols         => */ 3                                                   ,
                /* colNumbering => */ false                                               ,
                /* rowNumbering => */ false                                               );
        org.junit.Assert.assertArrayEquals(concreteBasePartialTemplateModel.colItems("Column"),
            new java.lang.String[] {"Column 1", "Column 2", "Column 3"} );
    }
    // endregion
}