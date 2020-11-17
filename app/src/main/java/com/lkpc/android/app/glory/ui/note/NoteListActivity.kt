package com.lkpc.android.app.glory.ui.note

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkpc.android.app.glory.R
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)

        val viewModel: NoteViewModel by viewModels()
        rv_note_list.layoutManager = LinearLayoutManager(this)
        rv_note_list.adapter = viewModel.adapter
        viewModel.getAllNotes().observe(this, { notes ->
            if (notes.isEmpty()) {
                empty_text_area.visibility = View.VISIBLE
            }
            viewModel.adapter.swapData(notes)
        })

        // title
        ab_title.text = getString(R.string.my_notes)
        ab_btn_edit.visibility = View.VISIBLE
        ab_btn_edit.setOnClickListener {
            startActionMode(viewModel.callback)!!
        }

        // back button
        ab_btn_back.visibility = View.VISIBLE
        ab_btn_back.setOnClickListener {
            finish()
        }
    }
}