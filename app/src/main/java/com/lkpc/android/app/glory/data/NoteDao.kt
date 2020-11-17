package com.lkpc.android.app.glory.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lkpc.android.app.glory.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY last_modified DESC")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE id==(:id) LIMIT 1")
    fun loadById(id: Int): LiveData<Note>

    @Query("SELECT * FROM note_table WHERE content_id==(:contentId) LIMIT 1")
    fun loadByContentId(contentId: String): LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE from note_table where id in (:idList)")
    suspend fun deleteAll(idList: List<Int>)
}