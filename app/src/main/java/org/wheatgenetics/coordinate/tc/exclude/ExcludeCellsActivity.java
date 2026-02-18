package org.wheatgenetics.coordinate.tc.exclude;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.utils.InsetHandler;
import org.wheatgenetics.coordinate.model.Cell;
import org.wheatgenetics.coordinate.model.ElementModel;
import org.wheatgenetics.coordinate.model.TemplateModel;

public class ExcludeCellsActivity extends AppCompatActivity implements
        TemplateDisplayFragment.Handler,
        StringGetter {
    // region Fields
    private TemplateModel templateModel = null;
    private ExcludeCellsActivity.DisplayModel
            displayModel = null;

    // region Private Methods
    private boolean isExcludedRow(final int row) {
        // noinspection SimplifiableConditionalExpression
        return null == this.templateModel ? false : this.templateModel.isExcludedRow(row);
    }
    // endregion

    private boolean isExcludedCol(final int col) {
        // noinspection SimplifiableConditionalExpression
        return null == this.templateModel ? false : this.templateModel.isExcludedCol(col);
    }

    private boolean isExcludedCell(
            @NonNull final Cell cell) {
        // noinspection SimplifiableConditionalExpression
        return null == this.templateModel ? false : this.templateModel.isExcludedCell(cell);
    }

    // region Overridden Methods
    @Override
    protected void onCreate(
            @Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InsetHandler.enableEdgeToEdge(this);
        this.setContentView(R.layout.activity_exclude_cells);

        androidx.appcompat.widget.Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.view.View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        InsetHandler.setupStandardInsets(rootView, toolbar);

        this.templateModel = TemplateModel.makeUserDefined(
                null == savedInstanceState ? this.getIntent().getExtras() : savedInstanceState,
                this);
        if (null != this.templateModel) this.displayModel =
                new ExcludeCellsActivity.DisplayModel(
                        this.templateModel, this);
    }
    // endregion

    @Override
    protected void onSaveInstanceState(
            @NonNull final Bundle outState) {
        if (null != this.templateModel) this.templateModel.export(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (null != this.templateModel) {
            final Intent intent = new Intent();
            {
                final Bundle bundle = new Bundle();
                this.templateModel.export(bundle);
                intent.putExtras(bundle);
            }
            this.setResult(Activity.RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    // region org.wheatgenetics.coordinate.tc.exclude.TemplateDisplayFragment.Handler Overridden Methods
    @Override
    public org.wheatgenetics.coordinate.model.DisplayModel getDisplayModel() {
        return this.displayModel;
    }

    @Override
    public void toggle(@Nullable final ElementModel elementModel) {
        if (null != this.templateModel)
            this.templateModel.toggle((Cell) elementModel);
    }

    @Override
    public boolean isExcluded(final Cell cell) {
        if (null == cell)
            return false;
        else if (this.isExcludedRow(cell.getRowValue()))
            return true;
        else
            // noinspection SimplifiableConditionalExpression
            return this.isExcludedCol(cell.getColValue()) ? true : this.isExcludedCell(cell);
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.getString(resId);
    }
    // endregion

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.getResources().getQuantityString(resId, quantity, formatArgs);
    }

    @SuppressWarnings({"ClassExplicitlyExtendsObject"})
    private static class DisplayModel
            extends Object implements org.wheatgenetics.coordinate.model.DisplayModel {
        // region Fields
        @NonNull
        private final TemplateModel
                templateModel;
        @NonNull
        private final StringGetter
                stringGetter;
        // endregion

        private DisplayModel(
                @NonNull final TemplateModel
                        templateModel,
                @NonNull final StringGetter stringGetter) {
            super();
            this.templateModel = templateModel;
            this.stringGetter = stringGetter;
        }

        // region Overridden Methods
        @Override
        @IntRange(from = 1)
        public int getRows() {
            return this.templateModel.getRows();
        }

        @Override
        @IntRange(from = 1)
        public int getCols() {
            return this.templateModel.getCols();
        }

        @Override
        public boolean getColNumbering() {
            return this.templateModel.getColNumbering();
        }

        @Override
        public boolean getRowNumbering() {
            return this.templateModel.getRowNumbering();
        }

        @Override
        @Nullable
        public ElementModel getElementModel(
                @IntRange(from = 1) final int row,
                @IntRange(from = 1) final int col) {
            return new Cell(
                    row, col, this.stringGetter);
        }
        // endregion
    }
    // endregion
    // endregion
}