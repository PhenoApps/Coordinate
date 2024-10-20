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
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.model.TemplateModel
 * org.wheatgenetics.coordinate.model.TemplateModels
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"}) public class TemplateModelsTest
extends Object implements StringGetter
{
    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override @Nullable public String get(
    @StringRes final int resId)
    {
        switch (resId)
        {
            case R.string.TimestampOptionalFieldDateFieldName:
                return "Date";


            case R.string.NonNullOptionalFieldsTrayIDFieldName:
                return "Tray";
            case R.string.NonNullOptionalFieldsTrayIDFieldHint:
                return "Tray ID";
            case R.string.NonNullOptionalFieldsSeedTrayPersonFieldName:
                return "Person";
            case
                R.string.NonNullOptionalFieldsSeedTrayPersonFieldHint:
                return "Person name";

            case R.string.NonNullOptionalFieldsPlateIDFieldName:
                return "Plate";
            case R.string.NonNullOptionalFieldsPlateIDFieldHint:
                return "Plate ID";

            case R.string.NonNullOptionalFieldsPlateNameFieldName:
                return "Plate Name";

            case R.string.NonNullOptionalFieldsNotesFieldName:
                return "Notes";

            case
            R.string.NonNullOptionalFieldsTissueTypeFieldName:
                return "tissue_type";
            case
            R.string.NonNullOptionalFieldsTissueTypeFieldValue:
                return "Leaf";

            case R.string.NonNullOptionalFieldsExtractionFieldName:
                return "extraction";
            case
            R.string.NonNullOptionalFieldsExtractionFieldValue:
                return "CTAB";

            case
            R.string.NonNullOptionalFieldsDNAPlatePersonFieldName:
                return "person";


            case R.string.SeedDefaultTemplateTitle: return "Seed Tray";
            case R.string.DNADefaultTemplateTitle : return "DNA Plate";


            default: Assert.fail(); return null;
        }
    }

    @Override @NonNull public String getQuantity(
    @PluralsRes         final int                 resId     ,
    @IntRange(from = 0) final int                 quantity  ,
    @Nullable           final Object... formatArgs)
    throws Resources.NotFoundException { Assert.fail(); return null; }
    // endregion

    @Test() public void iteratorWorks()
    {
              int                                               count          = 0;
        final TemplateModels templateModels =
            TemplateModels.makeDefault(this);

        // noinspection UnusedParameters
        for (final TemplateModel templateModel: templateModels)
            count++;

        Assert.assertEquals(count, templateModels.size());
    }

    // region Public Method Tests
    @Test() public void addAndSizeAndGetWork()
    {
        final TemplateModels templateModels =
            TemplateModels.makeDefault(this);
        {
            final TemplateModel templateModel =
                new TemplateModel(
                    /* id                           => */4,
                    /* title                        => */"testTitle",
                    /* code                         => */1,
                    /* rows                         => */5,
                    /* cols                         => */2,
                    /* generatedExcludedCellsAmount => */0,
                    /* initialExcludeCells          => */null,
                    /* excludeRows                  => */null,
                    /* excludeCols                  => */null,
                    /* colNumbering                 => */1,
                    /* rowNumbering                 => */0,
                    /* entryLabel                   => */null,
                    /* optionalFields               => */null,
                    /* stringGetter                 => */this,
                    /* timestamp                    => */0);
            templateModels.add(templateModel);
            Assert.assertEquals(templateModel, templateModels.get(2));
        }
        Assert.assertEquals(3, templateModels.size());
        Assert.assertNull  (templateModels.get(999));
    }

    @Test() public void getWorks()
    { Assert.assertNull(new TemplateModels().get(0)); }

    @Test() public void makeDefaultAndSizeSucceed()
    {
        Assert.assertEquals(2,
            TemplateModels.makeDefault(this).size());
    }

    @Test() public void titlesWorks()
    {
        Assert.assertNull(
            new TemplateModels().titles());
    }

    @Test() public void titlesAndMakeDefaultSucceed()
    {
        // noinspection CStyleArrayDeclaration
        final String expected[] = new String[]{
            TemplateModel.makeSeedDefault(this).getTitle(),
            TemplateModel.makeDNADefault (this).getTitle()};
        final TemplateModels templateModels =
            TemplateModels.makeDefault(this);
        Assert.assertArrayEquals(expected, templateModels.titles());
    }

    @Test() public void makeDefaultAndGetSucceed()
    {
        final TemplateModels templateModels =
            TemplateModels.makeDefault(this);
        Assert.assertEquals(templateModels.get(0),
            TemplateModel.makeSeedDefault(this));
        Assert.assertEquals(templateModels.get(1),
            TemplateModel.makeDNADefault(this));
    }
    // endregion
}