package com.lkpc.android.app.glory.ui.note

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.Note
import kotlinx.android.synthetic.main.list_item_note.view.*
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var notes: List<Note> = mutableListOf()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var title: TextView = view.note_title
        var lastModified: TextView = view.last_modified
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA)
        val lastModified = dateFormat.format(Date(notes[position].lastModified!!))

        if (holder is ViewHolder) {
            holder.title.text = notes[position].title
            holder.lastModified.text = lastModified
        }

        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, NoteDetailActivity::class.java)
            i.putExtra("noteId", notes[position].id)
//            i.putExtra("title", notes[position].title)
//            i.putExtra("content", notes[position].content)
//            i.putExtra("contentId", notes[position].contentId)
//            i.putExtra("lastModified", lastModified)
            holder.itemView.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun swapData(newData: List<Note>) {
        notes = newData
        notifyDataSetChanged()
    }

}