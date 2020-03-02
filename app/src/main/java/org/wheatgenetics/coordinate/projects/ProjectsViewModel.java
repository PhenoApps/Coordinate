package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * androidx.annotation.IntRange
 * androidx.lifecycle.ViewModel
 */
@java.lang.SuppressWarnings({"WeakerAccess"})
public class ProjectsViewModel extends androidx.lifecycle.ViewModel
{
    // region Fields
    @androidx.annotation.IntRange(from = 1) private long             projectId    ;
                                            private java.lang.String directoryName;
    // endregion

    // region Package Methods
    void setProjectIdAndDirectoryName(
    @androidx.annotation.IntRange(from = 1) final long             projectId    ,
                                            final java.lang.String directoryName)
    { this.projectId = projectId; this.directoryName = directoryName; }

    @androidx.annotation.IntRange(from = 1) long getProjectId    () { return this.projectId    ; }
                                java.lang.String getDirectoryName() { return this.directoryName; }
    // endregion
}