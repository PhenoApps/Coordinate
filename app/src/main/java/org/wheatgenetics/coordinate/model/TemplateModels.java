package org.wheatgenetics.coordinate.model;

import androidx.annotation.NonNull;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.model.TemplateModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 * <p>
 * org.wheatgenetics.coordinate.StringGetter
 * <p>
 * org.wheatgenetics.coordinate.model.TemplateModel
 */
@SuppressWarnings({"ClassExplicitlyExtendsObject"})
public class TemplateModels extends Object
        implements Iterable<TemplateModel> {
    private ArrayList<TemplateModel>
            arrayListInstance = null;                                                       // lazy load

    // region Private Methods
    @NonNull
    private ArrayList<TemplateModel> arrayList() {
        if (null == this.arrayListInstance)
            // noinspection Convert2Diamond
            this.arrayListInstance =
                    new ArrayList<TemplateModel>();
        return this.arrayListInstance;
    }

    private boolean isInRange(final int i) {
        // noinspection SimplifiableConditionalExpression
        return i < 0 ? false : null == this.arrayListInstance ?
                false : i < this.arrayListInstance.size();
    }
    // endregion

    // region java.lang.Iterable<> Overridden Method
    @Override
    @NonNull
    public Iterator<TemplateModel> iterator() {
        class Iterator extends Object
                implements java.util.Iterator<TemplateModel> {
            @NonNull
            private final
            ListIterator<TemplateModel>
                    listIterator;

            private Iterator(@NonNull final ArrayList<TemplateModel> arrayList) {
                super();
                this.listIterator = arrayList.listIterator();
            }

            // region Overridden Methods
            @Override
            public boolean hasNext() {
                return this.listIterator.hasNext();
            }

            @Override
            public TemplateModel next() {
                return this.listIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            // endregion
        }
        return new Iterator(this.arrayList());
    }
    // endregion

    // region Public Methods
    public void add(final TemplateModel templateModel) {
        if (null != templateModel) this.arrayList().add(templateModel);
    }

    @androidx.annotation.IntRange(from = 0)
    public int size() {
        return null == this.arrayListInstance ? 0 : this.arrayListInstance.size();
    }

    @androidx.annotation.Nullable
    public TemplateModel get(
            @androidx.annotation.IntRange(from = 0) final int i) {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    @androidx.annotation.Nullable
    public String[] titles() {
        final int size = this.size();

        if (size <= 0)
            return null;
        else {
            // noinspection CStyleArrayDeclaration
            final String result[] = new String[size];
            {
                final int first = 0, last = size - 1;
                for (int i = first; i <= last; i++) {
                    final TemplateModel templateModel =
                            this.get(i);
                    if (null != templateModel) result[i] = templateModel.getTitle();
                }
            }
            return result;
        }
    }

    @NonNull
    public static TemplateModels makeDefault(
            @NonNull final StringGetter stringGetter) {
        final TemplateModels result =
                new TemplateModels();

        // It matters that the seed tray default template is first and the DNA plate default
        // template is second.  Client code assumes the templates are present in the order just
        // described.  If these two default templates are not present (and not present in the order
        // just described), client code will break.
        result.add(TemplateModel.makeSeedDefault(stringGetter));
        result.add(TemplateModel.makeDNADefault(stringGetter));

        return result;
    }
    // endregion
}