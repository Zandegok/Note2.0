package com.example.note20;

import androidx.room.*;

import org.jetbrains.annotations.*;

import java.lang.System;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Dao()
public interface NoteDao {

    @NotNull()
    @Query(value = "SELECT * FROM notes_table ORDER BY id ASC")
    Flow<java.util.List<Note>> getAlphabetizedNotes();

    @Nullable()
    @Insert(onConflict = androidx.room.OnConflictStrategy.IGNORE)
    void insert(@NotNull() Note note);

    @Query(value = "DELETE FROM notes_table")
    int deleteAll();
}