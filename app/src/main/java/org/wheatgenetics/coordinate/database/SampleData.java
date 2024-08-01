package org.wheatgenetics.coordinate.database;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.annotation.StringRes;

import org.wheatgenetics.coordinate.R;
import org.wheatgenetics.coordinate.StringGetter;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.gc.GridCreator;
import org.wheatgenetics.coordinate.gc.StatelessGridCreator;
import org.wheatgenetics.coordinate.ge.GridExportPreprocessor;
import org.wheatgenetics.coordinate.ge.GridExporter;
import org.wheatgenetics.coordinate.model.JoinedGridModel;
import org.wheatgenetics.coordinate.model.ProjectModel;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;
import org.wheatgenetics.coordinate.model.TemplateModels;
import org.wheatgenetics.coordinate.pc.ProjectCreator;
import org.wheatgenetics.coordinate.model.TemplateModel;
import org.wheatgenetics.coordinate.model.TemplateModels;
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields;

import java.util.Iterator;

public class SampleData implements StringGetter {

    private Context mContext;

    public SampleData(Context context) {
        this.mContext = context;
    }

    public void insertSampleData() {
        TemplatesTable templatesTableInstance = new TemplatesTable(mContext);
        GridsTable gridsTableInstance = new GridsTable(mContext);
        ProjectsTable projectsTableInstance = new ProjectsTable(mContext);
        EntriesTable entriesTableInstance = new EntriesTable(mContext);

        //save a new project and set the first load flag to false
        String sampleProjectName = mContext.getString(R.string.sample_project_name);
        long pid = projectsTableInstance.insert(new ProjectModel(sampleProjectName, this));

        //insert default template grids into sample project
        String seedTrayDefaultName = mContext.getString(R.string.SeedDefaultTemplateTitle);
        String dnaDefaultName = mContext.getString(R.string.DNADefaultTemplateTitle);
        TemplateModel seedTrayTemplate = null;
        TemplateModel dnaTemplate = null;
        TemplateModels templatesModel = templatesTableInstance.load();
        if (templatesModel != null) {
            Iterator<TemplateModel> templates = templatesModel.iterator();
            for (Iterator<TemplateModel> it = templates; it.hasNext(); ) {
                TemplateModel model = it.next();
                if (model.isDefaultTemplate()) {
                    if (model.getTitle().equals(seedTrayDefaultName)) {
                        seedTrayTemplate = model;
                    } else if (model.getTitle().equals(dnaDefaultName)) {
                        dnaTemplate = model;
                    }
                }
            }
        }

        String sampleGridSeedTrayName = mContext.getString(R.string.sample_grid_seed_tray_name);
        String sampleGridDnaName = mContext.getString(R.string.sample_grid_dna_name);
        String seedTrayFieldId = mContext.getString(R.string.NonNullOptionalFieldsTrayIDFieldName);
        String dnaFieldId = mContext.getString(R.string.NonNullOptionalFieldsPlateIDFieldName);

        if (dnaTemplate != null) {
            NonNullOptionalFields fields = dnaTemplate.optionalFields();
            if (fields != null && fields.contains(dnaFieldId)) {
                fields.set(dnaFieldId, sampleGridDnaName);
            }
            JoinedGridModel jgm = new JoinedGridModel(pid, null, fields, this, dnaTemplate);
            long gid = gridsTableInstance.insert(jgm);
            jgm.setId(gid);
            gridsTableInstance.update(jgm);
            jgm.makeEntryModels();
            entriesTableInstance.insert(jgm.getEntryModels());
        }

        if (seedTrayTemplate != null) {
            NonNullOptionalFields fields = seedTrayTemplate.optionalFields();
            if (fields != null && fields.contains(seedTrayFieldId)) {
                fields.set(seedTrayFieldId, sampleGridSeedTrayName);
            }
            JoinedGridModel jgm = new JoinedGridModel(pid, null, fields, this, seedTrayTemplate);
            long gid = gridsTableInstance.insert(jgm);
            jgm.setId(gid);
            gridsTableInstance.update(jgm);
            jgm.makeEntryModels();
            entriesTableInstance.insert(jgm.getEntryModels());
        }
    }

    @Override
    @Nullable
    public String get(
            @StringRes final int resId) {
        Log.d("TAG", "get: ");
        return this.mContext.getString(resId);
    }

    @Override
    @NonNull
    public String getQuantity(
            @PluralsRes final int resId,
            @IntRange(from = 0) final int quantity,
            @Nullable final Object... formatArgs)
            throws Resources.NotFoundException {
        return this.mContext.getResources().getQuantityString(resId, quantity, formatArgs);
    }
}
