package com.lkpc.android.app.glory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lkpc.android.app.glory.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM note_table WHERE content_id==(:contentId)")
    suspend fun loadContentById(contentId: String): List<Note>

    @Insert
    suspend fun insertAll(vararg notes: Note)

    @Delete
    suspend fun delete(note: Note)
}