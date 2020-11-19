package com.lkpc.android.app.glory.ui.note

import android.app.Application
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.data.NoteDatabase
import com.lkpc.android.app.glory.entity.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val adapter = NoteAdapter()

    val callback = object: ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode!!.menuInflater
            inflater.inflate(R.menu.action_mode_note_list, menu)

            adapter.setSelectionMode(true)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode!!.title = "0 ${context.getString(R.string.selected)}"
            return true
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when(item!!.itemId) {
                R.id.navigation_column -> {
                    GlobalScope.launch {
                        val db = NoteDatabase.getDatabase(context = context)
                        db.noteDao().deleteAll(adapter.selectedNotes)
                    }
                }
                R.id.navigation_home -> {

                }
            }
            mode!!.finish()

            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            adapter.setSelectionMode(false)
        }
    }

    fun getAllNotes(): LiveData<List<Note>> {
        val db = NoteDatabase.getDatabase(context = context)
        return db.noteDao().getAll()
    }
}