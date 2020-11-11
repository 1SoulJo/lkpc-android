package com.lkpc.android.app.glory.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lkpc.android.app.glory.constants.ContentType

@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "content_type")
    val type: Int?,

    @ColumnInfo(name = "content_id")
    val contentId: String?,

    @ColumnInfo(name = "note_content")
    val noteContent: String?
)