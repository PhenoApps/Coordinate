package org.wheatgenetics.coordinate.ti;

import android.app.Activity;
import android.content.res.Resources;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;
import androidx.documentfile.provider.DocumentFile;

import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.TemplatesDir;
import org.wheatgenetics.coordinate.Utils;
import org.wheatgenetics.coordinate.database.TemplatesTable;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.phenoapps.permissions.Dir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TemplateImporter implements StringGetter {
    // region Fields
    @NonNull
    private final Activity activity;
    private final int requestCode;
    @Nullable
    private final
    TemplateImporter.Adapter adapter;
    private String fileName;
    private TemplatesTable templatesTableInstance = null;// ll
    // region Constructors
    public TemplateImporter(
            @NonNull final Activity activity,
            final int requestCode,
            @Nullable final TemplateImporter.Adapter
                    adapter) {
        super();
        this.activity = activity;
        this.requestCode = requestCode;
        this.adapter = adapter;
    }
    // endregion

    public TemplateImporter(@NonNull final Activity activity,
                            final int requestCode) {
        this(activity, requestCode, null);
    }

    // region Private Methods
    private void showLongToast(final String text) {
        org.wheatgenetics.coordinate.Utils.showLongToast(this.activity, text);
    }

    @NonNull
    private TemplatesTable templatesTable() {
        if (null == this.templatesTableInstance) this.templatesTableInstance =
                new TemplatesTable(this.activity);
        return this.templatesTableInstance;
    }
    // endregion

    private void notifyDataSetChanged() {
        if (null != this.adapter) this.adapter.notifyDataSetChanged();
    }

    // region org.wheatgenetics.coordinate.StringGetter Overridden Methods
    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        return this.activity.getString(resId);
    }
    // endregion

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.activity.getResources().getQuantityString(resId, quantity, formatArgs);
    }

    // region Public Methods
    public void importTemplate() {
        File file;
        try {
            final TemplatesDir templatesDir =
                    Utils.templatesDir(             // throws IOException,
                            this.activity, this.requestCode);                        //  PermissionException
            file = templatesDir.makeFile(this.fileName);  // throws IOException, PermissionException
        } catch (final IOException | Dir.PermissionException e) {
            if (!(e instanceof Dir.PermissionRequestedException))
                this.showLongToast(e.getMessage());
            file = null;
        }

        if (null != file) {
            final boolean templateImported = this.templatesTable().insert(
                    TemplateModel.makeUserDefined(
                            file, this)) > 0;
            if (templateImported) this.notifyDataSetChanged();
        }
    }

    public void importTemplate(InputStream input) {

        boolean templateImported = templatesTable().insert(
                TemplateModel.makeUserDefined(input, this)) > 0;

        if (templateImported) this.notifyDataSetChanged();

    }

    // endregion

    public void importTemplate(final String fileName) {
        this.fileName = fileName;
        this.importTemplate();
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Adapter {
        public abstract void notifyDataSetChanged();
    }
    // endregion
}