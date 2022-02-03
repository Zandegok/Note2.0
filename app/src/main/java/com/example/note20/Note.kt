package com.example.note20

import androidx.room.*


@Entity(tableName="notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Int=0,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var body: String
)
