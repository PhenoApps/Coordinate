package org.wheatgenetics.coordinate.model;

/**
 * Uses:
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.model.BaseTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class BaseTemplateModelTest extends java.lang.Object
{
    /**
     * This class was defined in order to test BaseTemplateModel.  Why not just test
     * BaseTemplateModel directly?  Because BaseTemplateModel is abstract.  Why does that matter?  
     * Because I can't instantiate an abstract class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteBaseTemplateModel
    extends org.wheatgenetics.coordinate.model.BaseTemplateModel
    {
        private ConcreteBaseTemplateModel(
        @android.support.annotation.IntRange(from = 1) final long id, final java.lang.String title,
        final org.wheatgenetics.coordinate.model.TemplateType type,
        @android.support.annotation.IntRange(from = 1) final int rows                        ,
        @android.support.annotation.IntRange(from = 1) final int cols                        ,
        @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
        final boolean colNumbering, final boolean rowNumbering, final long timestamp)
        {
            super(id, title, type, rows, cols, generatedExcludedCellsAmount,
                colNumbering, rowNumbering, timestamp);
        }

        private ConcreteBaseTemplateModel(final java.lang.String title,
        final org.wheatgenetics.coordinate.model.TemplateType type,
        @android.support.annotation.IntRange(from = 1) final int rows                        ,
        @android.support.annotation.IntRange(from = 1) final int cols                        ,
        @android.support.annotation.IntRange(from = 0) final int generatedExcludedCellsAmount,
        final boolean colNumbering, final boolean rowNumbering, final long timestamp)
        {
            super(title, type, rows, cols, generatedExcludedCellsAmount,
                colNumbering, rowNumbering, timestamp);
        }
    }

    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void idCausesFirstConstructorFail()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */ 0                                                          ,
            /* title => */ "testTitle"                                                ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows  => */ 2                                                          ,
            /* cols  => */ 2                                                          ,
            /* generatedExcludedCellsAmount => */ 0    ,
            /* colNumbering                 => */ false,
            /* rowNumbering                 => */ true ,
            /* timestamp                    => */ 0    );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void rowsCausesFirstConstructorFail()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */ 5                                                          ,
            /* title => */ "testTitle"                                                ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows  => */ 0                                                          ,
            /* cols  => */ 2                                                          ,
            /* generatedExcludedCellsAmount => */ 0    ,
            /* colNumbering                 => */ false,
            /* rowNumbering                 => */ true ,
            /* timestamp                    => */ 0    );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void colsCausesFirstConstructorFail()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */ 5                                                          ,
            /* title => */ "testTitle"                                                ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows  => */  2                                                         ,
            /* cols  => */ -5                                                         ,
            /* generatedExcludedCellsAmount => */ 0    ,
            /* colNumbering                 => */ false,
            /* rowNumbering                 => */ false,
            /* timestamp                    => */ 0    );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void rowsAndColsCauseFirstConstructorFail()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */ 5                                                  ,
            /* title => */ "testTitle"                                        ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows  => */ -999                                               ,
            /* cols  => */    0                                               ,
            /* generatedExcludedCellsAmount => */ 0   ,
            /* colNumbering                 => */ true,
            /* rowNumbering                 => */ true,
            /* timestamp                    => */ 0    );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void generatedExcludedCellsAmountCausesFirstConstructorFail()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */ 5                                                          ,
            /* title => */ "testTitle"                                                ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows  => */ 2                                                          ,
            /* cols  => */ 2                                                          ,
            /* generatedExcludedCellsAmount => */ -90  ,
            /* colNumbering                 => */ false,
            /* rowNumbering                 => */ false,
            /* timestamp                    => */ 0    );
    }

    @org.junit.Test
    public void firstConstructorSucceeds()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */ 88                                                  ,
            /* title => */ "testTitle"                                         ,
            /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
            /* rows  => */ 99                                                  ,
            /* cols  => */  4                                                  ,
            /* generatedExcludedCellsAmount => */ 0    ,
            /* colNumbering                 => */ true ,
            /* rowNumbering                 => */ false,
            /* timestamp                    => */ 0    );
    }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void rowsCausesSecondConstructorFail()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* title        => */ "testTitle"                                                ,
            /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows         => */ 0                                                          ,
            /* cols         => */ 2                                                          ,
            /* generatedExcludedCellsAmount => */ 0    ,
            /* colNumbering                 => */ false,
            /* rowNumbering                 => */ true ,
            /* timestamp                    => */ 0    );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void colsCausesSecondConstructorFail()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* title        => */ "testTitle"                                                ,
            /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED,
            /* rows         => */  2                                                         ,
            /* cols         => */ -5                                                         ,
            /* generatedExcludedCellsAmount => */ 0    ,
            /* colNumbering                 => */ false,
            /* rowNumbering                 => */ false,
            /* timestamp                    => */ 0    );
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void rowsAndColsCauseSecondConstructorFail()
    {
        new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* title        => */ "testTitle"                                        ,
            /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
            /* rows         => */ -999                                               ,
            /* cols         => */    0                                               ,
            /* generatedExcludedCellsAmount => */ 0   ,
            /* colNumbering                 => */ true,
            /* rowNumbering                 => */ true,
            /* timestamp                    => */ 0    );
    }

    @org.junit.Test
    public void secondConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* title        => */ "testTitle"                                         ,
                    /* type         => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows         => */ 99                                                  ,
                    /* cols         => */  4                                                  ,
                    /* generatedExcludedCellsAmount => */ 0    ,
                    /* colNumbering                 => */ true ,
                    /* rowNumbering                 => */ false,
                    /* timestamp                    => */ 0    );
        org.junit.Assert.assertEquals(concreteBaseTemplateModel.getId(), 0);
    }
    // endregion
    // endregion

    // region Getter and Setter Public Method Tests
    @org.junit.Test
    public void getAndSetTitleSucceed()
    {
        final java.lang.String firstTestTitle = "firstTestTitle";
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                  ,
                    /* title => */ firstTestTitle                                     ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                    /* rows  => */  9                                                 ,
                    /* cols  => */ 20                                                 ,
                    /* generatedExcludedCellsAmount => */ 0    ,
                    /* colNumbering                 => */ false,
                    /* rowNumbering                 => */ true ,
                    /* timestamp                    => */ 0    );
        org.junit.Assert.assertEquals(concreteBaseTemplateModel.getTitle(), firstTestTitle);

        final java.lang.String secondTestTitle = "secondTestTitle";
        concreteBaseTemplateModel.setTitle(secondTestTitle);
        org.junit.Assert.assertNotEquals(concreteBaseTemplateModel.getTitle(), firstTestTitle );
        org.junit.Assert.assertEquals   (concreteBaseTemplateModel.getTitle(), secondTestTitle);
    }

    @org.junit.Test
    public void getAndSetTypeSucceed()
    {
        final org.wheatgenetics.coordinate.model.TemplateType firstTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.SEED;
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id                           => */ 5                ,
                    /* title                        => */ "testTitle"      ,
                    /* type                         => */ firstTemplateType,
                    /* rows                         => */  9               ,
                    /* cols                         => */ 20               ,
                    /* generatedExcludedCellsAmount => */  0               ,
                    /* colNumbering                 => */ false            ,
                    /* rowNumbering                 => */ true             ,
                    /* timestamp                    => */ 0                );
        org.junit.Assert.assertEquals(concreteBaseTemplateModel.getType(), firstTemplateType);

        final org.wheatgenetics.coordinate.model.TemplateType secondTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        concreteBaseTemplateModel.setType(secondTemplateType);
        org.junit.Assert.assertNotEquals(concreteBaseTemplateModel.getType(), firstTemplateType );
        org.junit.Assert.assertEquals   (concreteBaseTemplateModel.getType(), secondTemplateType);
    }

    @org.junit.Test
    public void getRowsAndGetColsSucceed()
    {
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new 
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */  9                                                  ,
                    /* cols  => */ 20                                                  ,
                    /* generatedExcludedCellsAmount => */ 0    ,
                    /* colNumbering                 => */ false,
                    /* rowNumbering                 => */ true ,
                    /* timestamp                    => */ 0    );
        org.junit.Assert.assertEquals(concreteBaseTemplateModel.getRows(),  9);
        org.junit.Assert.assertEquals(concreteBaseTemplateModel.getCols(), 20);
    }

    @org.junit.Test
    public void getAndSetGeneratedExcludedCellsAmountSucceed()
    {
        final int firstGeneratedExcludedCellsAmount = 1;
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */  9                                                  ,
                    /* cols  => */ 20                                                  ,
                    /* generatedExcludedCellsAmount => */ firstGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ false                            ,
                    /* rowNumbering                 => */ true                             ,
                    /* timestamp                    => */ 0                                );
        org.junit.Assert.assertEquals(firstGeneratedExcludedCellsAmount,
            concreteBaseTemplateModel.getGeneratedExcludedCellsAmount());

        final int secondGeneratedExcludedCellsAmount = 55;
        concreteBaseTemplateModel.setGeneratedExcludedCellsAmount(
            secondGeneratedExcludedCellsAmount);
        org.junit.Assert.assertNotEquals(firstGeneratedExcludedCellsAmount,
            concreteBaseTemplateModel.getGeneratedExcludedCellsAmount());
        org.junit.Assert.assertEquals(secondGeneratedExcludedCellsAmount,
            concreteBaseTemplateModel.getGeneratedExcludedCellsAmount());
    }

    @org.junit.Test(expected = java.lang.IllegalArgumentException.class)
    public void setGeneratedExcludedCellsAmountFails()
    {
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */  9                                                  ,
                    /* cols  => */ 20                                                  ,
                    /* generatedExcludedCellsAmount => */ 0    ,
                    /* colNumbering                 => */ false,
                    /* rowNumbering                 => */ true ,
                    /* timestamp                    => */ 0    );
        concreteBaseTemplateModel.setGeneratedExcludedCellsAmount(-7);
    }

    @org.junit.Test
    public void getAndSetColNumberingSucceed()
    {
        final boolean firstColNumbering = false;
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel 
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */  9                                                  ,
                    /* cols  => */ 20                                                  ,
                    /* generatedExcludedCellsAmount => */ 0                ,
                    /* colNumbering                 => */ firstColNumbering,
                    /* rowNumbering                 => */ true             ,
                    /* timestamp                    => */ 0                );
        org.junit.Assert.assertEquals(
            concreteBaseTemplateModel.getColNumbering(), firstColNumbering);

        final boolean secondColNumbering = true;
        concreteBaseTemplateModel.setColNumbering(secondColNumbering);
        org.junit.Assert.assertNotEquals(
            concreteBaseTemplateModel.getColNumbering(), firstColNumbering);
        org.junit.Assert.assertEquals(
            concreteBaseTemplateModel.getColNumbering(), secondColNumbering);
    }

    @org.junit.Test
    public void getAndSetRowNumberingSucceed()
    {
        final boolean firstRowNumbering = false;
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */  9                                                  ,
                    /* cols  => */ 20                                                  ,
                    /* generatedExcludedCellsAmount => */ 0                ,
                    /* colNumbering                 => */ false            ,
                    /* rowNumbering                 => */ firstRowNumbering,
                    /* timestamp                    => */ 0                );
        org.junit.Assert.assertEquals(
            concreteBaseTemplateModel.getRowNumbering(), firstRowNumbering);

        final boolean secondRowNumbering = true;
        concreteBaseTemplateModel.setRowNumbering(secondRowNumbering);
        org.junit.Assert.assertNotEquals(
            concreteBaseTemplateModel.getRowNumbering(), firstRowNumbering);
        org.junit.Assert.assertEquals(
            concreteBaseTemplateModel.getRowNumbering(), secondRowNumbering);
    }

    @org.junit.Test
    public void getTimestampSucceeds()
    {
        final long timestamp = 880;
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 10                                                  ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */ 5                                                   ,
                    /* cols  => */ 2                                                   ,
                    /* generatedExcludedCellsAmount => */ 0        ,
                    /* colNumbering                 => */ true     ,
                    /* rowNumbering                 => */ false    ,
                    /* timestamp                    => */ timestamp);
        org.junit.Assert.assertEquals(concreteBaseTemplateModel.getTimestamp(), timestamp);
    }
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringSucceeds()
    {
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */  9                                                  ,
                    /* cols  => */ 20                                                  ,
                    /* generatedExcludedCellsAmount => */ 9    ,
                    /* colNumbering                 => */ false,
                    /* rowNumbering                 => */ false,
                    /* timestamp                    => */ 0    );
        final java.lang.String expectedString =
            "BaseTemplateModel [id: 05, title=testTitle, type=0, rows=9, cols=20, gener" +
            "atedExcludedCellsAmount=9, colNumbering=false, rowNumbering=false, stamp=0]";
        org.junit.Assert.assertEquals(expectedString, concreteBaseTemplateModel.toString());
    }

    @org.junit.Test
    public void equalsAndHashCodeSucceedAndFail()
    {
        final long                                            testId           = 67         ;
        final java.lang.String                                testTitle        = "testTitle";
        final org.wheatgenetics.coordinate.model.TemplateType testTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        final int     testRows = 15, testCols = 1, testGeneratedExcludedCellsAmount = 0;
        final boolean testColNumbering = true, testRowNumbering = false                ;

        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            firstConcreteBasePartialTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id                           => */ testId                          ,
                    /* title                        => */ testTitle                       ,
                    /* type                         => */ testTemplateType                ,
                    /* rows                         => */ testRows                        ,
                    /* cols                         => */ testCols                        ,
                    /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ testColNumbering                ,
                    /* rowNumbering                 => */ testRowNumbering                ,
                    /* timestamp                    => */ 0                               ),
            secondConcreteBasePartialTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id                           => */ testId                          ,
                    /* title                        => */ testTitle                       ,
                    /* type                         => */ testTemplateType                ,
                    /* rows                         => */ testRows                        ,
                    /* cols                         => */ testCols                        ,
                    /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ testColNumbering                ,
                    /* rowNumbering                 => */ testRowNumbering                ,
                    /* timestamp                    => */ 0                               );

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

        secondConcreteBasePartialTemplateModel.setGeneratedExcludedCellsAmount(5);
        org.junit.Assert.assertFalse(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
        org.junit.Assert.assertNotEquals(
            firstConcreteBasePartialTemplateModel.hashCode (),
            secondConcreteBasePartialTemplateModel.hashCode());
        secondConcreteBasePartialTemplateModel.setGeneratedExcludedCellsAmount(
            testGeneratedExcludedCellsAmount);
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
        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */  9                                                  ,
                    /* cols  => */ 20                                                  ,
                    /* generatedExcludedCellsAmount => */ 0    ,
                    /* colNumbering                 => */ false,
                    /* rowNumbering                 => */ false,
                    /* timestamp                    => */ 0    );
        final java.lang.String expectedFormatString =
            "%s [id: 05, title=testTitle, type=0, rows=9, cols=20, generatedExc" +
            "ludedCellsAmount=0, colNumbering=false, rowNumbering=false, stamp=0";
        org.junit.Assert.assertEquals(expectedFormatString,
            concreteBaseTemplateModel.formatString());
    }

    @org.junit.Test
    public void packageAssignSucceedsAndFails()
    {
        final long                                            testId           = 67         ;
        final java.lang.String                                testTitle        = "testTitle";
        final org.wheatgenetics.coordinate.model.TemplateType testTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        final int     testRows = 15, testCols = 1, testGeneratedExcludedCellsAmount = 0;
        final boolean testColNumbering = true, testRowNumbering = false                ;
        final long    testTimestamp = 0                                                ;

        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            firstConcreteBasePartialTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                        /* id                           => */ testId                          ,
                        /* title                        => */ testTitle                       ,
                        /* type                         => */ testTemplateType                ,
                        /* rows                         => */ testRows                        ,
                        /* cols                         => */ testCols                        ,
                        /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                        /* colNumbering                 => */ testColNumbering                ,
                        /* rowNumbering                 => */ testRowNumbering                ,
                        /* timestamp                    => */ testTimestamp                   ),
            secondConcreteBasePartialTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                        /* id    => */ testId                                              ,
                        /* title => */ "different"                                         ,
                        /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                        /* rows  => */ 3                                                   ,
                        /* cols  => */ 9                                                   ,
                        /* generatedExcludedCellsAmount => */ 3            ,
                        /* colNumbering                 => */ false        ,
                        /* rowNumbering                 => */ true         ,
                        /* timestamp                    => */ testTimestamp);                // same

        org.junit.Assert.assertFalse(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));

        secondConcreteBasePartialTemplateModel.assign(
            /* title                        => */ testTitle                       ,
            /* type                         => */ testTemplateType                ,
            /* rows                         => */ testRows                        ,
            /* cols                         => */ testCols                        ,
            /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
            /* colNumbering                 => */ testColNumbering                ,
            /* rowNumbering                 => */ testRowNumbering                );
        org.junit.Assert.assertTrue(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
    }
    // endregion

    // region Other Public Method Tests
    @org.junit.Test
    public void publicAssignSucceeds()
    {
        final long                                            testId           = 67         ;
        final java.lang.String                                testTitle        = "testTitle";
        final org.wheatgenetics.coordinate.model.TemplateType testTemplateType =
            org.wheatgenetics.coordinate.model.TemplateType.USERDEFINED;
        final int     testRows = 15, testCols = 1, testGeneratedExcludedCellsAmount = 5;
        final boolean testColNumbering = true, testRowNumbering = false                ;
        final long    testTimestamp = 0;

        final org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            firstConcreteBasePartialTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id                           => */ testId                          ,
                    /* title                        => */ testTitle                       ,
                    /* type                         => */ testTemplateType                ,
                    /* rows                         => */ testRows                        ,
                    /* cols                         => */ testCols                        ,
                    /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ testColNumbering                ,
                    /* rowNumbering                 => */ testRowNumbering                ,
                    /* timestamp                    => */ testTimestamp                   ),
            secondConcreteBasePartialTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ testId                                             ,
                    /* title => */ "different"                                        ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.DNA,
                    /* rows  => */ 3                                                  ,
                    /* cols  => */ 9                                                  ,
                    /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ testColNumbering                ,
                    /* rowNumbering                 => */ testRowNumbering                ,
                    /* timestamp                    => */ testTimestamp                   ); // same

        org.junit.Assert.assertFalse(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));

        secondConcreteBasePartialTemplateModel.assign(
            /* title => */ testTitle,
            /* rows  => */ testRows ,
            /* cols  => */ testCols );
        org.junit.Assert.assertTrue(
            firstConcreteBasePartialTemplateModel.equals(secondConcreteBasePartialTemplateModel));
    }

    @org.junit.Test
    public void rowItemsSucceeds()
    {
        org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */  1                                                  ,
                    /* cols  => */ 20                                                  ,
                    /* generatedExcludedCellsAmount => */ 0    ,
                    /* colNumbering                 => */ false,
                    /* rowNumbering                 => */ false,
                    /* timestamp                    => */ 0    );
        org.junit.Assert.assertArrayEquals(concreteBaseTemplateModel.rowItems("Row"),
            org.wheatgenetics.javalib.Utils.stringArray("Row 1"));

        concreteBaseTemplateModel =
            new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                /* id    => */ 5                                                   ,
                /* title => */ "testTitle"                                         ,
                /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows  => */  3                                                  ,
                /* cols  => */ 20                                                  ,
                /* generatedExcludedCellsAmount => */ 0    ,
                /* colNumbering                 => */ false,
                /* rowNumbering                 => */ false,
                /* timestamp                    => */ 0    );
        org.junit.Assert.assertArrayEquals(concreteBaseTemplateModel.rowItems("Row"),
            new java.lang.String[] {"Row 1", "Row 2", "Row 3"} );
    }

    @org.junit.Test
    public void colItemsSucceeds()
    {
        org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ 5                                                   ,
                    /* title => */ "testTitle"                                         ,
                    /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                    /* rows  => */ 1                                                   ,
                    /* cols  => */ 1                                                   ,
                    /* generatedExcludedCellsAmount => */ 0    ,
                    /* colNumbering                 => */ false,
                    /* rowNumbering                 => */ false,
                    /* timestamp                    => */ 0    );
        org.junit.Assert.assertArrayEquals(concreteBaseTemplateModel.colItems("Column"),
            org.wheatgenetics.javalib.Utils.stringArray("Column 1"));

        concreteBaseTemplateModel =
            new org.wheatgenetics.coordinate.model.BaseTemplateModelTest.ConcreteBaseTemplateModel(
                /* id    => */ 5                                                   ,
                /* title => */ "testTitle"                                         ,
                /* type  => */ org.wheatgenetics.coordinate.model.TemplateType.SEED,
                /* rows  => */ 3                                                   ,
                /* cols  => */ 3                                                   ,
                /* generatedExcludedCellsAmount => */ 0    ,
                /* colNumbering                 => */ false,
                /* rowNumbering                 => */ false,
                /* timestamp                    => */ 0    );
        org.junit.Assert.assertArrayEquals(concreteBaseTemplateModel.colItems("Column"),
            new java.lang.String[] {"Column 1", "Column 2", "Column 3"} );
    }
    // endregion
}