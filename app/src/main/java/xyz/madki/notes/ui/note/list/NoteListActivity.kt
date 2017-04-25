package xyz.madki.notes.ui.note.list

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import butterknife.Bind
import butterknife.ButterKnife
import xyz.madki.notes.R
import xyz.madki.notes.data.Note
import javax.inject.Inject

class NoteListActivity : AppCompatActivity() {
    private lateinit var notesComponent: NotesComponent
    @Inject lateinit var note: Note
    @Bind(R.id.toolbar) lateinit var toolbar: Toolbar
    @Bind(R.id.fab) lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Here's your note: $note", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        notesComponent = DaggerNotesComponent.builder().build()
        notesComponent.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
