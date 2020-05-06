package org.wheatgenetics.coordinate;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.wheatgenetics.androidlibrary.AlertDialog;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester;
import org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester;
import org.wheatgenetics.coordinate.tc.SetExcludesOptionalFieldsNumberingAlertDialogTester;
import org.wheatgenetics.coordinate.tc.SetNumberingAlertDialogTester;
import org.wheatgenetics.coordinate.tc.TemplateCreator;
import org.wheatgenetics.coordinate.tc.exclude.ExcludeAlertDialogTester;
import org.wheatgenetics.coordinate.tc.exclude.ExcludedRowsOrColsAlertDialogTester;
import org.wheatgenetics.coordinate.tc.exclude.GeneratedExcludedCellsAlertDialogTester;

import java.util.Locale;

class TestAlertDialog extends AlertDialog implements
        AddOptionalFieldAlertDialogTester.Handler,
        CheckAndAddOptionalFieldsAlertDialog.Handler,
        AssignTitleRowsColsAlertDialogTester.Handler,
        TemplateCreator.Handler,
        StringGetter {
    // region Fields
    @Types.RequestCode
    private final int requestCode;

    private TextView textView;

    private TemplateModel templateModel = null;
    private NonNullOptionalFields
            nonNullOptionalFieldsInstance = null;                                           // lazy load
    private AddOptionalFieldAlertDialogTester
            addOptionalFieldAlertDialogTester = null;                                       // lazy load
    private CheckAndAddOptionalFieldsAlertDialog
            checkAndAddOptionalFieldsAlertDialog = null;                                    // lazy load

    private ExcludedRowsOrColsAlertDialogTester
            excludedRowsOrColsAlertDialogTesterInstance = null;                             // lazy load
    private GeneratedExcludedCellsAlertDialogTester
            generatedExcludedCellsAlertDialogTester = null;                                 // lazy load
    private ExcludeAlertDialogTester
            excludeAlertDialogTester = null;                                                // lazy load
    private SetNumberingAlertDialogTester
            setNumberingAlertDialogTester = null;                                           // lazy load
    private SetExcludesOptionalFieldsNumberingAlertDialogTester
            setExcludesOptionalFieldsNumberingAlertDialogTester = null;                     // lazy load
    private AssignTitleRowsColsAlertDialogTester
            assignTitleRowsColsAlertDialogTester = null;                                    // lazy load

    private TemplateCreator templateCreator = null;     // lazy load
    // endregion

    TestAlertDialog(final Activity activity,
                    @Types.RequestCode final int requestCode) {
        super(activity);
        this.requestCode = requestCode;
    }

    // region Private Methods
    private void refreshText() {
        final StringBuilder textBuilder = new StringBuilder();

        if (null != this.nonNullOptionalFieldsInstance)
            textBuilder.append(this.nonNullOptionalFieldsInstance.toJson());

        if (null != this.templateModel) {
            {
                final NonNullOptionalFields
                        nonNullOptionalFields = this.templateModel.optionalFields();
                if (null != nonNullOptionalFields) {
                    if (textBuilder.length() > 0) textBuilder.append('\n');
                    textBuilder.append(nonNullOptionalFields.toJson());
                }
            }
            if (textBuilder.length() > 0) textBuilder.append('\n');
            textBuilder.append("generatedExcludedCellsAmount=").append(
                    this.templateModel.getGeneratedExcludedCellsAmount());
            {
                final String excludedCellsAsJson =
                        this.templateModel.getExcludedCellsAsJson();
                if (null != excludedCellsAsJson) {
                    if (textBuilder.length() > 0) textBuilder.append('\n');
                    textBuilder.append(excludedCellsAsJson);
                }
            }

            if (textBuilder.length() > 0) textBuilder.append('\n');
            textBuilder.append(String.format("colNumbering=%b, rowNumbering=%b",
                    this.templateModel.getColNumbering(), this.templateModel.getRowNumbering()));

            if (textBuilder.length() > 0) textBuilder.append('\n');
            textBuilder.append(String.format(Locale.getDefault(),
                    "title=%s, type=%d, rows=%d, cols=%d", this.templateModel.getTitle(),
                    this.templateModel.getType().getCode(), this.templateModel.getRows(),
                    this.templateModel.getCols()));
        }

        if (null != this.textView) this.textView.setText(textBuilder.toString());
    }

    private NonNullOptionalFields nonNullOptionalFields() {
        return null == this.templateModel ?
                null == this.nonNullOptionalFieldsInstance ?
                        this.nonNullOptionalFieldsInstance =
                                new NonNullOptionalFields(
                                        this) :
                        this.nonNullOptionalFieldsInstance :
                this.templateModel.optionalFields();
    }

    private void addOptionalField() {
        if (null == this.addOptionalFieldAlertDialogTester) this.addOptionalFieldAlertDialogTester =
                new AddOptionalFieldAlertDialogTester(
                        this.activity(), this);
        this.addOptionalFieldAlertDialogTester.test(this.nonNullOptionalFields());
    }

    private void checkAndAddOptionalField() {
        if (null == this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog =
                    new CheckAndAddOptionalFieldsAlertDialog(
                            this.activity(), this);
        this.checkAndAddOptionalFieldsAlertDialog.show(this.nonNullOptionalFields());
    }

    @NonNull
    private ExcludedRowsOrColsAlertDialogTester
    excludedRowsOrColsAlertDialogTester() {
        if (null == this.excludedRowsOrColsAlertDialogTesterInstance)
            this.excludedRowsOrColsAlertDialogTesterInstance =
                    new ExcludedRowsOrColsAlertDialogTester(
                            this.activity(), this.templateModel);
        return this.excludedRowsOrColsAlertDialogTesterInstance;
    }

    private void excludeRows() {
        this.excludedRowsOrColsAlertDialogTester().testExcludedRows();
    }

    private void excludeCols() {
        this.excludedRowsOrColsAlertDialogTester().testExcludedCols();
    }

    private void setGeneratedAmount() {
        if (null == this.generatedExcludedCellsAlertDialogTester)
            this.generatedExcludedCellsAlertDialogTester =
                    new GeneratedExcludedCellsAlertDialogTester(
                            this.activity(), this.templateModel);
        this.generatedExcludedCellsAlertDialogTester.testGeneratedExcludedCells();
    }

    private void exclude() {
        if (null == this.excludeAlertDialogTester) this.excludeAlertDialogTester =
                new ExcludeAlertDialogTester(
                        this.activity(), this.requestCode, this.templateModel);
        this.excludeAlertDialogTester.testExclude();
    }

    private void setNumbering() {
        if (null == this.setNumberingAlertDialogTester) this.setNumberingAlertDialogTester =
                new SetNumberingAlertDialogTester(
                        this.activity(), this.templateModel);
        this.setNumberingAlertDialogTester.testSetNumbering();
    }

    private void setExcludesOptionalFieldsNumbering() {
        if (null == this.setExcludesOptionalFieldsNumberingAlertDialogTester)
            this.setExcludesOptionalFieldsNumberingAlertDialogTester = new
                    SetExcludesOptionalFieldsNumberingAlertDialogTester(
                    this.activity(), this.requestCode, this.templateModel);
        this.setExcludesOptionalFieldsNumberingAlertDialogTester
                .testSetExcludesOptionalFieldsNumbering();
    }

    private void assignTitleRowsCols() {
        if (null == this.assignTitleRowsColsAlertDialogTester)
            this.assignTitleRowsColsAlertDialogTester =
                    new AssignTitleRowsColsAlertDialogTester(
                            this.activity(), this.templateModel, this);
        this.assignTitleRowsColsAlertDialogTester.testAssignTitleRowsCols();
    }
    // endregion

    private void createTemplate() {
        if (null == this.templateCreator)
            this.templateCreator = new TemplateCreator(
                    this.activity(), this.requestCode, this);
        this.templateCreator.create();
    }

    // region Overridden Methods
    @Override
    public void configure() {
        this.setTitle(R.string.TestAlertDialogTitle);
        {
            final View view = this.inflate(R.layout.test);
            if (null != view) {
                this.textView = view.findViewById(R.id.textView);
                {
                    final Button addOptionalFieldButton = view.findViewById(
                            R.id.addOptionalFieldButton);
                    if (null != addOptionalFieldButton) addOptionalFieldButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    TestAlertDialog.this.addOptionalField();
                                }
                            });
                }
                {
                    final Button checkAndAddOptionalFieldButton = view.findViewById(
                            R.id.checkAndAddOptionalFieldButton);
                    if (null != checkAndAddOptionalFieldButton)
                        checkAndAddOptionalFieldButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        TestAlertDialog.this.checkAndAddOptionalField();
                                    }
                                });
                }
                {
                    final Button refreshTextButton = view.findViewById(
                            R.id.refreshTextButton);
                    if (null != refreshTextButton) refreshTextButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    TestAlertDialog.this.refreshText();
                                }
                            });
                }
                {
                    final Button excludeRowsButton = view.findViewById(
                            R.id.excludeRowsButton);
                    if (null != excludeRowsButton) excludeRowsButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    TestAlertDialog.this.excludeRows();
                                }
                            });

                }
                {
                    final Button excludeColsButton = view.findViewById(
                            R.id.excludeColsButton);
                    if (null != excludeColsButton) excludeColsButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    TestAlertDialog.this.excludeCols();
                                }
                            });

                }
                {
                    final Button setGeneratedAmountButton = view.findViewById(
                            R.id.setGeneratedAmountButton);
                    if (null != setGeneratedAmountButton)
                        setGeneratedAmountButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        TestAlertDialog.this.setGeneratedAmount();
                                    }
                                });
                }
                {
                    final Button excludeButton = view.findViewById(
                            R.id.excludeButton);
                    if (null != excludeButton) excludeButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    TestAlertDialog.this.exclude();
                                }
                            });
                }
                {
                    final Button setNumberingButton = view.findViewById(
                            R.id.setNumberingButton);
                    if (null != setNumberingButton) setNumberingButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    TestAlertDialog.this.setNumbering();
                                }
                            });
                }
                {
                    final Button setExcludesOptionalFieldsNumberingButton =
                            view.findViewById(R.id.setExcludesOptionalFieldsNumberingButton);
                    if (null != setExcludesOptionalFieldsNumberingButton)
                        setExcludesOptionalFieldsNumberingButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        TestAlertDialog
                                                .this.setExcludesOptionalFieldsNumbering();
                                    }
                                });
                }
                {
                    final Button assignTitleRowsColsButton = view.findViewById(
                            R.id.assignTitleRowsColsButton);
                    if (null != assignTitleRowsColsButton)
                        assignTitleRowsColsButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {
                                        TestAlertDialog.this.assignTitleRowsCols();
                                    }
                                });
                }
                {
                    final Button createTemplateButton = view.findViewById(
                            R.id.createTemplateButton);
                    if (null != createTemplateButton) createTemplateButton.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    TestAlertDialog.this.createTemplate();
                                }
                            });
                }
            }
            this.setView(view);
        }
        this.setOKPositiveButton();
    }

    // region org.wheatgenetics.coordinate.optionalField.AddOptionalFieldAlertDialogTester.Handler Overridden Method
    @Override
    public void handleAddOptionalFieldDone() {
        this.refreshText();
    }
    // endregion

    // region org.wheatgenetics.coordinate.optionalField.CheckAndAddOptionalFieldsAlertDialog.Handler Overridden Method
    @Override
    public void showCheckAndAddOptionalFieldsAlertDialogAgain() {
        this.refreshText();
        if (null != this.checkAndAddOptionalFieldsAlertDialog)
            this.checkAndAddOptionalFieldsAlertDialog.show(this.nonNullOptionalFields());
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.AssignTitleRowsColsAlertDialogTester.Handler Overridden Method
    @Override
    public void handleAssignDone() {
        this.refreshText();
    }
    // endregion

    // region org.wheatgenetics.coordinate.tc.TemplateCreator.Handler Overridden Method
    @Override
    public void handleTemplateCreated(@NonNull final TemplateModel templateModel) {
        this.templateModel = templateModel;
        this.refreshText();
    }
    // endregion

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.activity().getString(resId);
    }

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.activity().getResources().getQuantityString(resId, quantity, formatArgs);
    }
    // endregion
    // endregion
}