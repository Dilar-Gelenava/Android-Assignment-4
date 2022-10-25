package com.example.android_assignment_4

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        val gson = Gson()
        val mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val json = mPrefs.getString("notes","[]")
        val notes: Array<Note> = gson.fromJson(json, Array<Note>::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        val adapter = NoteRecyclerAdapter(notes)
        recyclerView.adapter = adapter
    }


    fun addNoteMenu(view: View) {
        val intent = Intent(this, NoteAddActivity::class.java)
        startActivity(intent)
    }
}