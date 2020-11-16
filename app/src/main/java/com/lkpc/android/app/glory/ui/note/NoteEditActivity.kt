package com.lkpc.android.app.glory.ui.note

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.constants.ContentType
import com.lkpc.android.app.glory.data.NoteDatabase
import com.lkpc.android.app.glory.entity.Note
import kotlinx.android.synthetic.main.activity_note_edit.*
import kotlinx.android.synthetic.main.title_area.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class NoteEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        // title
        ta_action_bar_title.text = getString(R.string.my_notes)

        // content
        note_title.setText(intent.getStringExtra("title"))
        note_content.setText(intent.getStringExtra("content"))

        // back button
        ta_btn_back.setOnClickListener {
            finish()
        }

        // save button
        val id = intent.getIntExtra("id", -1)
        ta_btn_save.visibility = View.VISIBLE
        ta_btn_save.setOnClickListener {
            GlobalScope.launch {
                val note = Note(
                    type = intent.getStringExtra("type"),
                    contentId = intent.getStringExtra("contentId"),
                    title = note_title.text.toString(),
                    content = note_content.text.toString(),
                    lastModified = Date().time
                )
                if (id > -1) {
                    note.id = id
                }

                val db = NoteDatabase.getDatabase(context = applicationContext)
                val saved = db.noteDao().insert(note)
                setResult(saved.toInt())
                finish()
            }
        }
    }
}