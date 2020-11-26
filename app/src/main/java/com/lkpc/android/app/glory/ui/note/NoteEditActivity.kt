package com.lkpc.android.app.glory.ui.note

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.data.NoteDatabase
import com.lkpc.android.app.glory.entity.Note
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_note_edit.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class NoteEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        // title
        ab_title.text = getString(R.string.my_notes)

        // content
        note_title.setText(intent.getStringExtra("title"))
        note_content.setText(intent.getStringExtra("content"))

        // back button
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            setResult(-1)
            finish()
        }

        // save button
        val id = intent.getIntExtra("id", -1)
        ab_btn_save.visibility = View.VISIBLE
        ab_btn_save.setOnClickListener {
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