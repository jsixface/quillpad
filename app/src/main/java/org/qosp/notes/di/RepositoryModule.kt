package org.qosp.notes.di

import org.koin.dsl.module
import org.qosp.notes.data.AppDatabase
import org.qosp.notes.data.repo.*

const val NO_SYNC = "NO_SYNC"

object RepositoryModule {

    val module = module {
        single { IdMappingRepository(get<AppDatabase>().idMappingDao) }

        single {
            TagRepository(
                get<AppDatabase>().tagDao,
                get<AppDatabase>().noteTagDao,
                noteRepository = get(),
                syncManager = get()
            )
        }

        single {
            NotebookRepository(get<AppDatabase>().notebookDao, noteRepository = get(), syncManager = get())
        }

        single {
            NoteRepository(
                get<AppDatabase>().noteDao,
                get<AppDatabase>().idMappingDao,
                get<AppDatabase>().reminderDao,
                syncManager = get()
            )
        }

        single {
            ReminderRepository(get<AppDatabase>().reminderDao)
        }
    }
}
