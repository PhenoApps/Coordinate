package org.wheatgenetics.coordinate.projects;

/**
 * Uses:
 * androidx.annotation.IntRange
 *
 * org.wheatgenetics.coordinate.viewmodel.ViewModel
 */
@java.lang.SuppressWarnings({"WeakerAccess"})
public class ProjectsViewModel extends org.wheatgenetics.coordinate.viewmodel.ViewModel
{
    private java.lang.String directoryName;

    // region Package Methods
    void setProjectIdAndDirectoryName(@androidx.annotation.IntRange(from = 1) final long projectId,
    final java.lang.String directoryName)
    { this.setId(projectId); this.directoryName = directoryName; }

    java.lang.String getDirectoryName() { return this.directoryName; }
    // endregion
}