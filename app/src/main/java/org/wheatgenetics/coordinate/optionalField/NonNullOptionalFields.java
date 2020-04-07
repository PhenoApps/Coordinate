package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * androidx.annotation.Size
 * androidx.annotation.VisibleForTesting
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONObject
 * org.json.JSONTokener
 *
 * org.wheatgenetics.coordinate.R
 * org.wheatgenetics.coordinate.StringGetter
 *
 * org.wheatgenetics.coordinate.optionalField.BaseOptionalField
 * org.wheatgenetics.coordinate.optionalField.DateOptionalField
 * org.wheatgenetics.coordinate.optionalField.OptionalFields
 * org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField
 * org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass
 */
public class NonNullOptionalFields extends org.wheatgenetics.coordinate.optionalField.OptionalFields
implements java.lang.Cloneable
{
    private static final java.lang.String PERSON_FIELD_DEFAULT_NAME = "Person";

    @androidx.annotation.NonNull private final org.wheatgenetics.coordinate.StringGetter
        stringGetter;

    // region Private Methods
    @androidx.annotation.NonNull private static java.lang.String getString(
    @androidx.annotation.StringRes final int                                       resId       ,
    @androidx.annotation.NonNull   final org.wheatgenetics.coordinate.StringGetter stringGetter,
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1) final java.lang.String
        defaultValue)
    {
        final java.lang.String candidateResult = stringGetter.get(resId);
        return null == candidateResult ? defaultValue : candidateResult;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields checkedAdd(
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1) final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        if (null != value && value.trim().length() > 0)
            return this.add(name, /* value => */ value, /* hint => */ hint);
        else
            return this.add(name);
    }
    // endregion

    // region Constructors
    public NonNullOptionalFields(@androidx.annotation.NonNull
    final org.wheatgenetics.coordinate.StringGetter stringGetter)
    { super(); this.stringGetter = stringGetter; }

    public NonNullOptionalFields(final java.lang.String json,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        this(stringGetter);

        if (null != json)
        {
            final org.json.JSONArray jsonArray;
            {
                final org.json.JSONTokener jsonTokener = new org.json.JSONTokener(json);
                try
                {
                    jsonArray = (org.json.JSONArray)         // throws java.lang.ClassCastException,
                        jsonTokener.nextValue();             //  org.json.JSONException
                }
                catch (final java.lang.ClassCastException | org.json.JSONException e) { return; }
            }

            if (null != jsonArray)
            {
                final int last = jsonArray.length() - 1;
                for (int i = 0; i <= last; i++)
                {
                    org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField;
                    {
                        final org.json.JSONObject jsonObject;
                        try { jsonObject = (org.json.JSONObject) jsonArray.get(i); }  // throws org-
                        catch (final org.json.JSONException e) { continue; }          //  .json.-
                                                                                      //  JSONExcep-
                        try                                                           //  tion
                        {
                            baseOptionalField = new org.wheatgenetics.coordinate.optionalField
                                .OtherOptionalField(jsonObject, this.stringGetter);        // throws
                        }
                        catch (final
                        org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass e)
                        {
                            baseOptionalField =
                                new org.wheatgenetics.coordinate.optionalField.DateOptionalField(
                                    jsonObject, this.stringGetter);
                        }
                    }
                    this.arrayList.add(baseOptionalField);
                }
            }
        }
    }
    // endregion

    @java.lang.Override @androidx.annotation.NonNull public java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(this.stringGetter);

        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            if (baseOptionalField instanceof
            org.wheatgenetics.coordinate.optionalField.DateOptionalField)
            {
                final org.wheatgenetics.coordinate.optionalField.DateOptionalField
                    dateOptionalField =
                        (org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                            baseOptionalField;
                result.arrayList.add((org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                    dateOptionalField.clone());
            }
            else
                if (baseOptionalField instanceof
                org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                {
                    final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
                        otherOptionalField =
                            (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                                baseOptionalField;
                    result.arrayList.add(
                        (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                            otherOptionalField.clone());
                }

        return result;
    }

    // region Add Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields add(
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1) final java.lang.String name)
    {
        this.arrayList.add(new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
            name, this.stringGetter));
        return this;
    }

    @androidx.annotation.NonNull
    public org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields add(
    @androidx.annotation.NonNull @androidx.annotation.Size(min = 1) final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        this.arrayList.add(new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(
            name, value, hint, this.stringGetter));
        return this;
    }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields addDate()
    {
        this.arrayList.add(new org.wheatgenetics.coordinate.optionalField.DateOptionalField(
            this.stringGetter));
        return this;
    }
    // endregion

    // region Package Methods
    boolean setChecked(@androidx.annotation.IntRange(from = 0)
    final int index, final boolean checked)
    {
        final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField =
            this.get(index);
        baseOptionalField.setChecked(checked);
        return baseOptionalField.nameIsIdentification();
    }

    @androidx.annotation.NonNull boolean[] checks()
    {
        // noinspection CStyleArrayDeclaration
        final boolean result[];
        {
            @java.lang.SuppressWarnings({"Convert2Diamond"})
            final java.util.ArrayList<java.lang.Boolean> checkedArrayList =
                new java.util.ArrayList<java.lang.Boolean>();

            for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
            baseOptionalField: this)
                checkedArrayList.add(baseOptionalField.getChecked());

            @androidx.annotation.IntRange(from = 0) final int checkedArrayListSize =
                checkedArrayList.size();
            result = new boolean[checkedArrayListSize];

            for (int i = 0; i < checkedArrayListSize; i++) result[i] = checkedArrayList.get(i);
        }
        return result;
    }
    // endregion

    // region Public Methods
    public boolean isEmpty() { return !this.iterator().hasNext(); }

    @androidx.annotation.NonNull
    public org.wheatgenetics.coordinate.optionalField.BaseOptionalField get(
    @androidx.annotation.IntRange(from = 0) final int index)
    {
        @androidx.annotation.IntRange(from = 0) int size = 0;
        {
            final org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator =
                this.iterator();
            // noinspection WhileLoopReplaceableByForEach
            while (iterator.hasNext()) { size++; iterator.next(); }
        }

        if (index < 0 || index >= size)
            throw new java.lang.IndexOutOfBoundsException();
        else
        {
            org.wheatgenetics.coordinate.optionalField.BaseOptionalField result;
            {
                final org.wheatgenetics.coordinate.optionalField.OptionalFields.Iterator iterator =
                    this.iterator();
                @androidx.annotation.IntRange(from = 0) int i = 0;
                do result = iterator.next(); while (i++ < index);
            }
            return result;
        }
    }

    @androidx.annotation.NonNull public java.lang.String getDatedFirstValue()
    {
        final int first = 0;
        return this.get(first).getValue() + "_" +
            org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate().replace(
                ".","_");
    }

    @androidx.annotation.NonNull public java.lang.String toJson()
    {
        final org.json.JSONArray jsonArray = new org.json.JSONArray();

        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            if (baseOptionalField instanceof
            org.wheatgenetics.coordinate.optionalField.DateOptionalField)
            {
                final org.wheatgenetics.coordinate.optionalField.DateOptionalField
                    dateOptionalField =
                        (org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                            baseOptionalField;
                jsonArray.put(dateOptionalField.makeJSONObject(this.stringGetter));
            }
            else
                if (baseOptionalField instanceof
                org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                {
                    final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
                        otherOptionalField =
                            (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                                baseOptionalField;
                    jsonArray.put(otherOptionalField.makeJSONObject(this.stringGetter));
                }

        return jsonArray.toString();
    }

    @androidx.annotation.NonNull public java.lang.String[] names()
    {
        @java.lang.SuppressWarnings({"Convert2Diamond"})
        final java.util.ArrayList<java.lang.String> nameArrayList =
            new java.util.ArrayList<java.lang.String>();
        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            nameArrayList.add(baseOptionalField.getName());

        // noinspection CStyleArrayDeclaration
        final java.lang.String result[] = new java.lang.String[nameArrayList.size()];
        return nameArrayList.toArray(result);
    }

    @androidx.annotation.NonNull public java.lang.String[] values()
    {
        @java.lang.SuppressWarnings({"Convert2Diamond"})
        final java.util.ArrayList<java.lang.String> valueArrayList =
            new java.util.ArrayList<java.lang.String>();
        for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField baseOptionalField:
        this)
            if (baseOptionalField instanceof
            org.wheatgenetics.coordinate.optionalField.DateOptionalField)
            {
                final org.wheatgenetics.coordinate.optionalField.DateOptionalField
                    dateOptionalField =
                        (org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                            baseOptionalField;
                valueArrayList.add(dateOptionalField.getValue());
            }
            else
                if (baseOptionalField instanceof
                org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                {
                    final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
                        otherOptionalField =
                            (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                                baseOptionalField;
                    valueArrayList.add(otherOptionalField.getValue());
                }

        // noinspection CStyleArrayDeclaration
        final java.lang.String result[] = new java.lang.String[valueArrayList.size()];
        return valueArrayList.toArray(result);
    }

    @androidx.annotation.Nullable @java.lang.SuppressWarnings({"CStyleArrayDeclaration"})
    public java.lang.String[] values(final java.lang.String names[])
    {
        if (null == names)
            return null;
        else
            if (names.length <= 0)
                return null;
            else
            {
                @java.lang.SuppressWarnings({"Convert2Diamond"})
                final java.util.ArrayList<java.lang.String> valueArrayList =
                    new java.util.ArrayList<java.lang.String>();

                for (final java.lang.String name: names)
                {
                    boolean nameFound = false;
                    for (final org.wheatgenetics.coordinate.optionalField.BaseOptionalField
                    baseOptionalField: this) if (baseOptionalField.namesAreEqual(name))
                    {
                        final java.lang.String safeValue;
                        {
                            if (baseOptionalField instanceof
                            org.wheatgenetics.coordinate.optionalField.DateOptionalField)
                            {
                                final org.wheatgenetics.coordinate.optionalField
                                    .DateOptionalField dateOptionalField =
                                        (org.wheatgenetics.coordinate.optionalField
                                            .DateOptionalField) baseOptionalField;
                                safeValue = dateOptionalField.getSafeValue();
                            }
                            else
                                if (baseOptionalField instanceof
                                org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                                {
                                    final org.wheatgenetics.coordinate.optionalField
                                        .OtherOptionalField otherOptionalField =
                                            (org.wheatgenetics.coordinate.optionalField
                                                .OtherOptionalField) baseOptionalField;
                                    safeValue = otherOptionalField.getSafeValue();
                                }
                                else safeValue = null;
                        }
                        valueArrayList.add(safeValue);
                        nameFound = true; break;
                    }
                    if (!nameFound) valueArrayList.add("");
                }

                final java.lang.String result[] = new java.lang.String[valueArrayList.size()];
                return valueArrayList.toArray(result);
            }
    }

    // region Make Public Methods
    @androidx.annotation.NonNull @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE)
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeNew(
    final java.lang.String identificationValue, final java.lang.String personValue,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        @androidx.annotation.NonNull final java.lang.String personName =
            org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                org.wheatgenetics.coordinate.R.string.BaseOptionalFieldPersonFieldName,
                stringGetter, org.wheatgenetics.coordinate
                    .optionalField.NonNullOptionalFields.PERSON_FIELD_DEFAULT_NAME);
        return new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(
            stringGetter).checkedAdd(
                org.wheatgenetics.coordinate.optionalField
                    .BaseOptionalField.identificationFieldName(stringGetter),
                identificationValue,null).checkedAdd(
                    personName, personValue,null).addDate();
    }

    @androidx.annotation.NonNull
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeNew(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        return org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew(
            null,null, stringGetter);
    }

    @androidx.annotation.NonNull @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE)
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeSeedDefault(
    final java.lang.String trayIdValue, final java.lang.String personValue,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        @androidx.annotation.NonNull
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(stringGetter);
        {
            @androidx.annotation.NonNull final java.lang.String trayIdName =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsTrayIDFieldName,
                    stringGetter,"Tray");
            @androidx.annotation.NonNull final java.lang.String trayIdHint =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsTrayIDFieldHint,
                    stringGetter,"Tray ID");
            result.checkedAdd(trayIdName, trayIdValue, trayIdHint);
        }
        {
            @androidx.annotation.NonNull final java.lang.String personName =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string
                        .NonNullOptionalFieldsSeedTrayPersonFieldName,
                    stringGetter, org.wheatgenetics.coordinate
                        .optionalField.NonNullOptionalFields.PERSON_FIELD_DEFAULT_NAME);
            @androidx.annotation.NonNull final java.lang.String personHint =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string
                        .NonNullOptionalFieldsSeedTrayPersonFieldHint,
                    stringGetter,"Person name");
            result.checkedAdd(personName, personValue, personHint).addDate();
        }
        return result;
    }

    @androidx.annotation.NonNull
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeSeedDefault(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        return org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeSeedDefault(
            null,null, stringGetter);
    }

    @androidx.annotation.NonNull @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @androidx.annotation.VisibleForTesting(
        otherwise = androidx.annotation.VisibleForTesting.PRIVATE)
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeDNADefault(
    final java.lang.String plateIdValue, final java.lang.String plateNameValue,
    final java.lang.String personValue,
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        @androidx.annotation.NonNull
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields(stringGetter);
        {
            @androidx.annotation.NonNull final java.lang.String plateIDName =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsPlateIDFieldName,
                    stringGetter,"Plate");
            @androidx.annotation.NonNull final java.lang.String plateIDHint =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsPlateIDFieldHint,
                    stringGetter,"Plate ID");
            result.checkedAdd(plateIDName, plateIdValue, plateIDHint);
        }
        {
            @androidx.annotation.NonNull final java.lang.String plateName =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsPlateNameFieldName,
                    stringGetter,"Plate Name");
            result.checkedAdd(plateName, plateNameValue,null);
        }
        {
            @androidx.annotation.NonNull final java.lang.String notesName =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsNotesFieldName,
                    stringGetter,"Notes");
            result.add(notesName);
        }
        {
            @androidx.annotation.NonNull final java.lang.String tissueTypeName =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsTissueTypeFieldName,
                    stringGetter,"tissue_type");
            @androidx.annotation.NonNull final java.lang.String tissueTypeValue =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsTissueTypeFieldValue,
                    stringGetter,"Leaf");
            result.add(tissueTypeName,tissueTypeValue,"");
        }
        {
            @androidx.annotation.NonNull final java.lang.String extractionName =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsExtractionFieldName,
                    stringGetter,"extraction");
            @androidx.annotation.NonNull final java.lang.String extractionValue =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string.NonNullOptionalFieldsExtractionFieldValue,
                    stringGetter,"CTAB");
            result.add(extractionName, extractionValue,"");
        }
        {
            @androidx.annotation.NonNull final java.lang.String personName =
                org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.getString(
                    org.wheatgenetics.coordinate.R.string
                        .NonNullOptionalFieldsDNAPlatePersonFieldName,
                    stringGetter,"person");
            result.checkedAdd(personName, personValue,null);
        }
        return result.addDate();
    }

    @androidx.annotation.NonNull
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeDNADefault(
    @androidx.annotation.NonNull final org.wheatgenetics.coordinate.StringGetter stringGetter)
    {
        return org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeDNADefault(
            null,null,null, stringGetter);
    }
    // endregion
    // endregion
}