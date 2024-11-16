package org.wheatgenetics.coordinate.optionalField;

import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.wheatgenetics.coordinate.R;
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
 * org.json.JSONArray
 * org.json.JSONObject
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.javalib.Utils
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class NonNullOptionalFieldsTest extends Object
{
    private static class StringGetter extends Object
    implements org.wheatgenetics.coordinate.StringGetter
    {
        // region Overridden Methods
        @Override @Nullable public String get(
        @StringRes final int resId)
        {
            switch (resId)
            {
                case R.string.BaseOptionalFieldPersonFieldName:
                    return "Person";
                case R.string.BaseOptionalFieldIdentificationFieldName:
                    return "Identification";


                case R.string.TimestampOptionalFieldDateFieldName:
                    return "Date";


                case R.string.NonNullOptionalFieldsTrayIDFieldName:
                    return "Tray";
                case R.string.NonNullOptionalFieldsTrayIDFieldHint:
                    return "Tray ID";
                case
                R.string.NonNullOptionalFieldsSeedTrayPersonFieldName:
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


                default: Assert.fail(); return null;
            }
        }

        @Override @NonNull public String getQuantity(
        @PluralsRes         final int                 resId     ,
        @IntRange(from = 0) final int                 quantity  ,
        @Nullable           final Object... formatArgs)
        throws Resources.NotFoundException { Assert.fail(); return null; }
        // endregion
    }

    private static final String NAME1 = "name1", VALUE1 = "value1", HINT1 = "hint1";

    private static final
        NonNullOptionalFieldsTest.StringGetter
        stringGetter =
            new NonNullOptionalFieldsTest.StringGetter();

    // region Private Methods
    @IntRange(from = 0) private static int size(
    final NonNullOptionalFields nonNullOptionalFields)
    {
        int result = 0;
        if (null != nonNullOptionalFields)
            // noinspection UnusedParameters
            for (final BaseOptionalField
            baseOptionalField: nonNullOptionalFields)
                result++;
        return result;
    }

    @NonNull private static JSONObject makeJSONObject()
    {
        return OptionalField.makeJSONObject(
            NonNullOptionalFieldsTest.NAME1       ,
            NonNullOptionalFieldsTest.VALUE1      ,
            NonNullOptionalFieldsTest.HINT1       ,
            NonNullOptionalFieldsTest.stringGetter);
    }

    @NonNull private static JSONArray makeJSONArray()
    {
        final JSONArray result = new JSONArray();
        result.put(
            NonNullOptionalFieldsTest.makeJSONObject());
        result.put(OptionalField.makeJSONObject(
            "name2","value2",
            BaseOptionalField.TIMESTAMP_HINT,
            NonNullOptionalFieldsTest.stringGetter));
        return result;
    }
    // endregion

    @NonNull
    static NonNullOptionalFields
    makeNonNullOptionalFields()
    {
        return new NonNullOptionalFields(
            NonNullOptionalFieldsTest.makeJSONArray()
                .toString(),
            NonNullOptionalFieldsTest.stringGetter);
    }

    // region Constructor Tests
    // region First Constructor Test
    @Test() public void firstConstructorSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields =
                new NonNullOptionalFields(NonNullOptionalFieldsTest.stringGetter);
        Assert.assertNotNull(nonNullOptionalFields);
        Assert.assertEquals (0,
            NonNullOptionalFieldsTest.size(
                nonNullOptionalFields));
    }
    // endregion

    // region Second Constructor Tests
    @Test() public void secondConstructorNullParameterSucceeds()
    {
        Assert.assertTrue(
            new NonNullOptionalFields(null,
                NonNullOptionalFieldsTest.stringGetter)
                    .isEmpty());
    }

    @Test() public void secondConstructorEmptyParameterSucceeds()
    {
        Assert.assertTrue(
            new NonNullOptionalFields("",
                NonNullOptionalFieldsTest.stringGetter)
                    .isEmpty());
    }

    @Test() public void secondConstructorSpacesParameterSucceeds()
    {
        Assert.assertTrue(
            new NonNullOptionalFields("  ",
                NonNullOptionalFieldsTest.stringGetter)
                    .isEmpty());
    }

    @Test() public void secondConstructorBadParameterSucceeds()
    {
        Assert.assertTrue(
            new NonNullOptionalFields("abc",
                NonNullOptionalFieldsTest.stringGetter)
                    .isEmpty());
    }

    @Test() public void secondConstructorGoodParameterSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertFalse (nonNullOptionalFields.isEmpty());
        Assert.assertEquals(2,
            NonNullOptionalFieldsTest.size(
                nonNullOptionalFields));
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @Test() public void toStringSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertEquals(
            "{" +
                NonNullOptionalFieldsTest.NAME1 +
                ", Date}",
            nonNullOptionalFields.toString());
    }

    @Test() public void equalsAndCloneSucceed()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();

        final NonNullOptionalFields
            clonedNonNullOptionalFields =
                (NonNullOptionalFields)
                    nonNullOptionalFields.clone();

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(nonNullOptionalFields.equals(clonedNonNullOptionalFields));
    }

    @Test() public void hashCodeSucceeds()
    {
        final NonNullOptionalFields
            firstNonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();

        final NonNullOptionalFields
            secondNonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();

        Assert.assertEquals(
            firstNonNullOptionalFields.hashCode(), secondNonNullOptionalFields.hashCode());
    }

    @Test() public void iteratorWorks()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertEquals(2,
            NonNullOptionalFieldsTest.size(
                nonNullOptionalFields));

        nonNullOptionalFields.arrayList.add(null);
        Assert.assertEquals(2,
            NonNullOptionalFieldsTest.size(
                nonNullOptionalFields));

        Assert.assertEquals(3, nonNullOptionalFields.arrayList.size());
    }
    // endregion

    // region Add Method Test
    @Test() public void addAndGetSucceed()
    {
        final NonNullOptionalFields
            nonNullOptionalFields =
                new NonNullOptionalFields(null, NonNullOptionalFieldsTest.stringGetter);
        nonNullOptionalFields.add(
            NonNullOptionalFieldsTest.NAME1 ,
            NonNullOptionalFieldsTest.VALUE1,
            NonNullOptionalFieldsTest.HINT1 );

        final OtherOptionalField otherOptionalField =
            new OtherOptionalField(
                NonNullOptionalFieldsTest.NAME1       ,
                NonNullOptionalFieldsTest.VALUE1      ,
                NonNullOptionalFieldsTest.HINT1       ,
                NonNullOptionalFieldsTest.stringGetter);

        // noinspection SimplifiableJUnitAssertion
        Assert.assertTrue(otherOptionalField.equals(nonNullOptionalFields.get(0)));
    }
    // endregion

    // region Package Method Tests
    @Test() public void setCheckedSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();

        final int first = 0;
        nonNullOptionalFields.setChecked(first,false);
        Assert.assertFalse(nonNullOptionalFields.get(first).getChecked());

        nonNullOptionalFields.setChecked(first,true);
        Assert.assertTrue(nonNullOptionalFields.get(first).getChecked());
    }

    @Test() public void checksSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertArrayEquals(
            new boolean[]{true, true}, nonNullOptionalFields.checks());
    }
    // endregion

    // region Public Method Tests
    @Test(expected = IndexOutOfBoundsException.class)
    public void getIndexParameterTooSmallFails()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        nonNullOptionalFields.get(-3);                 // throws java.lang.IndexOutOfBoundsException
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getIndexParameterTooBigFails()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        nonNullOptionalFields.get(33);                 // throws java.lang.IndexOutOfBoundsException
    }

    @Test() public void getSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertNotNull(nonNullOptionalFields.get(0));
        Assert.assertNotNull(nonNullOptionalFields.get(1));
    }

    @Test() public void getDatedFirstValueSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields =
                new NonNullOptionalFields(null, NonNullOptionalFieldsTest.stringGetter);
        nonNullOptionalFields.add(
            NonNullOptionalFieldsTest.NAME1 ,
            NonNullOptionalFieldsTest.VALUE1,
            NonNullOptionalFieldsTest.HINT1 );
        Assert.assertEquals(
            NonNullOptionalFieldsTest.VALUE1 +
                "_" + TimestampOptionalField.getCurrentTimestamp()
                    .replace(".","_"),
            nonNullOptionalFields.getDatedFirstValue());
    }

    @Test() public void toJSONSucceeds()
    {
        final String actualJSON = NonNullOptionalFieldsTest.makeNonNullOptionalFields().toJson();

        final String expectedJSON;
        {
            final JSONArray jsonArray = new JSONArray();
            {
                final JSONObject jsonObject = NonNullOptionalFieldsTest.makeJSONObject();
                OptionalField.putChecked(
                    jsonObject,true, NonNullOptionalFieldsTest.stringGetter);
                jsonArray.put(jsonObject);
            }
            jsonArray.put(new
                    TimestampOptionalField(NonNullOptionalFieldsTest.stringGetter)
                        .makeJSONObject(NonNullOptionalFieldsTest.stringGetter));
            expectedJSON = jsonArray.toString();
        }

        Assert.assertEquals(expectedJSON, actualJSON);
    }

    @Test() public void namesSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertArrayEquals(new String[]{
                NonNullOptionalFieldsTest.NAME1, "Date"},
            nonNullOptionalFields.names());
    }

    @Test() public void firstValuesSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertArrayEquals(new String[]{
                NonNullOptionalFieldsTest.VALUE1  ,
                TimestampOptionalField.getCurrentTimestamp()},
            nonNullOptionalFields.values());
    }

    @Test() public void secondValuesFails()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertNull(nonNullOptionalFields.values(null));
        Assert.assertNull(nonNullOptionalFields.values(new String[]{}));
    }

    @Test() public void secondValuesSucceeds()
    {
        final NonNullOptionalFields
            nonNullOptionalFields = NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        Assert.assertArrayEquals(
            Utils.stringArray(
                NonNullOptionalFieldsTest.VALUE1),
            nonNullOptionalFields.values(Utils.stringArray(
                NonNullOptionalFieldsTest.NAME1)));
        Assert.assertArrayEquals(
            Utils.stringArray(
                TimestampOptionalField.getCurrentTimestamp()),
            nonNullOptionalFields.values(Utils.stringArray(
                "Date")));
    }

    // region Make Public Methods
    @Test() public void makeNewWorks()
    {
        final NonNullOptionalFields
            newNonNullOptionalFields =
                NonNullOptionalFields.makeNew(NonNullOptionalFieldsTest.stringGetter);
        Assert.assertNotNull(newNonNullOptionalFields          );
        Assert.assertFalse  (newNonNullOptionalFields.isEmpty());
        Assert.assertEquals (3,
            NonNullOptionalFieldsTest.size(
                newNonNullOptionalFields));
        Assert.assertEquals("Identification",
            newNonNullOptionalFields.get(0).getName());
        Assert.assertEquals("Person", newNonNullOptionalFields.get(1).getName());
        Assert.assertEquals("Date"  , newNonNullOptionalFields.get(2).getName());
    }

    @Test() public void makeSeedDefaultWorks()
    {
        final NonNullOptionalFields
            seedDefaultNonNullOptionalFields =
                NonNullOptionalFields.makeSeedDefault(NonNullOptionalFieldsTest.stringGetter);
        Assert.assertNotNull(seedDefaultNonNullOptionalFields          );
        Assert.assertFalse  (seedDefaultNonNullOptionalFields.isEmpty());
        Assert.assertEquals (3,
            NonNullOptionalFieldsTest.size(
                seedDefaultNonNullOptionalFields));
        Assert.assertEquals("Tray",
            seedDefaultNonNullOptionalFields.get(0).getName());
        Assert.assertEquals("Person",
            seedDefaultNonNullOptionalFields.get(1).getName());
        Assert.assertEquals("Date",
            seedDefaultNonNullOptionalFields.get(2).getName());
    }

    @Test() public void makeDNADefaultWorks()
    {
        final NonNullOptionalFields
            dnaDefaultNonNullOptionalFields =
                NonNullOptionalFields.makeDNADefault(NonNullOptionalFieldsTest.stringGetter);
        Assert.assertNotNull(dnaDefaultNonNullOptionalFields          );
        Assert.assertFalse  (dnaDefaultNonNullOptionalFields.isEmpty());
        Assert.assertEquals (7,
            NonNullOptionalFieldsTest.size(
                dnaDefaultNonNullOptionalFields));
        Assert.assertEquals("Plate",
            dnaDefaultNonNullOptionalFields.get(0).getName());
        Assert.assertEquals("Plate Name",
            dnaDefaultNonNullOptionalFields.get(1).getName());
        Assert.assertEquals("Notes",
            dnaDefaultNonNullOptionalFields.get(2).getName());
        Assert.assertEquals("tissue_type",
            dnaDefaultNonNullOptionalFields.get(3).getName());
        Assert.assertEquals("extraction",
            dnaDefaultNonNullOptionalFields.get(4).getName());
        Assert.assertEquals("person",
            dnaDefaultNonNullOptionalFields.get(5).getName());
        Assert.assertEquals("Date",
            dnaDefaultNonNullOptionalFields.get(6).getName());
    }
    // endregion
    // endregion
}