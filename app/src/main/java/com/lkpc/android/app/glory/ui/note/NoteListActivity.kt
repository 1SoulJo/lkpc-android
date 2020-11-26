package com.lkpc.android.app.glory.ui.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkpc.android.app.glory.R
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.tool_bar.*

class NoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar_title.setText(R.string.my_notes)

        val viewModel: NoteViewModel by viewModels()
        rv_note_list.layoutManager = LinearLayoutManager(this)
        rv_note_list.adapter = viewModel.adapter
        viewModel.getAllNotes().observe(this, { notes ->
            if (notes.isEmpty()) {
                empty_text_area.visibility = View.VISIBLE
            }
            viewModel.adapter.swapData(notes)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu!!.findItem(R.id.note_menu_share).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

            R.id.note_menu_delete -> {
                val viewModel: NoteViewModel by viewModels()
                toolbar.startActionMode(viewModel.callback)!!
            }
        }

        return super.onOptionsItemSelected(item)
    }
}