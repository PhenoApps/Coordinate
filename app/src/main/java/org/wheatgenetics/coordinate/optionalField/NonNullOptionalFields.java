package org.wheatgenetics.coordinate.optionalField;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.utils.TimestampUtil;

import java.util.ArrayList;

public class NonNullOptionalFields extends OptionalFields
        implements Cloneable {
    private static final String PERSON_FIELD_DEFAULT_NAME = "Person";

    @NonNull
    private final StringGetter
            stringGetter;

    // region Constructors
    public NonNullOptionalFields(@NonNull final StringGetter stringGetter) {
        super();
        this.stringGetter = stringGetter;
    }

    public NonNullOptionalFields(final String json,
                                 @NonNull final StringGetter stringGetter) {
        this(stringGetter);

        if (null != json) {
            final JSONArray jsonArray;
            {
                final JSONTokener jsonTokener = new JSONTokener(json);
                try {
                    jsonArray = (JSONArray)         // throws java.lang.ClassCastException,
                            jsonTokener.nextValue();             //  org.json.JSONException
                } catch (final ClassCastException | JSONException e) {
                    return;
                }
            }

            if (null != jsonArray) {
                final int last = jsonArray.length() - 1;
                for (int i = 0; i <= last; i++) {
                    BaseOptionalField baseOptionalField;
                    {
                        final JSONObject jsonObject;
                        try {
                            jsonObject = (JSONObject) jsonArray.get(i);
                        }  // throws org-
                        catch (final JSONException e) {
                            continue;
                        }          //  .json.-
                        //  JSONExcep-
                        try                                                           //  tion
                        {
                            baseOptionalField = new OtherOptionalField(jsonObject, this.stringGetter);        // throws
                        } catch (final
                        OtherOptionalField.WrongClass e) {
                            baseOptionalField =
                                    new DateOptionalField(
                                            jsonObject, this.stringGetter);
                        }
                    }
                    this.arrayList.add(baseOptionalField);
                }
            }
        }
    }
    // endregion

    // region Private Methods
    @NonNull
    private static String getString(
            @StringRes final int resId,
            @NonNull final StringGetter stringGetter,
            @NonNull @Size(min = 1) final String
                    defaultValue) {
        final String candidateResult = stringGetter.get(resId);
        return null == candidateResult ? defaultValue : candidateResult;
    }

    // region Make Public Methods
    @NonNull
    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    public static NonNullOptionalFields makeNew(
            final String identificationValue, final String personValue,
            @NonNull final StringGetter stringGetter) {
        @NonNull final String personName =
                NonNullOptionalFields.getString(
                        R.string.BaseOptionalFieldPersonFieldName,
                        stringGetter, NonNullOptionalFields.PERSON_FIELD_DEFAULT_NAME);
        return new NonNullOptionalFields(
                stringGetter).checkedAdd(
                BaseOptionalField.identificationFieldName(stringGetter),
                identificationValue, null).checkedAdd(
                personName, personValue, null).addDate();
    }
    // endregion

    @NonNull
    public static NonNullOptionalFields makeNew(
            @NonNull final StringGetter stringGetter) {
        return NonNullOptionalFields.makeNew(
                null, null, stringGetter);
    }

    @NonNull
    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    public static NonNullOptionalFields makeSeedDefault(
            final String trayIdValue, final String personValue,
            @NonNull final StringGetter stringGetter) {
        @NonNull final NonNullOptionalFields result =
                new NonNullOptionalFields(stringGetter);
        {
            @NonNull final String trayIdName =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsTrayIDFieldName,
                            stringGetter, "Tray");
            @NonNull final String trayIdHint =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsTrayIDFieldHint,
                            stringGetter, "Tray ID");
            result.checkedAdd(trayIdName, trayIdValue, trayIdHint);
        }
        {
            @NonNull final String personName =
                    NonNullOptionalFields.getString(
                            R.string
                                    .NonNullOptionalFieldsSeedTrayPersonFieldName,
                            stringGetter, NonNullOptionalFields.PERSON_FIELD_DEFAULT_NAME);
            @NonNull final String personHint =
                    NonNullOptionalFields.getString(
                            R.string
                                    .NonNullOptionalFieldsSeedTrayPersonFieldHint,
                            stringGetter, "Person name");
            result.checkedAdd(personName, personValue, personHint).addDate();
        }
        return result;
    }

    @NonNull
    public static NonNullOptionalFields makeSeedDefault(
            @NonNull final StringGetter stringGetter) {
        return NonNullOptionalFields.makeSeedDefault(
                null, null, stringGetter);
    }

    @NonNull
    @SuppressWarnings({"DefaultAnnotationParam"})
    @VisibleForTesting(
            otherwise = VisibleForTesting.PRIVATE)
    public static NonNullOptionalFields makeDNADefault(
            final String plateIdValue, final String plateNameValue,
            final String personValue,
            @NonNull final StringGetter stringGetter) {
        @NonNull final NonNullOptionalFields result =
                new NonNullOptionalFields(stringGetter);
        {
            @NonNull final String plateIDName =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsPlateIDFieldName,
                            stringGetter, "Plate");
            @NonNull final String plateIDHint =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsPlateIDFieldHint,
                            stringGetter, "Plate ID");
            result.checkedAdd(plateIDName, plateIdValue, plateIDHint);
        }
        {
            @NonNull final String plateName =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsPlateNameFieldName,
                            stringGetter, "Plate Name");
            result.checkedAdd(plateName, plateNameValue, null);
        }
        {
            @NonNull final String notesName =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsNotesFieldName,
                            stringGetter, "Notes");
            result.add(notesName);
        }
        {
            @NonNull final String tissueTypeName =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsTissueTypeFieldName,
                            stringGetter, "tissue_type");
            @NonNull final String tissueTypeValue =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsTissueTypeFieldValue,
                            stringGetter, "Leaf");
            result.add(tissueTypeName, tissueTypeValue, "");
        }
        {
            @NonNull final String extractionName =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsExtractionFieldName,
                            stringGetter, "extraction");
            @NonNull final String extractionValue =
                    NonNullOptionalFields.getString(
                            R.string.NonNullOptionalFieldsExtractionFieldValue,
                            stringGetter, "CTAB");
            result.add(extractionName, extractionValue, "");
        }
        {
            @NonNull final String personName =
                    NonNullOptionalFields.getString(
                            R.string
                                    .NonNullOptionalFieldsDNAPlatePersonFieldName,
                            stringGetter, "person");
            result.checkedAdd(personName, personValue, null);
        }
        return result.addDate();
    }
    // endregion

    @NonNull
    public static NonNullOptionalFields makeDNADefault(
            @NonNull final StringGetter stringGetter) {
        return NonNullOptionalFields.makeDNADefault(
                null, null, null, stringGetter);
    }

    @NonNull
    private NonNullOptionalFields checkedAdd(
            @NonNull @Size(min = 1) final String name,
            final String value, final String hint) {
        if (null != value && value.trim().length() > 0)
            return this.add(name, /* value => */ value, /* hint => */ hint);
        else
            return this.add(name);
    }
    // endregion

    @Override
    @NonNull
    public Object clone() {
        final NonNullOptionalFields result =
                new NonNullOptionalFields(this.stringGetter);

        for (final BaseOptionalField baseOptionalField :
                this)
            if (baseOptionalField instanceof
                    DateOptionalField) {
                final DateOptionalField
                        dateOptionalField =
                        (DateOptionalField)
                                baseOptionalField;
                result.arrayList.add((DateOptionalField)
                        dateOptionalField.clone());
            } else if (baseOptionalField instanceof
                    OtherOptionalField) {
                final OtherOptionalField
                        otherOptionalField =
                        (OtherOptionalField)
                                baseOptionalField;
                result.arrayList.add(
                        (OtherOptionalField)
                                otherOptionalField.clone());
            }

        return result;
    }

    // region Add Methods
    @NonNull
    private NonNullOptionalFields add(
            @NonNull @Size(min = 1) final String name) {
        this.arrayList.add(new OtherOptionalField(
                name, this.stringGetter));
        return this;
    }

    @NonNull
    public NonNullOptionalFields add(
            @NonNull @Size(min = 1) final String name,
            final String value, final String hint) {
        this.arrayList.add(new OtherOptionalField(
                name, value, hint, this.stringGetter));
        return this;
    }

    @NonNull
    private NonNullOptionalFields addDate() {
        this.arrayList.add(new DateOptionalField(
                this.stringGetter));
        return this;
    }

    // region Package Methods
    public boolean setChecked(@IntRange(from = 0) final int index, final boolean checked) {
        final BaseOptionalField baseOptionalField =
                this.get(index);
        baseOptionalField.setChecked(checked);
        return baseOptionalField.nameIsIdentification();
    }

    public boolean getChecked(@IntRange(from = 0) final int index) {
        return this.get(index).getChecked();
    }

    public void set(final String name, final String value) {
        if (this.contains(name)) {
            int size = this.arrayList.size();
            for (int i = 0; i < size; i++) {
                BaseOptionalField field = get(i);
                if (field.getName().equals(name)) {
                    field.setValue(value);
                    break;
                }
            }
        }
    }

    @NonNull
    boolean[] checks() {
        // noinspection CStyleArrayDeclaration
        final boolean result[];
        {
            @SuppressWarnings({"Convert2Diamond"}) final ArrayList<Boolean> checkedArrayList =
                    new ArrayList<Boolean>();

            for (final BaseOptionalField
                    baseOptionalField : this)
                checkedArrayList.add(baseOptionalField.getChecked());

            @IntRange(from = 0) final int checkedArrayListSize =
                    checkedArrayList.size();
            result = new boolean[checkedArrayListSize];

            for (int i = 0; i < checkedArrayListSize; i++) result[i] = checkedArrayList.get(i);
        }
        return result;
    }

    // region Public Methods
    public boolean isEmpty() {
        return !this.iterator().hasNext();
    }

    @NonNull
    public BaseOptionalField get(
            @IntRange(from = 0) final int index) {
        @IntRange(from = 0) int size = 0;
        {
            final OptionalFields.Iterator iterator =
                    this.iterator();
            // noinspection WhileLoopReplaceableByForEach
            while (iterator.hasNext()) {
                size++;
                iterator.next();
            }
        }

        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        else {
            BaseOptionalField result;
            {
                final OptionalFields.Iterator iterator =
                        this.iterator();
                @IntRange(from = 0) int i = 0;
                do result = iterator.next(); while (i++ < index);
            }
            return result;
        }
    }

    @NonNull
    public String getDatedFirstValue() {
        final int first = 0;
        return this.get(first).getValue() + "_" + new TimestampUtil().getTime();
    }

    @NonNull
    public String toJson() {
        final JSONArray jsonArray = new JSONArray();

        for (final BaseOptionalField baseOptionalField :
                this)
            if (baseOptionalField instanceof
                    DateOptionalField) {
                final DateOptionalField
                        dateOptionalField =
                        (DateOptionalField)
                                baseOptionalField;
                jsonArray.put(dateOptionalField.makeJSONObject(this.stringGetter));
            } else if (baseOptionalField instanceof
                    OtherOptionalField) {
                final OtherOptionalField
                        otherOptionalField =
                        (OtherOptionalField)
                                baseOptionalField;
                jsonArray.put(otherOptionalField.makeJSONObject(this.stringGetter));
            }

        return jsonArray.toString();
    }

    @NonNull
    public String[] names() {
        @SuppressWarnings({"Convert2Diamond"}) final ArrayList<String> nameArrayList =
                new ArrayList<String>();
        for (final BaseOptionalField baseOptionalField :
                this) {
            // if an optional field has a default text, show the default
            // else just show the name
            if (baseOptionalField instanceof OtherOptionalField && !baseOptionalField.getValue().isEmpty()) {
                nameArrayList.add(baseOptionalField.getName() +  " (Default: " + baseOptionalField.getValue() + ")");
            } else {
                nameArrayList.add(baseOptionalField.getName());
            }
        }

        // noinspection CStyleArrayDeclaration
        final String result[] = new String[nameArrayList.size()];
        return nameArrayList.toArray(result);
    }

    public boolean contains(String name) {

        for (String n : names()) {
            if (n.equals(name)) return true;
        }

        return false;
    }

    @NonNull
    public String[] values() {
        @SuppressWarnings({"Convert2Diamond"}) final ArrayList<String> valueArrayList =
                new ArrayList<String>();
        for (final BaseOptionalField baseOptionalField :
                this)
            if (baseOptionalField instanceof
                    DateOptionalField) {
                final DateOptionalField
                        dateOptionalField =
                        (DateOptionalField)
                                baseOptionalField;
                valueArrayList.add(dateOptionalField.getValue());
            } else if (baseOptionalField instanceof
                    OtherOptionalField) {
                final OtherOptionalField
                        otherOptionalField =
                        (OtherOptionalField)
                                baseOptionalField;
                valueArrayList.add(otherOptionalField.getValue());
            }

        // noinspection CStyleArrayDeclaration
        final String result[] = new String[valueArrayList.size()];
        return valueArrayList.toArray(result);
    }

    @Nullable
    @SuppressWarnings({"CStyleArrayDeclaration"})
    public String[] values(final String names[]) {
        if (null == names)
            return null;
        else if (names.length <= 0)
            return null;
        else {
            @SuppressWarnings({"Convert2Diamond"}) final ArrayList<String> valueArrayList =
                    new ArrayList<String>();

            for (final String name : names) {
                boolean nameFound = false;
                for (final BaseOptionalField
                        baseOptionalField : this)
                    if (baseOptionalField.namesAreEqual(name)) {
                        final String safeValue;
                        {
                            if (baseOptionalField instanceof
                                    DateOptionalField) {
                                final DateOptionalField dateOptionalField =
                                        (DateOptionalField) baseOptionalField;
                                safeValue = dateOptionalField.getSafeValue();
                            } else if (baseOptionalField instanceof
                                    OtherOptionalField) {
                                final OtherOptionalField otherOptionalField =
                                        (OtherOptionalField) baseOptionalField;
                                safeValue = otherOptionalField.getSafeValue();
                            } else safeValue = null;
                        }
                        valueArrayList.add(safeValue);
                        nameFound = true;
                        break;
                    }
                if (!nameFound) valueArrayList.add("");
            }

            final String result[] = new String[valueArrayList.size()];
            return valueArrayList.toArray(result);
        }
    }
    // endregion
    // endregion
}