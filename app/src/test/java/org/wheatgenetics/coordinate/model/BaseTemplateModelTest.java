package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.javalib.Utils;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.BaseTemplateModel
 * org.wheatgenetics.coordinate.model.TemplateType
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class BaseTemplateModelTest
extends Object implements StringGetter
{
    /**
     * This class was defined in order to test BaseTemplateModel.  Why not just test
     * BaseTemplateModel directly?  Because BaseTemplateModel is abstract.  Why does that matter?  
     * Because I can't instantiate an abstract class.  If I can't instantiate it I can't test it.
     */
    private static class ConcreteBaseTemplateModel
    extends BaseTemplateModel
    {
        private ConcreteBaseTemplateModel(
                @IntRange(from = 1) final long id, final String title,
                final TemplateType type,
                @IntRange(from = 1) final int rows                        ,
                @IntRange(from = 1) final int cols                        ,
                @IntRange(from = 0) final int generatedExcludedCellsAmount,
                final boolean colNumbering, final boolean rowNumbering, final long timestamp,
                @NonNull final StringGetter stringGetter)
        {
            super(id, title, type, rows, cols, generatedExcludedCellsAmount,
                colNumbering, rowNumbering, timestamp, stringGetter);
        }

        private ConcreteBaseTemplateModel(
        @SuppressWarnings({"SameParameterValue"}) final String title,
        final TemplateType type,
        @IntRange(from = 1) final int rows,
        @IntRange(from = 1) final int cols,
        @SuppressWarnings({"SameParameterValue"}) @IntRange(from = 0)
            final int generatedExcludedCellsAmount,
        final boolean colNumbering, final boolean rowNumbering,
        @SuppressWarnings({"SameParameterValue"}) final long timestamp,
        @NonNull final StringGetter stringGetter)
        {
            super(title, type, rows, cols, generatedExcludedCellsAmount,
                colNumbering, rowNumbering, timestamp, stringGetter);
        }
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.ModelIdMustBeGreaterThanZero:
                return "id must be > 0";

            case R.string.UtilsInvalidValue:
                return "value must be >= %d";

            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Constructor Tests
    // region First Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void idCausesFirstConstructorFail()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */0,
            /* title => */"testTitle",
            /* type  => */ TemplateType.USERDEFINED,
            /* rows  => */2,
            /* cols  => */2,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering                 => */false,
            /* rowNumbering                 => */true,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rowsCausesFirstConstructorFail()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */5,
            /* title => */"testTitle",
            /* type  => */ TemplateType.USERDEFINED,
            /* rows  => */0,
            /* cols  => */2,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering                 => */false,
            /* rowNumbering                 => */true,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void colsCausesFirstConstructorFail()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */5,
            /* title => */"testTitle",
            /* type  => */ TemplateType.USERDEFINED,
            /* rows  => */2,
            /* cols  => */-5,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering                 => */false,
            /* rowNumbering                 => */false,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rowsAndColsCauseFirstConstructorFail()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */5,
            /* title => */"testTitle",
            /* type  => */ TemplateType.DNA,
            /* rows  => */-999,
            /* cols  => */0,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering                 => */true,
            /* rowNumbering                 => */true,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generatedExcludedCellsAmountCausesFirstConstructorFail()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */5,
            /* title => */"testTitle",
            /* type  => */ TemplateType.USERDEFINED,
            /* rows  => */2,
            /* cols  => */2,
            /* generatedExcludedCellsAmount => */-90,
            /* colNumbering                 => */false,
            /* rowNumbering                 => */false,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test() public void firstConstructorSucceeds()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */88,
            /* title => */"testTitle",
            /* type  => */ TemplateType.SEED,
            /* rows  => */99,
            /* cols  => */4,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering                 => */true,
            /* rowNumbering                 => */false,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }
    // endregion

    // region Second Constructor Tests
    @Test(expected = IllegalArgumentException.class)
    public void rowsCausesSecondConstructorFail()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* title        => */"testTitle",
            /* type         => */ TemplateType.USERDEFINED,
            /* rows         => */0,
            /* cols         => */2,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering                 => */false,
            /* rowNumbering                 => */true,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void colsCausesSecondConstructorFail()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* title        => */"testTitle",
            /* type         => */ TemplateType.USERDEFINED,
            /* rows         => */2,
            /* cols         => */-5,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering                 => */false,
            /* rowNumbering                 => */false,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rowsAndColsCauseSecondConstructorFail()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* title        => */"testTitle",
            /* type         => */ TemplateType.DNA,
            /* rows         => */-999,
            /* cols         => */0,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering                 => */true,
            /* rowNumbering                 => */true,
            /* timestamp                    => */0,
            /* stringGetter                 => */this);
    }

    @Test() public void secondConstructorSucceeds()
    {
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* title        => */"testTitle",
                    /* type         => */ TemplateType.SEED,
                    /* rows         => */99,
                    /* cols         => */4,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */true,
                    /* rowNumbering                 => */false,
                    /* timestamp                    => */0,
            /* stringGetter                 => */this);
        Assert.assertEquals(concreteBaseTemplateModel.getId(),0);
    }
    // endregion
    // endregion

    // region Getter and Setter Public Method Tests
    @Test() public void getAndSetTitleSucceed()
    {
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel;
        final String secondTestTitle;
        {
            final String firstTestTitle = "firstTestTitle";
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */ firstTestTitle,
                    /* type  => */ TemplateType.DNA,
                    /* rows  => */9,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */true,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
            Assert.assertEquals(firstTestTitle, concreteBaseTemplateModel.getTitle());

            secondTestTitle = "secondTestTitle";
            concreteBaseTemplateModel.setTitle(secondTestTitle);
            Assert.assertNotEquals(firstTestTitle, concreteBaseTemplateModel.getTitle());
        }
        Assert.assertEquals(secondTestTitle, concreteBaseTemplateModel.getTitle());
    }

    @Test() public void getAndSetTypeSucceed()
    {
        final TemplateType firstTemplateType =
            TemplateType.SEED;
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id                           => */5,
                    /* title                        => */"testTitle",
                    /* type                         => */ firstTemplateType,
                    /* rows                         => */9,
                    /* cols                         => */20,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false ,
                    /* rowNumbering                 => */true,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertEquals(firstTemplateType, concreteBaseTemplateModel.getType());

        final TemplateType secondTemplateType =
            TemplateType.USERDEFINED;
        concreteBaseTemplateModel.setType(secondTemplateType);
        Assert.assertNotEquals(firstTemplateType , concreteBaseTemplateModel.getType());
        Assert.assertEquals   (secondTemplateType, concreteBaseTemplateModel.getType());
    }

    @Test() public void getRowsAndGetColsSucceed()
    {
        final int rows = 9, cols = 20;
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new 
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */rows,
                    /* cols  => */cols,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */true,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertEquals(rows, concreteBaseTemplateModel.getRows());
        Assert.assertEquals(cols, concreteBaseTemplateModel.getCols());
    }

    @Test() public void getAndSetGeneratedExcludedCellsAmountSucceed()
    {
        final int firstGeneratedExcludedCellsAmount = 1;
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */9,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */ firstGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */true,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertEquals(firstGeneratedExcludedCellsAmount,
            concreteBaseTemplateModel.getGeneratedExcludedCellsAmount());

        final int secondGeneratedExcludedCellsAmount = 55;
        concreteBaseTemplateModel.setGeneratedExcludedCellsAmount(
            secondGeneratedExcludedCellsAmount);
        Assert.assertNotEquals(firstGeneratedExcludedCellsAmount,
            concreteBaseTemplateModel.getGeneratedExcludedCellsAmount());
        Assert.assertEquals(secondGeneratedExcludedCellsAmount,
            concreteBaseTemplateModel.getGeneratedExcludedCellsAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setGeneratedExcludedCellsAmountFails()
    {
        new BaseTemplateModelTest.ConcreteBaseTemplateModel(
            /* id    => */5,
            /* title => */"testTitle",
            /* type  => */ TemplateType.SEED,
            /* rows  => */9,
            /* cols  => */20,
            /* generatedExcludedCellsAmount => */0,
            /* colNumbering => */false,
            /* rowNumbering => */true,
            /* timestamp    => */0,
            /* stringGetter => */this).setGeneratedExcludedCellsAmount(-7);
    }

    @Test() public void getAndSetColNumberingSucceed()
    {
        final boolean firstColNumbering = false;
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */9,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */ firstColNumbering,
                    /* rowNumbering                 => */true,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertEquals(firstColNumbering,
            concreteBaseTemplateModel.getColNumbering());

        final boolean secondColNumbering = true;
        concreteBaseTemplateModel.setColNumbering(secondColNumbering);
        Assert.assertNotEquals(firstColNumbering,
            concreteBaseTemplateModel.getColNumbering());
        Assert.assertEquals(secondColNumbering,
            concreteBaseTemplateModel.getColNumbering());
    }

    @Test() public void getAndSetRowNumberingSucceed()
    {
        final boolean firstRowNumbering = false;
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */9,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */ firstRowNumbering,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertEquals(firstRowNumbering,
            concreteBaseTemplateModel.getRowNumbering());

        final boolean secondRowNumbering = true;
        concreteBaseTemplateModel.setRowNumbering(secondRowNumbering);
        Assert.assertNotEquals(firstRowNumbering,
            concreteBaseTemplateModel.getRowNumbering());
        Assert.assertEquals(secondRowNumbering,
            concreteBaseTemplateModel.getRowNumbering());
    }

    @Test() public void entryLabelIsNotNullAndGetAndSetEntryLabelSucceed()
    {
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.DNA,
                    /* rows  => */9,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */true,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertFalse(concreteBaseTemplateModel.entryLabelIsNotNull());
        Assert.assertNull (concreteBaseTemplateModel.getEntryLabel()      );

        final String testEntryLabel = "testEntryLabel";
        concreteBaseTemplateModel.setEntryLabel(testEntryLabel);
        Assert.assertTrue(concreteBaseTemplateModel.entryLabelIsNotNull());
        Assert.assertEquals(testEntryLabel, concreteBaseTemplateModel.getEntryLabel());
    }

    @Test() public void getTimestampSucceeds()
    {
        final long timestamp = 880;
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */10,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */5,
                    /* cols  => */2,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */true,
                    /* rowNumbering                 => */false,
                    /* timestamp                    => */ timestamp,
                    /* stringGetter                 => */this);
        Assert.assertEquals(timestamp, concreteBaseTemplateModel.getTimestamp());
    }
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringSucceeds()
    {
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */9,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */9,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */false,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        final String expectedString =
            "BaseTemplateModel [id: 05, title=testTitle, type=0, rows=9, cols=20, generatedExcl" +
            "udedCellsAmount=9, colNumbering=false, rowNumbering=false, entryLabel=null, stamp=0]";
        Assert.assertEquals(expectedString, concreteBaseTemplateModel.toString());
    }

    @Test() public void equalsAndHashCodeWork()
    {
        final long                                            testId           = 67         ;
        final String                                testTitle        = "testTitle";
        final TemplateType testTemplateType =
            TemplateType.USERDEFINED;
        final int     testRows = 15, testCols = 1, testGeneratedExcludedCellsAmount = 0;
        final boolean testColNumbering = true, testRowNumbering = false                ;

        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            firstConcreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id                           => */ testId                          ,
                    /* title                        => */ testTitle                       ,
                    /* type                         => */ testTemplateType                ,
                    /* rows                         => */ testRows                        ,
                    /* cols                         => */ testCols                        ,
                    /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ testColNumbering                ,
                    /* rowNumbering                 => */ testRowNumbering                ,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this),
            secondConcreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id                           => */ testId                          ,
                    /* title                        => */ testTitle                       ,
                    /* type                         => */ testTemplateType                ,
                    /* rows                         => */ testRows                        ,
                    /* cols                         => */ testCols                        ,
                    /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ testColNumbering                ,
                    /* rowNumbering                 => */ testRowNumbering                ,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());

        secondConcreteBaseTemplateModel.setTitle("different");
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertNotEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());
        secondConcreteBaseTemplateModel.setTitle(testTitle);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());

        secondConcreteBaseTemplateModel.setType(
            TemplateType.SEED);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertNotEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());
        secondConcreteBaseTemplateModel.setType(testTemplateType);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());

        secondConcreteBaseTemplateModel.setGeneratedExcludedCellsAmount(5);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertNotEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());
        secondConcreteBaseTemplateModel.setGeneratedExcludedCellsAmount(
            testGeneratedExcludedCellsAmount);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());

        secondConcreteBaseTemplateModel.setColNumbering(false);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertNotEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());
        secondConcreteBaseTemplateModel.setColNumbering(testColNumbering);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());

        secondConcreteBaseTemplateModel.setRowNumbering(true);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertNotEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());
        secondConcreteBaseTemplateModel.setRowNumbering(testRowNumbering);
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());

        {
            final String testEntryLabel = "testEntryLabel";
            secondConcreteBaseTemplateModel.setEntryLabel(testEntryLabel);
            // noinspection SimplifiableJUnitAssertion
            Assert.assertFalse(firstConcreteBaseTemplateModel.equals(
                secondConcreteBaseTemplateModel));
            Assert.assertNotEquals(firstConcreteBaseTemplateModel.hashCode(),
                secondConcreteBaseTemplateModel.hashCode());
            firstConcreteBaseTemplateModel.setEntryLabel(testEntryLabel);
        }
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
        Assert.assertEquals(firstConcreteBaseTemplateModel.hashCode(),
            secondConcreteBaseTemplateModel.hashCode());
    }
    // endregion

    @Test() public void formatStringSucceeds()
    {
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */9,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */false,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        final String expectedFormatString =
            "%s [id: 05, title=testTitle, type=0, rows=9, cols=20, generatedExcludedCel" +
            "lsAmount=0, colNumbering=false, rowNumbering=false, entryLabel=null, stamp=0";
        Assert.assertEquals(expectedFormatString,
            concreteBaseTemplateModel.formatString());
    }

    // region Other Public Method Tests
    @Test() public void publicAssignSucceeds()
    {
        final long                                            testId           = 67         ;
        final String                                testTitle        = "testTitle";
        final TemplateType testTemplateType =
            TemplateType.USERDEFINED;
        final int     testRows = 15, testCols = 1, testGeneratedExcludedCellsAmount = 5;
        final boolean testColNumbering = true, testRowNumbering = false                ;
        final long    testTimestamp = 0;

        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            firstConcreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id                           => */ testId                          ,
                    /* title                        => */ testTitle                       ,
                    /* type                         => */ testTemplateType                ,
                    /* rows                         => */ testRows                        ,
                    /* cols                         => */ testCols                        ,
                    /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ testColNumbering                ,
                    /* rowNumbering                 => */ testRowNumbering                ,
                    /* timestamp                    => */ testTimestamp                   ,
                    /* stringGetter                 => */this),
            secondConcreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */ testId,
                    /* title => */"different",
                    /* type  => */ TemplateType.DNA,
                    /* rows  => */3,
                    /* cols  => */9,
                    /* generatedExcludedCellsAmount => */ testGeneratedExcludedCellsAmount,
                    /* colNumbering                 => */ testColNumbering                ,
                    /* rowNumbering                 => */ testRowNumbering                ,
                    /* timestamp                    => */ testTimestamp                   ,  // same
                    /* stringGetter                 => */this);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));

        secondConcreteBaseTemplateModel.assign(
            /* title => */ testTitle,
            /* rows  => */ testRows ,
            /* cols  => */ testCols );
        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(firstConcreteBaseTemplateModel.equals(
            secondConcreteBaseTemplateModel));
    }

    @Test() public void isDefaultWorks()
    {
        final BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.USERDEFINED,
                    /* rows  => */1,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */false,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertFalse(concreteBaseTemplateModel.isDefaultTemplate());

        concreteBaseTemplateModel.setType(TemplateType.SEED);
        Assert.assertTrue(concreteBaseTemplateModel.isDefaultTemplate());
    }

    @Test() public void rowItemsSucceeds()
    {
        BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */1,
                    /* cols  => */20,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */false,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertArrayEquals(
            Utils.stringArray("Row 1"),
            concreteBaseTemplateModel.rowItems("Row"));

        concreteBaseTemplateModel =
            new BaseTemplateModelTest.ConcreteBaseTemplateModel(
                /* id    => */5,
                /* title => */"testTitle",
                /* type  => */ TemplateType.SEED,
                /* rows  => */3,
                /* cols  => */20,
                /* generatedExcludedCellsAmount => */0,
                /* colNumbering                 => */false,
                /* rowNumbering                 => */false,
                /* timestamp                    => */0,
                /* stringGetter                 => */this);
        Assert.assertArrayEquals(new String[]{"Row 1", "Row 2", "Row 3"},
            concreteBaseTemplateModel.rowItems("Row"));
    }

    @Test() public void colItemsSucceeds()
    {
        BaseTemplateModelTest.ConcreteBaseTemplateModel
            concreteBaseTemplateModel = new
                BaseTemplateModelTest.ConcreteBaseTemplateModel(
                    /* id    => */5,
                    /* title => */"testTitle",
                    /* type  => */ TemplateType.SEED,
                    /* rows  => */1,
                    /* cols  => */1,
                    /* generatedExcludedCellsAmount => */0,
                    /* colNumbering                 => */false,
                    /* rowNumbering                 => */false,
                    /* timestamp                    => */0,
                    /* stringGetter                 => */this);
        Assert.assertArrayEquals(
            Utils.stringArray("Column 1"),
            concreteBaseTemplateModel.colItems("Column"));

        concreteBaseTemplateModel =
            new BaseTemplateModelTest.ConcreteBaseTemplateModel(
                /* id    => */5,
                /* title => */"testTitle",
                /* type  => */ TemplateType.SEED,
                /* rows  => */3,
                /* cols  => */3,
                /* generatedExcludedCellsAmount => */0,
                /* colNumbering                 => */false,
                /* rowNumbering                 => */false,
                /* timestamp                    => */0,
                /* stringGetter                 => */this);
        Assert.assertArrayEquals(
            new String[]{"Column 1", "Column 2", "Column 3"},
            concreteBaseTemplateModel.colItems("Column"));
    }
    // endregion
}