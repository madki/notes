package xyz.madki.notes.ui.note.list

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import xyz.madki.notes.ui.base.BasePresenter
import xyz.madki.notes.ui.base.IBaseView
import xyz.madki.notes.ui.base.PerActivity
import javax.inject.Inject

@PerActivity
class NoteListPresenter @Inject constructor() : BasePresenter<NoteListPresenter.IView>() {

    override fun IView.connector() = arrayOf(
            addNewNoteClicks().subscribeBy { openNoteCreateScreen() }
    )

    interface IView : IBaseView {
        fun addNewNoteClicks(): Observable<Unit>
        fun openNoteCreateScreen()
    }
}


