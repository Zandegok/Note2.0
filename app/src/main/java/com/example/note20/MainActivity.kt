package com.example.note20

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainActivity : AppCompatActivity() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy {NoteRoomDatabase.getDatabase(this,applicationScope)}
    val repository by lazy {NoteRepository(database.noteDao())}
    private val wordViewModel: NoteVM by viewModels {
        NoteViewModelFactory(repository)
    }
    private lateinit var editNoteView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editNoteView = findViewById(R.id.edit_word)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editNoteView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val note = editNoteView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, note)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NoteRVAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.allWords.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}