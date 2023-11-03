package org.qosp.notes.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.qosp.notes.data.AppDatabase

object DatabaseModule {

    val dbModule = module {
        single {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.DB_NAME)
                // we don't want to silently wipe user data in case DB migration fails,
                // rather let the app crash
                // .fallbackToDestructiveMigration()
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .build()
        }
    }
}
