package com.lkpc.android.app.glory.ui.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.data.NoteDatabase
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_note_detail.*
import kotlinx.android.synthetic.main.title_area.*
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        // back button
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }

        val noteId = intent.getIntExtra("noteId", -1)
        val db = NoteDatabase.getDatabase(context = this)
        db.noteDao().loadById(noteId).observe(
            this, { note ->
                // title
                ab_title.text = note.title

                // main content
                note_detail_title.text = note.title
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA)
                val lastModified = dateFormat.format(note.lastModified)
                note_detail_last_modified.text = lastModified
                note_detail_content.text = note.content

                // edit button
                ab_btn_edit.visibility = View.VISIBLE
                ab_btn_edit.setOnClickListener {
                    val i = Intent(this, NoteEditActivity::class.java)
                    i.putExtra("id", noteId)
                    i.putExtra("type", note.type)
                    i.putExtra("title", note.title)
                    i.putExtra("content", note.content)
                    i.putExtra("contentId", note.contentId)
                    i.putExtra("lastModified", lastModified)

                    startActivity(i)
                }
            }
        )
    }
}