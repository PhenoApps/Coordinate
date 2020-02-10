package org.wheatgenetics.coordinate.gc;

/**
 * Uses:
 * android.app.Activity
 *
 * androidx.annotation.IntRange
 * androidx.annotation.NonNull
 * androidx.annotation.Nullable
 *
 * org.wheatgenetics.coordinate.Types.RequestCode
 *
 * org.wheatgenetics.coordinate.model.ProjectModel
 *
 * org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter
 * org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter.Handler
 *
 * org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
 * org.wheatgenetics.coordinate.gc.ts.StatefulTemplateSetter
 * org.wheatgenetics.coordinate.gc.ts.StatefulTemplateSetter.Handler
 *
 * org.wheatgenetics.coordinate.gc.GridCreator
 * org.wheatgenetics.coordinate.gc.GridCreator.Handler
 */
public class StatefulGridCreator extends org.wheatgenetics.coordinate.gc.GridCreator
{
    @java.lang.SuppressWarnings({"UnnecessaryInterfaceModifier"})
    public interface Handler extends org.wheatgenetics.coordinate.gc.GridCreator.Handler
    {
        public abstract void loadProjectModel(
            @androidx.annotation.IntRange(from = 1) long projectId);
        public abstract void clearProjectModel();
    }

    // region Fields
    private org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter
        choosingTemplateSetterInstance = null;                                          // lazy load
    private org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter
        statefulProjectSetterInstance = null;                                           // lazy load
    // endregion

    // region Private Methods
    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.StatefulGridCreator.Handler statefulHandler()
    {
        final org.wheatgenetics.coordinate.gc.GridCreator.Handler handler = this.handler();
        if (null == handler)
            throw new java.lang.AssertionError();
        else
            return (org.wheatgenetics.coordinate.gc.StatefulGridCreator.Handler) handler;
    }

    private void clearProjectModel()
    { this.statefulHandler().clearProjectModel(); }

    private void loadProjectModel(@androidx.annotation.IntRange(from = 1) final long projectId)
    { this.statefulHandler().loadProjectModel(projectId); }

    @androidx.annotation.NonNull
    private org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter statefulProjectSetter()
    {
        if (null == this.statefulProjectSetterInstance) this.statefulProjectSetterInstance =
            new org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter(this.activity(),
                new org.wheatgenetics.coordinate.gc.ps.StatefulProjectSetter.Handler()
                {
                    @java.lang.Override public void clearProjectModel()
                    {
                        org.wheatgenetics.coordinate.gc
                            .StatefulGridCreator.this.clearProjectModel();
                    }

                    @java.lang.Override public void loadProjectModel(
                    @androidx.annotation.IntRange(from = 1) final long projectId)
                    {
                        org.wheatgenetics.coordinate.gc.StatefulGridCreator.this.loadProjectModel(
                            projectId);
                    }

                    @java.lang.Override public void handleNoProjectSet()
                    {
                        org.wheatgenetics.coordinate.gc
                            .StatefulGridCreator.this.handleNoProjectSet();
                    }

                    @java.lang.Override public void handleProjectSet(
                    @androidx.annotation.IntRange(from = 1) final long    projectId,
                                                            final boolean existing )
                    {
                        org.wheatgenetics.coordinate.gc.StatefulGridCreator.this.handleProjectSet(
                            projectId, existing);
                    }
                });
        return this.statefulProjectSetterInstance;
    }
    // endregion

    public StatefulGridCreator(                     final android.app.Activity activity   ,
    @org.wheatgenetics.coordinate.Types.RequestCode final int                  requestCode,
    @androidx.annotation.NonNull                    final
        org.wheatgenetics.coordinate.gc.StatefulGridCreator.Handler handler)
    { super(activity, requestCode, handler); }

    // region Overridden Methods
    @java.lang.Override @androidx.annotation.NonNull
    org.wheatgenetics.coordinate.gc.ts.ChoosingTemplateSetter choosingTemplateSetter()
    {
        if (null == this.choosingTemplateSetterInstance) this.choosingTemplateSetterInstance =
            new org.wheatgenetics.coordinate.gc.ts.StatefulTemplateSetter(
                this.activity(), this.requestCode(),
                new org.wheatgenetics.coordinate.gc.ts.StatefulTemplateSetter.Handler()
                {
                    @java.lang.Override public void handleTemplateSet(
                    @androidx.annotation.IntRange(from = 1) final long templateId)
                    {
                        org.wheatgenetics.coordinate.gc.StatefulGridCreator
                            .this.handleTemplateSet(templateId);
                    }
                });
        return this.choosingTemplateSetterInstance;
    }

    /*@java.lang.Override boolean choosingTemplateSetterIsNotNull()                 // TODO: Remove?
    { return null != this.choosingTemplateSetterInstance; }*/
    // endregion

    public void create(@androidx.annotation.Nullable
    final org.wheatgenetics.coordinate.model.ProjectModel projectModel)
    { this.statefulProjectSetter().set(projectModel); }
}