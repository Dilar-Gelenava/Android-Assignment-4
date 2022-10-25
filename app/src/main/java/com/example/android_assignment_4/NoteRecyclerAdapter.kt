package com.example.android_assignment_4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class NoteRecyclerAdapter(private val notes: Array<Note>) :
    RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.setData(currentNote)
        holder.noteLayout.setOnClickListener() {
            val mPrefs =
                holder.itemView.context.getSharedPreferences("shared preferences", MODE_PRIVATE)
            val gson = Gson()
            val json = mPrefs.getString("notes", "[]")
            val notes: Array<Note> = gson.fromJson(json, Array<Note>::class.java)
            val newNotes = notes.filter { it.id != currentNote.id }
            val newJson = gson.toJson(newNotes)
            mPrefs.edit().putString("notes", newJson).apply()
            notifyItemRemoved(position)

            val snackbar = Snackbar.make(holder.itemView, "Note deleted", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    override fun getItemCount() = notes.size

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idView: TextView = itemView.findViewById(R.id.id)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val descriptionView: TextView = itemView.findViewById(R.id.description)
        val noteLayout: View = itemView.findViewById(R.id.noteLayout)
        fun setData(note: Note) {
            titleView.text = note.title
            descriptionView.text = note.description
            idView.text = note.id.toString()
            noteLayout.id = note.id
        }
    }
}