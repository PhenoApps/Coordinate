package org.wheatgenetics.coordinate.preference

class GeneralKeys private constructor() {

    companion object {

        private const val COORDINATE_PREFIX = "org.wheatgenetics.coordinate."


        /**
         * Settings
         */
        // Collection Preferences
        const val DIRECTION = COORDINATE_PREFIX + "DIRECTION"
        const val NOTIFICATION_SOUNDS = COORDINATE_PREFIX + "NOTIFICATION_SOUNDS"
        const val UNIQUE_VALUES = COORDINATE_PREFIX + "UNIQUE_VALUES"
        const val UNIQUE_OPTIONS = COORDINATE_PREFIX + "UNIQUE_OPTIONS"

        // Layout Preferences
        const val SCALING = COORDINATE_PREFIX + "SCALING"

        // Export Preferences
        const val PROJECT_EXPORT = COORDINATE_PREFIX + "PROJECT_EXPORT"
        const val SHARE_EXPORTS = COORDINATE_PREFIX + "SHARE_EXPORTS"

        // Storage Preferences
        const val RESET_DATABASE = COORDINATE_PREFIX + "RESET_DATABASE"
        const val IMPORT_DATABASE = COORDINATE_PREFIX + "IMPORT_DATABASE"
        const val EXPORT_DATABASE = COORDINATE_PREFIX + "EXPORT_DATABASE"

    }
}