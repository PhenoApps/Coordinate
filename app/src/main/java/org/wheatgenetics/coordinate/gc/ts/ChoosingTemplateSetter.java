package org.wheatgenetics.coordinate.gc.ts;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.SelectAlertDialog;
import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.model.Model;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.model.TemplateModels;
import org.wheatgenetics.coordinate.tc.TemplateCreator;
import org.wheatgenetics.javalib.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ChoosingTemplateSetter extends TemplateSetter {
    // region Fields
    // region Constructor Fields
    @Types.RequestCode
    private final int requestCode;
    @NonNull
    private final
    ChoosingTemplateSetter.Handler handler;
    private TemplatesTable templatesTableInstance = null;// ll
    // endregion
    private TemplateCreator templateCreatorInstance = null;  // ll
    private TemplateModels templateModels = null;
    private SelectAlertDialog
            templateChoiceAlertDialogInstance = null;                                       // lazy load

    public ChoosingTemplateSetter(final Activity activity,
                                  @Types.RequestCode final int requestCode,
                                  @NonNull final
                                  ChoosingTemplateSetter.Handler handler) {
        super(activity);
        this.requestCode = requestCode;
        this.handler = handler;
    }
    // endregion

    // region Private Methods
    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.activity());
        return this.templatesTableInstance;
    }

    // region templateCreator Private Methods
    private void handleTemplateCreated(@NonNull final TemplateModel templateModel) {
        final long templateId = this.templatesTable().insert(templateModel);
        if (Model.illegal(templateId))
            org.wheatgenetics.coordinate.Utils.alert(this.activity(),
                    R.string.ChoosingTemplateSetterAlertMessage);
        else
            this.handler.handleTemplateSet(templateId);
    }

    @NonNull
    private TemplateCreator templateCreator() {
        if (null == this.templateCreatorInstance) this.templateCreatorInstance =
                new TemplateCreator(this.activity(),
                        this.requestCode, new TemplateCreator.Handler() {
                    @Override
                    public void handleTemplateCreated(@NonNull final TemplateModel templateModel) {
                        ChoosingTemplateSetter
                                .this.handleTemplateCreated(templateModel);
                    }
                });
        return this.templateCreatorInstance;
    }
    // endregion

    // region templateChoiceAlertDialog Private Methods
    private void createTemplate() {
        this.templateCreator().create();
    }

    private void chooseExisting(@IntRange(from = 0) final int which) {
        if (null != this.templateModels) {
            final TemplateModel templateModel =
                    this.templateModels.get(which);
            if (null != templateModel) this.handler.handleTemplateSet(templateModel.getId());
            this.templateModels = null;
        }
    }

    @NonNull
    private SelectAlertDialog templateChoiceAlertDialog() {
        if (null == this.templateChoiceAlertDialogInstance) this.templateChoiceAlertDialogInstance =
                new SelectAlertDialog(this.activity(),
                        new SelectAlertDialog.Handler() {
                            @Override
                            public void select(final int which) {
                                if (which < 0)
                                    throw new IllegalArgumentException();
                                else if (which == 0)
                                    ChoosingTemplateSetter.this.createTemplate();
                                else
                                    ChoosingTemplateSetter.this.chooseExisting(which - 1);
                            }
                        });
        return this.templateChoiceAlertDialogInstance;
    }

    private void showTemplateChoiceAlertDialog() {
        @SuppressWarnings({"CStyleArrayDeclaration"}) @NonNull final String items[];
        {
            @NonNull final String firstItem =
                    this.activity().getString(
                            R.string.TemplateChoiceAlertDialogNewItem);

            this.templateModels = this.templatesTable().load();
            if (null == this.templateModels)
                items = Utils.stringArray(firstItem);
            else {
                @Nullable final String[] titles =
                        this.templateModels.titles();
                if (null == titles)
                    items = Utils.stringArray(firstItem);
                else if (titles.length <= 0)
                    items = Utils.stringArray(firstItem);
                else {
                    // noinspection Convert2Diamond
                    final ArrayList<String> arrayList =
                            new ArrayList<String>(
                                    1 + titles.length);
                    arrayList.add(firstItem);
                    arrayList.addAll(Arrays.asList(titles));
                    arrayList.toArray(items = new String[arrayList.size()]);
                }
            }
        }
        this.templateChoiceAlertDialog().show(
                R.string.TemplateChoiceAlertDialogTitle, items);
    }
    // endregion
    // endregion

    // region Public Methods
    public void set() {
        this.showTemplateChoiceAlertDialog();
    }

    public void continueExcluding(final Bundle bundle) {
        this.templateCreator().continueExcluding(bundle);
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler {
        public abstract void handleTemplateSet(
                @IntRange(from = 1) long templateId);
    }
    // endregion
}