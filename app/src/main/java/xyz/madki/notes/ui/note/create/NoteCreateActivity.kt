package xyz.madki.notes.ui.note.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import xyz.madki.notes.R

class NoteCreateActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_create)
    }

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, NoteCreateActivity::class.java))
        }
    }
}