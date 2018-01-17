package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * org.json.JSONArray
 * org.json.JSONObject
 *
 * org.junit.Assert
 * org.junit.Test
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 *
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 * org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 *
 * org.wheatgenetics.coordinate.optionalField.OptionalFieldTest
 */
@java.lang.SuppressWarnings("ClassExplicitlyExtendsObject")
public class NonNullOptionalFieldsTest extends java.lang.Object
{
    private static final java.lang.String NAME1 = "name1", VALUE1 = "value1", HINT1 = "hint1";

    // region Private Methods
    private static org.json.JSONObject makeJSONObject()
    {
        return org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.NAME1 ,
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1,
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.HINT1 );
    }

    private static org.json.JSONArray makeJSONArray()
    {
        final org.json.JSONArray result = new org.json.JSONArray();
        result.put(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.makeJSONObject());
        result.put(org.wheatgenetics.coordinate.optionalField.OptionalFieldTest.makeJSONObject(
            "name2", "value2",
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField.DATE_HINT));
        return result;
    }

    private static int size(
    final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields)
    {
        int result = 0;
        if (null != nonNullOptionalFields)
            for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
            baseOptionalField: nonNullOptionalFields)
                result++;
        return result;
    }
    // endregion

    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
    makeNonNullOptionalFields()
    {
        return new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.makeJSONArray()
                .toString());
    }

    // region Constructor Tests
    // region First Constructor Tests
    @org.junit.Test
    public void firstConstructorSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields nonNullOptionalFields =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();
        org.junit.Assert.assertNotNull(nonNullOptionalFields);
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.size(
                nonNullOptionalFields),
            0);
    }
    // endregion

    // region Second Constructor Tests
    @org.junit.Test
    public void secondConstructorNullParameterSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(null);
        org.junit.Assert.assertTrue(nonNullOptionalFields.isEmpty());
    }

    @org.junit.Test
    public void secondConstructorEmptyParameterSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields("");
        org.junit.Assert.assertTrue(nonNullOptionalFields.isEmpty());
    }

    @org.junit.Test
    public void secondConstructorSpacesParameterSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields("  ");
        org.junit.Assert.assertTrue(nonNullOptionalFields.isEmpty());
    }

    @org.junit.Test
    public void secondConstructorBadParameterSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields("abc");
        org.junit.Assert.assertTrue(nonNullOptionalFields.isEmpty());
    }

    @org.junit.Test
    public void secondConstructorGoodParameterSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        org.junit.Assert.assertFalse (nonNullOptionalFields.isEmpty());
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.size(
                nonNullOptionalFields),
            2);
    }
    // endregion
    // endregion

    // region Overridden Method Tests
    @org.junit.Test
    public void toStringSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        assert null != nonNullOptionalFields;
        org.junit.Assert.assertEquals(nonNullOptionalFields.toString(), "{" +
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.NAME1 + ", Date}");
    }

    @org.junit.Test
    public void equalsAndCloneSucceed()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();

        assert null != nonNullOptionalFields;
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            clonedNonNullOptionalFields =
                (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields)
                    nonNullOptionalFields.clone();

        org.junit.Assert.assertTrue(nonNullOptionalFields.equals(clonedNonNullOptionalFields));
    }

    @org.junit.Test
    public void hashCodeSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            firstNonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();

        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            secondNonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();

        assert null != firstNonNullOptionalFields; assert null != secondNonNullOptionalFields;
        org.junit.Assert.assertEquals(
            firstNonNullOptionalFields.hashCode(), secondNonNullOptionalFields.hashCode());
    }

    @org.junit.Test
    public void iteratorWorks()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.size(
                nonNullOptionalFields),
            2);

        assert null != nonNullOptionalFields; nonNullOptionalFields.arrayList.add(null);
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.size(
                nonNullOptionalFields),
            2);
        org.junit.Assert.assertEquals(nonNullOptionalFields.arrayList.size(), 3);
    }
    // endregion

    // region Add Method Test
    @org.junit.Test
    public void addAndGetSucceed()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(null);
        nonNullOptionalFields.add(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.NAME1 ,
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1,
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.HINT1 );

        final org.wheatgenetics.coordinate.optionalField.OtherOptionalField otherOptionalField =
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.NAME1 ,
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1,
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.HINT1 );

        org.junit.Assert.assertTrue(otherOptionalField.equals(nonNullOptionalFields.get(0)));
    }
    // endregion

    // region Public Method Tests
    @org.junit.Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void getIndexParameterTooSmallFails()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        assert null != nonNullOptionalFields; nonNullOptionalFields.get(-3);
    }

    @org.junit.Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void getIndexParameterTooBigFails()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
            .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        assert null != nonNullOptionalFields; nonNullOptionalFields.get(33);
    }


    @org.junit.Test
    public void getSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
            .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        assert null != nonNullOptionalFields;
        org.junit.Assert.assertNotNull(nonNullOptionalFields.get(0));
        org.junit.Assert.assertNotNull(nonNullOptionalFields.get(1));
    }

    @org.junit.Test
    public void getFirstValueSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(null);
        nonNullOptionalFields.add(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.NAME1 ,
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1,
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.HINT1 );
        org.junit.Assert.assertEquals(nonNullOptionalFields.getFirstValue(),
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1);
    }

    @org.junit.Test
    public void getDatedFirstValueSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields =
                new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(null);
        nonNullOptionalFields.add(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.NAME1 ,
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1,
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.HINT1 );
        org.junit.Assert.assertEquals(nonNullOptionalFields.getDatedFirstValue(),
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1 + "_" +
                org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate()
                    .replace(".", "_"));
    }

    @org.junit.Test
    public void toJSONSucceeds()
    {
        final java.lang.String actualJSON = org.wheatgenetics.coordinate.optionalField
            .NonNullOptionalFieldsTest.makeNonNullOptionalFields().toJson();

        java.lang.String expectedJSON;
        {
            final org.json.JSONArray jsonArray = new org.json.JSONArray();
            {
                final org.json.JSONObject jsonObject = org.wheatgenetics.coordinate.optionalField
                    .NonNullOptionalFieldsTest.makeJSONObject();
                org.wheatgenetics.coordinate.optionalField.OptionalField.putChecked(
                    jsonObject, true);
                jsonArray.put(jsonObject);
            }
            jsonArray.put(new
                org.wheatgenetics.coordinate.optionalField.DateOptionalField().makeJSONObject());
            expectedJSON = jsonArray.toString();
        }

        org.junit.Assert.assertEquals(actualJSON, expectedJSON);
    }

    @org.junit.Test
    public void namesSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        org.junit.Assert.assertArrayEquals(nonNullOptionalFields.names(), new java.lang.String[] {
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.NAME1, "Date" });
    }

    @org.junit.Test
    public void firstValuesSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        org.junit.Assert.assertArrayEquals(nonNullOptionalFields.values(), new java.lang.String[] {
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1,
            org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate() });
    }

    @org.junit.Test
    public void secondValuesFails()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        org.junit.Assert.assertNull(nonNullOptionalFields.values(null                     ));
        org.junit.Assert.assertNull(nonNullOptionalFields.values(new java.lang.String[] {}));
    }

    @org.junit.Test
    public void secondValuesSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        org.junit.Assert.assertArrayEquals(
            nonNullOptionalFields.values(org.wheatgenetics.javalib.Utils.stringArray(
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.NAME1)),
            org.wheatgenetics.javalib.Utils.stringArray(
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.VALUE1));
        org.junit.Assert.assertArrayEquals(
            nonNullOptionalFields.values(org.wheatgenetics.javalib.Utils.stringArray("Date")) ,
            org.wheatgenetics.javalib.Utils.stringArray(
                org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate()));
    }

    @org.junit.Test
    public void checksSucceeds()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            nonNullOptionalFields = org.wheatgenetics.coordinate.optionalField
                .NonNullOptionalFieldsTest.makeNonNullOptionalFields();
        org.junit.Assert.assertArrayEquals(
            nonNullOptionalFields.checks(), new boolean[] {true, true});
    }

    // region Make Public Methods
    @org.junit.Test
    public void makeNewWorks()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            newNonNullOptionalFields =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew();
        org.junit.Assert.assertFalse (newNonNullOptionalFields.isEmpty());
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.size(
                newNonNullOptionalFields),
            3);
        org.junit.Assert.assertEquals(newNonNullOptionalFields.get(0).getName(), "Identification");
        org.junit.Assert.assertEquals(newNonNullOptionalFields.get(1).getName(), "Person"        );
        org.junit.Assert.assertEquals(newNonNullOptionalFields.get(2).getName(), "Date"          );
    }

    @org.junit.Test
    public void makeSeedDefaultWorks()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            seedDefaultNonNullOptionalFields =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeSeedDefault();
        org.junit.Assert.assertFalse (seedDefaultNonNullOptionalFields.isEmpty());
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.size(
                seedDefaultNonNullOptionalFields),
            3);
        org.junit.Assert.assertEquals(seedDefaultNonNullOptionalFields.get(0).getName(), "Tray"  );
        org.junit.Assert.assertEquals(seedDefaultNonNullOptionalFields.get(1).getName(), "Person");
        org.junit.Assert.assertEquals(seedDefaultNonNullOptionalFields.get(2).getName(), "Date"  );
    }

    @org.junit.Test
    public void makeDNADefaultWorks()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
            dnaDefaultNonNullOptionalFields =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeDNADefault();
        org.junit.Assert.assertFalse (dnaDefaultNonNullOptionalFields.isEmpty());
        org.junit.Assert.assertEquals(
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFieldsTest.size(
                dnaDefaultNonNullOptionalFields),
            7);
        org.junit.Assert.assertEquals(dnaDefaultNonNullOptionalFields.get(0).getName(), "Plate");
        org.junit.Assert.assertEquals(
            dnaDefaultNonNullOptionalFields.get(1).getName(), "Plate Name");
        org.junit.Assert.assertEquals(dnaDefaultNonNullOptionalFields.get(2).getName(), "Notes");
        org.junit.Assert.assertEquals(
            dnaDefaultNonNullOptionalFields.get(3).getName(), "tissue_type");
        org.junit.Assert.assertEquals(
            dnaDefaultNonNullOptionalFields.get(4).getName(), "extraction");
        org.junit.Assert.assertEquals(dnaDefaultNonNullOptionalFields.get(5).getName(), "person");
        org.junit.Assert.assertEquals(dnaDefaultNonNullOptionalFields.get(6).getName(), "date"  );
    }
    // endregion
    // endregion
}