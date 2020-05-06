package org.wheatgenetics.coordinate.model;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.StringGetter;

import java.io.StringWriter;

/**
 * Uses:
 * android.content.res.Resources.NotFoundException
 *
 * androidx.annotation.IntRange
 * androidx.annotation.Nullable
 * androidx.annotation.PluralsRes
 * androidx.annotation.StringRes
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class TemplateModelTest
extends Object implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        // noinspection SwitchStatementWithTooFewBranches
        switch (resId) { default: Assert.fail(); return null; }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringSucceeds()
    {
        final String expectedString =
            "TemplateModel [id: 03, title=testTitle, type=1, rows=5, cols=2, generatedExcludedCel" +
            "lsAmount=0, colNumbering=true, rowNumbering=false, entryLabel=null, stamp=0, exclude" +
            "dCells=null, excludedRows=null, excludedCols=null, options=]";
        final TemplateModel templateModel =
            new TemplateModel(
                /* id                           => */3,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */0);
        Assert.assertEquals(expectedString, templateModel.toString());
    }

    @Test() public void equalsAndHashCodeWork()
    {
        final long             id    = 44                                                       ;
        final String title = "testTitle"                                              ;
        final int              code  = 1, rows = 5, cols = 2, colNumbering = 1, rowNumbering = 0;
        final long             timestamp = 0                                                    ;

        final TemplateModel firstTemplateModel =
            new TemplateModel(
                /* id                           => */ id   ,
                /* title                        => */ title,
                /* code                         => */ code ,
                /* rows                         => */ rows ,
                /* cols                         => */ cols ,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */ colNumbering,
                /* rowNumbering                 => */ rowNumbering,
                /* entryLabel                   => */null,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */ timestamp);
        TemplateModel secondTemplateModel =
            new TemplateModel(
                /* id                           => */ id   ,
                /* title                        => */ title,
                /* code                         => */ code ,
                /* rows                         => */ rows ,
                /* cols                         => */ cols ,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */ colNumbering,
                /* rowNumbering                 => */ rowNumbering,
                /* entryLabel                   => */null,
                /* optionalFields               => */null,                   // Notice.
                /* stringGetter                 => */this,
                /* timestamp                    => */ timestamp);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
        Assert.assertEquals(
            firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

        secondTemplateModel = new TemplateModel(
            /* id                           => */ id   ,
            /* title                        => */ title,
            /* code                         => */ code ,
            /* rows                         => */ rows ,
            /* cols                         => */ cols ,
            /* generatedExcludedCellsAmount => */0,
            /* excludedCells                => */null,
            /* excludedRows                 => */null,
            /* excludedCols                 => */null,
            /* colNumbering                 => */ colNumbering,
            /* rowNumbering                 => */ rowNumbering,
            /* entryLabel                   => */null,
            /* optionalFields               => */"",                         // Notice.
            /* stringGetter                 => */this,
            /* timestamp                    => */ timestamp);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
        Assert.assertEquals(
            firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

        secondTemplateModel = new TemplateModel(
            /* id                           => */ id   ,
            /* title                        => */ title,
            /* code                         => */ code ,
            /* rows                         => */ rows ,
            /* cols                         => */ cols ,
            /* generatedExcludedCellsAmount => */0,
            /* excludedCells                => */null,
            /* excludedRows                 => */null,
            /* excludedCols                 => */null,
            /* colNumbering                 => */ colNumbering,
            /* rowNumbering                 => */ rowNumbering,
            /* entryLabel                   => */null,
            /* optionalFields               => */"  ",                       // Notice.
            /* stringGetter                 => */this,
            /* timestamp                    => */ timestamp);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue  (firstTemplateModel.equals(secondTemplateModel));
        Assert.assertEquals(
            firstTemplateModel.hashCode(), secondTemplateModel.hashCode());

        secondTemplateModel = new TemplateModel(
            /* id                           => */ id   ,
            /* title                        => */ title,
            /* code                         => */ code ,
            /* rows                         => */ rows ,
            /* cols                         => */ cols ,
            /* generatedExcludedCellsAmount => */0,
            /* excludedCells                => */null,
            /* excludedRows                 => */null,
            /* excludedCols                 => */null,
            /* colNumbering                 => */ colNumbering,
            /* rowNumbering                 => */ rowNumbering,
            /* entryLabel                   => */null,
            /* optionalFields               => */null,
            /* stringGetter                 => */this,
            /* timestamp                    => */5087);                         // Notice.

        // noinspection SimplifiableJUnitAssertion
        Assert.assertFalse    (firstTemplateModel.equals(secondTemplateModel));
        Assert.assertNotEquals(
            firstTemplateModel.hashCode(), secondTemplateModel.hashCode());
    }

    @Test() public void cloneSucceeds()
    {
        final TemplateModel templateModel =
            new TemplateModel(
                /* id                           => */12,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */2,
                /* generatedExcludedCellsAmount => */1,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */0);
        final TemplateModel clonedTemplateModel =
            (TemplateModel) templateModel.clone();

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(templateModel.equals(clonedTemplateModel));
    }
    // endregion

    // region export() Package Method Tests
    @Test() public void exportWorks()
    {
        final String expectedString =
            "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>\n"           +
            "<template>\n"                                                         +
            "    <title>testTitle</title>\n"                                       +
            "    <rows>5</rows>\n"                                                 +
            "    <cols>2</cols>\n"                                                 +
            "    <generatedExcludedCellsAmount>0</generatedExcludedCellsAmount>\n" +
            "    <colNumbering>true</colNumbering>\n"                              +
            "    <rowNumbering>false</rowNumbering>\n"                             +
            "</template>"                                                          ;
        final StringWriter stringWriter = new StringWriter();
        {
            final TemplateModel templateModel =
                new TemplateModel(
                    /* id                           => */3,
                    /* title                        => */"testTitle",
                    /* code                         => */1,
                    /* rows                         => */5,
                    /* cols                         => */2,
                    /* generatedExcludedCellsAmount => */0,
                    /* excludedCells                => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* optionalFields               => */null,
                    /* stringGetter                 => */this,
                    /* timestamp                    => */0);
            Assert.assertTrue(templateModel.export(stringWriter));
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }

    @Test() public void entryLabelExportWorks()
    {
        final String expectedString =
            "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>\n"           +
            "<template>\n"                                                         +
            "    <title>testTitle</title>\n"                                       +
            "    <rows>5</rows>\n"                                                 +
            "    <cols>2</cols>\n"                                                 +
            "    <generatedExcludedCellsAmount>0</generatedExcludedCellsAmount>\n" +
            "    <colNumbering>true</colNumbering>\n"                              +
            "    <rowNumbering>false</rowNumbering>\n"                             +
            "    <entryLabel>testEntryLabel</entryLabel>\n"                        +
            "</template>"                                                          ;
        final StringWriter stringWriter = new StringWriter();
        {
            final TemplateModel templateModel =
                new TemplateModel(
                    /* id                           => */3,
                    /* title                        => */"testTitle",
                    /* code                         => */1,
                    /* rows                         => */5,
                    /* cols                         => */2,
                    /* generatedExcludedCellsAmount => */0,
                    /* excludedCells                => */null,
                    /* excludedRows                 => */null,
                    /* excludedCols                 => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* optionalFields               => */null,
                    /* stringGetter                 => */this,
                    /* timestamp                    => */0);
            templateModel.setEntryLabel("testEntryLabel");
            Assert.assertTrue(templateModel.export(stringWriter));
        }
        Assert.assertEquals(expectedString, stringWriter.toString());
    }
    // endregion

    @Test() public void optionalFieldsMethodsSucceed()
    {
        final TemplateModel templateModel =
            new TemplateModel(
                /* id                           => */10,
                /* title                        => */"testTitle",
                /* code                         => */1,
                /* rows                         => */5,
                /* cols                         => */5,
                /* generatedExcludedCellsAmount => */0,
                /* excludedCells                => */null,
                /* excludedRows                 => */null,
                /* excludedCols                 => */null,
                /* colNumbering                 => */1,
                /* rowNumbering                 => */0,
                /* entryLabel                   => */null,
                /* optionalFields               => */null,
                /* stringGetter                 => */this,
                /* timestamp                    => */880);
        Assert.assertNull(templateModel.optionalFields       ());
        Assert.assertTrue(templateModel.optionalFieldsIsEmpty());
        Assert.assertNull(templateModel.optionalFieldsClone  ());
        Assert.assertNull(templateModel.optionalFieldsAsJson ());
    }
}