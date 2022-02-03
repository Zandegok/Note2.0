package com.example.note20

import androidx.lifecycle.*
import kotlinx.coroutines.*

class NoteVM(private val repository: NoteRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Note>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}