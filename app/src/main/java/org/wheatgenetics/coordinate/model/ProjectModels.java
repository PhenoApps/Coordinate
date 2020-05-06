package org.wheatgenetics.coordinate.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProjectModels {
    private ArrayList<ProjectModel>
            arrayListInstance = null;                                                       // lazy load

    // region Private Methods
    @NonNull
    private ArrayList<ProjectModel> arrayList() {
        if (null == this.arrayListInstance)
            // noinspection Convert2Diamond
            this.arrayListInstance =
                    new ArrayList<ProjectModel>();
        return this.arrayListInstance;
    }

    private boolean isInRange(final int i) {
        // noinspection SimplifiableConditionalExpression
        return i < 0 ? false : null == this.arrayListInstance ?
                false : i < this.arrayListInstance.size();
    }
    // endregion

    // region Public Methods
    public void add(final ProjectModel projectModel) {
        if (null != projectModel) this.arrayList().add(projectModel);
    }

    @IntRange(from = 0)
    public int size() {
        return null == this.arrayListInstance ? 0 : this.arrayListInstance.size();
    }

    @Nullable
    public ProjectModel get(
            @IntRange(from = 0) final int i) {
        if (null == this.arrayListInstance)
            return null;
        else
            return this.isInRange(i) ? this.arrayListInstance.get(i) : null;
    }

    @Nullable
    public String[] titles() {
        @IntRange(from = 0) final int size = this.size();

        if (size <= 0)
            return null;
        else {
            // noinspection CStyleArrayDeclaration
            final String result[] = new String[size];
            {
                final int first = 0, last = size - 1;
                for (int i = first; i <= last; i++) {
                    final ProjectModel projectModel =
                            this.get(i);
                    if (null != projectModel) result[i] = projectModel.getTitle();
                }
            }
            return result;
        }
    }
    // endregion
}