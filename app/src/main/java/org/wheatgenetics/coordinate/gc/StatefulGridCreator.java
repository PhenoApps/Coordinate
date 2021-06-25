package org.wheatgenetics.coordinate.gc;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.wheatgenetics.coordinate.Types;
import org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter;
import org.wheatgenetics.coordinate.model.ProjectModel;

public class StatefulGridCreator extends GridCreator {
    private StatefulProjectSetter
            statefulProjectSetterInstance = null;                                           // lazy load

    public StatefulGridCreator(final Activity activity,
                               @Types.RequestCode final int requestCode,
                               @NonNull final
                               StatefulGridCreator.Handler handler) {
        super(activity, requestCode, handler);
    }

    // region Private Methods
    @NonNull
    private StatefulGridCreator.Handler statefulHandler() {
        final GridCreator.Handler handler = this.handler();
        if (null == handler)
            throw new AssertionError();
        else
            return (StatefulGridCreator.Handler) handler;
    }

    private void clearProjectModel() {
        this.statefulHandler().clearProjectModel();
    }

    private void loadProjectModel(@IntRange(from = 1) final long projectId) {
        this.statefulHandler().loadProjectModel(projectId);
    }

    @NonNull
    private StatefulProjectSetter statefulProjectSetter() {
        if (null == this.statefulProjectSetterInstance) this.statefulProjectSetterInstance =
                new StatefulProjectSetter(this.activity(),
                        new StatefulProjectSetter.Handler() {
                            @Override
                            public void clearProjectModel() {
                                StatefulGridCreator.this.clearProjectModel();
                            }

                            @Override
                            public void loadProjectModel(
                                    @IntRange(from = 1) final long projectId) {
                                StatefulGridCreator.this.loadProjectModel(
                                        projectId);
                            }

                            @Override
                            public void handleNoProjectSet() {
                                StatefulGridCreator.this.handleNoProjectSet();
                            }

                            @Override
                            public void handleProjectSet(
                                    @IntRange(from = 1) final long projectId,
                                    final boolean existing) {
                                StatefulGridCreator.this.handleProjectSet(
                                        projectId, existing);
                            }
                        });
        return this.statefulProjectSetterInstance;
    }
    // endregion

    public void create(@Nullable final ProjectModel projectModel) {
        this.statefulProjectSetter().set(projectModel);
    }

    @SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends GridCreator.Handler {
        public abstract void loadProjectModel(
                @IntRange(from = 1) long projectId);

        public abstract void clearProjectModel();
    }
}