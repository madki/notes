package xyz.madki.notes.ui.note.view

import xyz.madki.notes.App
import xyz.madki.notes.R
import xyz.madki.notes.ui.base.BaseActivity
import javax.inject.Inject

class NoteViewActivity: BaseActivity<NoteViewPresenter, NoteViewComponent>(), NoteViewPresenter.IView {
    @Inject lateinit var noteViewPresenter: NoteViewPresenter

    override fun layout() = R.layout.activity_note_view

    override fun createComponent(): NoteViewComponent = DaggerNoteViewComponent
            .builder()
            .appComponent(App.component(this))
            .noteViewModule(NoteViewModule(intent))
            .build()

    override fun getPresenter() = noteViewPresenter
}


