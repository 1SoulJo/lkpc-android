package com.lkpc.android.app.glory.ui.note

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkpc.android.app.glory.R
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.title_area.*

class NoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        // title
        ta_action_bar_title.text = getString(R.string.my_notes)

        // back button
        ta_btn_back.setOnClickListener {
            finish()
        }

        val viewModel: NoteViewModel by viewModels()
        rv_note_list.layoutManager = LinearLayoutManager(this)
        rv_note_list.adapter = viewModel.adapter
        viewModel.getAllNotes().observe(this, { notes ->
            if (notes.isNotEmpty()) {
                empty_text_area.visibility = View.GONE
            }
            (rv_note_list.adapter as NoteAdapter).swapData(notes)
        })
    }
}