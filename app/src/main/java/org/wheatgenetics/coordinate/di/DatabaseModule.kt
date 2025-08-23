package org.wheatgenetics.coordinate.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.wheatgenetics.coordinate.data.CoordinateDatabase
import org.wheatgenetics.coordinate.data.dao.EntryDao
import org.wheatgenetics.coordinate.data.dao.GridDao
import org.wheatgenetics.coordinate.data.dao.ProjectDao
import org.wheatgenetics.coordinate.data.dao.TemplateDao
import org.wheatgenetics.coordinate.data.migrations.MigrationV2UseRoom
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCoordinateDatabase(@ApplicationContext context: Context): CoordinateDatabase {
        return Room.databaseBuilder(
            context,
            CoordinateDatabase::class.java,
            CoordinateDatabase.DATABASE_NAME
        )
            .addMigrations(MigrationV2UseRoom())
            .build()
    }

    @Provides
    fun provideProjectDao(database: CoordinateDatabase): ProjectDao {
        return database.projectDao()
    }

    @Provides
    fun provideTemplateDao(database: CoordinateDatabase): TemplateDao {
        return database.templateDao()
    }

    @Provides
    fun provideGridDao(database: CoordinateDatabase): GridDao {
        return database.gridDao()
    }

    @Provides
    fun provideEntryDao(database: CoordinateDatabase): EntryDao =
        database.entryDao()
}