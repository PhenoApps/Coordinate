package org.wheatgenetics.coordinate.optionalField;

/**
 * Uses:
 * android.support.annotation.IntRange
 * android.support.annotation.NonNull
 * android.support.annotation.Nullable
 * android.support.annotation.VisibleForTesting
 *
 * org.json.JSONArray
 * org.json.JSONException
 * org.json.JSONObject
 * org.json.JSONTokener
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
    private static boolean useValue(final java.lang.String value)
    { return null != value && value.trim().length() > 0; }

    // region Constructors
    public NonNullOptionalFields() { super(); }

    public NonNullOptionalFields(final java.lang.String json)
    {
        this();

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
                            baseOptionalField = new org.wheatgenetics.coordinate
                                .optionalField.OtherOptionalField(jsonObject);             // throws
                        }
                        catch (final
                        org.wheatgenetics.coordinate.optionalField.OtherOptionalField.WrongClass e)
                        {
                            baseOptionalField =
                                new org.wheatgenetics.coordinate.optionalField.DateOptionalField(
                                    jsonObject);
                        }
                    }
                    this.arrayList.add(baseOptionalField);
                }
            }
        }
    }
    // endregion

    @java.lang.Override @java.lang.SuppressWarnings({"CloneDoesntCallSuperClone"})
    public java.lang.Object clone()
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

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
    private void add(@android.support.annotation.NonNull final java.lang.String name)
    { this.arrayList.add(new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name)); }

    private void add(@android.support.annotation.NonNull final java.lang.String name,
    final java.lang.String hint)
    {
        this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name, hint));
    }

    public void add(@android.support.annotation.NonNull final java.lang.String name,
    final java.lang.String value, final java.lang.String hint)
    {
        this.arrayList.add(
            new org.wheatgenetics.coordinate.optionalField.OtherOptionalField(name, value, hint));
    }

    private void addDate()
    { this.arrayList.add(new org.wheatgenetics.coordinate.optionalField.DateOptionalField()); }
    // endregion

    // region Package Methods
    void setChecked(@android.support.annotation.IntRange(from = 0) final int index,
    final boolean checked) { this.get(index).setChecked(checked); }

    @android.support.annotation.NonNull boolean[] checks()
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

            final int checkedArrayListSize = checkedArrayList.size();
            result = new boolean[checkedArrayListSize];

            for (int i = 0; i < checkedArrayListSize; i++) result[i] = checkedArrayList.get(i);
        }
        return result;
    }
    // endregion

    // region Public Methods
    public boolean isEmpty() { return !this.iterator().hasNext(); }

    public org.wheatgenetics.coordinate.optionalField.BaseOptionalField get(
    @android.support.annotation.IntRange(from = 0) final int index)
    {
        @android.support.annotation.IntRange(from = 0) int size = 0;
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
                @android.support.annotation.IntRange(from = 0) int i = 0;
                do result = iterator.next(); while (i++ < index);
            }
            return result;
        }
    }

    @android.support.annotation.NonNull public java.lang.String getDatedFirstValue()
    {
        final int first = 0;
        return this.get(first).getValue() + "_" +
            org.wheatgenetics.coordinate.optionalField.DateOptionalField.getCurrentDate().replace(
                ".","_");
    }

    @android.support.annotation.NonNull public java.lang.String toJson()
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
                jsonArray.put(dateOptionalField.makeJSONObject());
            }
            else
                if (baseOptionalField instanceof
                org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                {
                    final org.wheatgenetics.coordinate.optionalField.OtherOptionalField
                        otherOptionalField =
                            (org.wheatgenetics.coordinate.optionalField.OtherOptionalField)
                                baseOptionalField;
                    jsonArray.put(otherOptionalField.makeJSONObject());
                }

        return jsonArray.toString();
    }

    @android.support.annotation.NonNull public java.lang.String[] names()
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

    @android.support.annotation.NonNull public java.lang.String[] values()
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

    @java.lang.SuppressWarnings({"CStyleArrayDeclaration"}) @android.support.annotation.Nullable
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

                // noinspection CStyleArrayDeclaration
                final java.lang.String result[] = new java.lang.String[valueArrayList.size()];
                return valueArrayList.toArray(result);
            }
    }

    // region Make Public Methods
    @android.support.annotation.NonNull @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeNew(
    final java.lang.String identification, final java.lang.String person)
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        {
            final java.lang.String name = "Identification";
            if (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.useValue(
            identification))
                result.add(name, /* value => */ identification, /* hint => */null);
            else
                result.add(name);
        }
        {
            final java.lang.String name = "Person";
            if (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.useValue(person))
                result.add(name, /* value => */ person, /* hint => */null);
            else
                result.add(name);
        }
        result.addDate();

        return result;
    }

    @android.support.annotation.NonNull
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeNew()
    {
        return org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeNew(
            null,null);
    }

    @android.support.annotation.NonNull @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeSeedDefault(
    final java.lang.String trayId, final java.lang.String person)
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        {
            final java.lang.String name = "Tray", hint = "Tray ID";
            if (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.useValue(trayId))
                result.add(name, /* value => */ trayId, hint);
            else
                result.add(name, hint);
        }
        {
            final java.lang.String name = "Person", hint = "Person name";
            if (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.useValue(person))
                result.add(name, /* value => */ person, hint);
            else
                result.add(name, hint);
        }
        result.addDate();

        return result;
    }

    @android.support.annotation.NonNull
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeSeedDefault()
    {
        return org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeSeedDefault(
            null,null);
    }

    @android.support.annotation.NonNull @java.lang.SuppressWarnings({"DefaultAnnotationParam"})
    @android.support.annotation.VisibleForTesting(
        otherwise = android.support.annotation.VisibleForTesting.PRIVATE)
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeDNADefault(
    final java.lang.String plateId, final java.lang.String plateName, final java.lang.String person)
    {
        final org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields result =
            new org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields();

        {
            final java.lang.String name = "Plate", hint = "Plate ID";
            if (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.useValue(plateId))
                result.add(name, /* value => */ plateId, hint);
            else
                result.add(name, hint);                                                 // TODO: dna
        }
        {
            final java.lang.String name = "Plate Name";
            if (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.useValue(
            plateName))
                result.add(name, /* value => */ plateName, /* hint => */null);
            else
                result.add(name);
        }
        result.add("Notes");
        result.add("tissue_type", /* value => */"Leaf", /* hint => */"");
        result.add("extraction" , /* value => */"CTAB", /* hint => */"");
        {
            final java.lang.String name = "person";
            if (org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.useValue(person))
                result.add(name, /* value => */ person, /* hint => */null);
            else
                result.add(name);
        }
        result.addDate();

        return result;
    }

    @android.support.annotation.NonNull
    public static org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields makeDNADefault()
    {
        return org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields.makeDNADefault(
            null,null,null);
    }
    // endregion
    // endregion
}