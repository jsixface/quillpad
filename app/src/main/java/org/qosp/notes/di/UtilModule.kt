package org.qosp.notes.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import org.qosp.notes.App
import org.qosp.notes.BuildConfig
import org.qosp.notes.components.MediaStorageManager
import org.qosp.notes.components.backup.BackupManager
import org.qosp.notes.components.workers.BinCleaningWorker
import org.qosp.notes.components.workers.SyncWorker
import org.qosp.notes.data.sync.core.SyncManager
import org.qosp.notes.ui.reminders.ReminderManager
import org.qosp.notes.ui.utils.ConnectionManager

object UtilModule {

    val module = module {
        single { MediaStorageManager(androidContext(), noteRepository = get(), App.MEDIA_FOLDER) }
        single { ReminderManager(androidContext(), reminderRepository = get()) }
        single {
            SyncManager(
                preferenceRepository = get(),
                idMappingRepository = get(),
                ConnectionManager(androidContext()),
                androidContext(),
                nextcloudBackend = get(),
                storageBackend = get(),
                (androidApplication() as App).syncingScope
            )
        }
        single {
            BackupManager(
                BuildConfig.VERSION_CODE,
                noteRepository = get(),
                notebookRepository = get(),
                tagRepository = get(),
                reminderRepository = get(),
                idMappingRepository = get(),
                reminderManager = get(),
                androidContext()
            )
        }
        worker {
            SyncWorker(
                context = get(),
                params = get(),
                preferenceRepository = get(),
                syncManager = get(),
            )
        }
        worker {
            BinCleaningWorker(
                context = get(),
                params = get(),
                preferenceRepository = get(),
                noteRepository = get(),
                mediaStorageManager = get(),
            )
        }
    }

}
