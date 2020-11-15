package com.lkpc.android.app.glory.ui.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lkpc.android.app.glory.data.NoteDatabase
import com.lkpc.android.app.glory.entity.Note

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val adapter = NoteAdapter()

    fun getAllNotes(): LiveData<List<Note>> {
        val db = NoteDatabase.getDatabase(context = context)
        return db.noteDao().getAll()
    }
}