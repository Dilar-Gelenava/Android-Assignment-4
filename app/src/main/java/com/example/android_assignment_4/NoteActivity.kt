package com.example.android_assignment_4

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson


class NoteActivity : AppCompatActivity() {

    lateinit var noteLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_item)

        noteLayout = findViewById(R.id.noteLayout)
        noteLayout.setOnClickListener() {
            deleteNote(this)
        }
    }


    private fun deleteNote(view: NoteActivity) {
        val mPrefs = getPreferences(MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("notes", "[]")
        val notes: Array<Note> = gson.fromJson(json, Array<Note>::class.java)
        val noteId = intent.getIntExtra("noteId", 0)
        val newNotes = (notes.filter { it.id != noteId }).toTypedArray()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        val adapter = NoteRecyclerAdapter(newNotes)
        recyclerView.adapter = adapter

        val newJson = gson.toJson(newNotes)
        mPrefs.edit().putString("notes", newJson).apply()
    }
}