package org.qosp.notes.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.qosp.notes.ui.ActivityViewModel
import org.qosp.notes.ui.archive.ArchiveViewModel
import org.qosp.notes.ui.attachments.dialog.AttachmentDialogViewModel
import org.qosp.notes.ui.deleted.DeletedViewModel
import org.qosp.notes.ui.editor.EditorViewModel
import org.qosp.notes.ui.main.MainViewModel
import org.qosp.notes.ui.notebooks.ManageNotebooksViewModel
import org.qosp.notes.ui.notebooks.dialog.NotebookDialogViewModel
import org.qosp.notes.ui.reminders.EditReminderViewModel
import org.qosp.notes.ui.search.SearchViewModel
import org.qosp.notes.ui.settings.SettingsViewModel
import org.qosp.notes.ui.sync.nextcloud.NextcloudViewModel
import org.qosp.notes.ui.tags.TagsViewModel
import org.qosp.notes.ui.tags.dialog.TagDialogViewModel

const val NO_SYNC = "NO_SYNC"

object UiModule {

    val module = module {
        viewModel {
            EditorViewModel(
                noteRepository = get(),
                notebookRepository = get(),
                syncManager = get(),
                preferenceRepository = get(),
            )
        }
        viewModel {
            ActivityViewModel(
                noteRepository = get(),
                notebookRepository = get(),
                preferenceRepository = get(),
                reminderRepository = get(),
                reminderManager = get(),
                tagRepository = get(),
                mediaStorageManager = get(),
                syncManager = get(),
            )
        }
        viewModel {
            DeletedViewModel(
                notesRepository = get(),
                mediaStorageManager = get(),
                preferenceRepository = get(),
                syncManager = get(),
            )
        }
        viewModel {
            MainViewModel(
                noteRepository = get(),
                notebookRepository = get(),
                preferenceRepository = get(),
                syncManager = get(),
            )
        }
        viewModel {
            EditReminderViewModel(
                reminderRepository = get(),
                reminderManager = get(),
                preferenceRepository = get(),
            )
        }
        viewModel {
            SearchViewModel(
                noteRepository = get(),
                notebookRepository = get(),
                preferenceRepository = get(),
                syncManager = get(),
            )
        }
        viewModel {
            SettingsViewModel(
                preferenceRepository = get(),
                syncManager = get(),
            )
        }
        viewModel {
            NextcloudViewModel(
                preferenceRepository = get(),
                syncManager = get(),
            )
        }
        viewModel { TagDialogViewModel(tagRepository = get()) }
        viewModel { TagsViewModel( tagRepository = get()) }
        viewModel { AttachmentDialogViewModel(noteRepository = get()) }
        viewModel { ManageNotebooksViewModel(notebookRepository = get()) }
        viewModel { NotebookDialogViewModel(notebookRepository = get()) }
        viewModel {
            ArchiveViewModel(
                noteRepository = get(),
                preferenceRepository = get(),
                syncManager = get(),
            )
        }
    }
}
