package org.wheatgenetics.coordinate.preference

class GeneralKeys private constructor() {

    companion object {

        private const val COORDINATE_PREFIX = "org.wheatgenetics.coordinate."

        const val STORAGE_ASK_KEY = COORDINATE_PREFIX + "FIRST_ASK_DOCUMENT_TREE_SET"

        const val MIGRATE_ASK_KEY = COORDINATE_PREFIX + "MIGRATE_ASK_KEY"

        const val FIRST_LOAD_COMPLETE = COORDINATE_PREFIX + "FIRST_LOAD_COMPLETE"

        const val FROM_INTRO_AUTOMATIC = COORDINATE_PREFIX + "FROM_INTRO_AUTOMATIC"

        /**
         * App Intro
         */
        const val LOAD_SAMPLE_DATA = COORDINATE_PREFIX + "LOAD_SAMPLE_DATA"


        /**
         * Settings
         */
        // Collection Preferences
        const val DIRECTION = COORDINATE_PREFIX + "DIRECTION"
        const val UNIQUE_VALUES = COORDINATE_PREFIX + "UNIQUE_VALUES"
        const val UNIQUE_OPTIONS = COORDINATE_PREFIX + "UNIQUE_OPTIONS"
        // sounds
        const val NAVIGATION_SOUND = COORDINATE_PREFIX + "NAVIGATION_SOUND"
        const val GRID_FILLED_SOUND = COORDINATE_PREFIX + "GRID_FILLED_SOUND"
        const val DUPLICATE_ENTRY_SOUND = COORDINATE_PREFIX + "DUPLICATE_ENTRY_SOUND"

        // Layout Preferences
        const val SCALING = COORDINATE_PREFIX + "SCALING"

        // Export Preferences
        const val PROJECT_EXPORT = COORDINATE_PREFIX + "PROJECT_EXPORT"
        const val SHARE_EXPORTS = COORDINATE_PREFIX + "SHARE_EXPORTS"

        // Storage Preferences
        const val RESET_DATABASE = COORDINATE_PREFIX + "RESET_DATABASE"
        const val IMPORT_DATABASE = COORDINATE_PREFIX + "IMPORT_DATABASE"
        const val EXPORT_DATABASE = COORDINATE_PREFIX + "EXPORT_DATABASE"
        const val RELOAD_DATABASE = COORDINATE_PREFIX + "RELOAD_DATABASE"

        // Appearance Preferences
        const val TIPS = COORDINATE_PREFIX + "TIPS"

    }
}