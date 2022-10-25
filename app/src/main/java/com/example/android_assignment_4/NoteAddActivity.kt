package com.example.android_assignment_4

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson


class NoteAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_add)
    }

    fun back(view: View) {
        finish()
    }

    @SuppressLint("MutatingSharedPrefs")
    fun addNote(view: View) {
        val title = findViewById<EditText>(R.id.titleView).text.toString()
        val description = findViewById<EditText>(R.id.descriptionView).text.toString()

        val mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("notes", "[]")
        val notes: Array<Note> = gson.fromJson(json, Array<Note>::class.java)

        val newNote = Note(
            (1..1000).random(),
            title,
            description
        )

        val newNotes = notes + newNote
        val newJson = gson.toJson(newNotes)
        mPrefs.edit().putString("notes", newJson).apply()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = NoteRecyclerAdapter(notes)
        recyclerView.adapter = adapter

        finish()
    }
}