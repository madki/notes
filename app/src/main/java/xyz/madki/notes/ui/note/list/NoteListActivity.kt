package xyz.madki.notes.ui.note.list

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import butterknife.Bind
import com.jakewharton.rxbinding2.view.clicks
import hugo.weaving.DebugLog
import io.reactivex.Observable
import timber.log.Timber
import xyz.madki.notes.App
import xyz.madki.notes.R
import xyz.madki.notes.data.Note
import xyz.madki.notes.ui.base.BaseActivity
import xyz.madki.notes.ui.note.create.NoteCreateActivity
import javax.inject.Inject

class NoteListActivity : BaseActivity<NoteListPresenter, NotesComponent>(), NoteListPresenter.IView {

    @Inject lateinit var note: Note
    @Bind(R.id.fab) lateinit var fab: FloatingActionButton
    @Bind(R.id.rv_notes) lateinit var rvNotes: RecyclerView
    @Inject lateinit var noteListPresenter: NoteListPresenter
    @Inject lateinit var noteListAdapter: NoteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter = noteListAdapter
    }

    @DebugLog
    override fun setData(notes: List<Note>) {
        Timber.d("Notes in Act: %s", notes.joinToString(";;"))
        noteListAdapter.setData(notes)
    }

    override fun layout() = R.layout.activity_note_list

    override fun getPresenter() = noteListPresenter

    override fun createComponent(): NotesComponent = DaggerNotesComponent
            .builder()
            .appComponent(App.component(this))
            .noteListModule(NoteListModule(intent))
            .build()

    override fun addNewNoteClicks(): Observable<Unit> {
        return fab.clicks()
    }

    override fun openNoteCreateScreen() {
        Timber.d("Open note create called")
        NoteCreateActivity.open(this)
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
