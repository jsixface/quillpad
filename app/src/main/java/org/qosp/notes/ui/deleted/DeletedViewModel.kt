package org.qosp.notes.ui.deleted

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.qosp.notes.components.MediaStorageManager
import org.qosp.notes.data.repo.NoteRepository
import org.qosp.notes.data.sync.core.SyncManager
import org.qosp.notes.preferences.PreferenceRepository
import org.qosp.notes.ui.common.AbstractNotesViewModel

class DeletedViewModel(
    private val notesRepository: NoteRepository,
    private val mediaStorageManager: MediaStorageManager,
    preferenceRepository: PreferenceRepository,
    syncManager: SyncManager,
) : AbstractNotesViewModel(preferenceRepository, syncManager) {

    override val provideNotes = notesRepository::getDeleted

    fun permanentlyDeleteNotesInBin() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.permanentlyDeleteNotesInBin()
            mediaStorageManager.cleanUpStorage()
        }
    }
}
