package xyz.madki.notes.ui.note.create

import android.content.Context
import android.content.Intent
import xyz.madki.notes.App
import xyz.madki.notes.R
import xyz.madki.notes.ui.base.BaseActivity
import javax.inject.Inject

class NoteCreateActivity: BaseActivity<NoteCreatePresenter, NoteCreateComponent>() {
    @Inject lateinit var noteCreatePresenter: NoteCreatePresenter

    override fun layout() = R.layout.activity_note_view

    override fun createComponent(): NoteCreateComponent = DaggerNoteCreateComponent
            .builder()
            .appComponent(App.component(this))
            .noteCreateModule(NoteCreateModule(intent))
            .build()

    override fun getPresenter() = noteCreatePresenter

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, NoteCreateActivity::class.java)
            context.startActivity(intent)
        }
    }
}
