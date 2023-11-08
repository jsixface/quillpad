package org.qosp.notes.ui.notebooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.qosp.notes.data.model.Notebook
import org.qosp.notes.data.repo.NotebookRepository

class ManageNotebooksViewModel(private val notebookRepository: NotebookRepository) : ViewModel() {
    fun deleteNotebooks(vararg notebooks: Notebook) {
        viewModelScope.launch(Dispatchers.IO) {
            notebookRepository.delete(*notebooks)
        }
    }
}
