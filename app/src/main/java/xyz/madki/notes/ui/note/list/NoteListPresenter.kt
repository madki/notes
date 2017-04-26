package xyz.madki.notes.ui.note.list

import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import xyz.madki.notes.data.Note
import xyz.madki.notes.db.Db
import xyz.madki.notes.ui.base.BasePresenter
import xyz.madki.notes.ui.base.IBaseView
import xyz.madki.notes.ui.base.PerActivity
import javax.inject.Inject

@PerActivity
class NoteListPresenter @Inject constructor(private val db: Db) : BasePresenter<NoteListPresenter.IView>() {
    private val allNotes = db.getAllNotes().replay(1)

    init {
        allNotes.connect()
    }

    override fun onLoad(savedState: Bundle?) {
        super.onLoad(savedState)
        Timber.d("NoteListPresenter.onLoad")
    }

    override fun dropView(view: IView) {
        super.dropView(view)
        Timber.d("NoteListPresenter.dropView")
    }

    override fun IView.connector() = arrayOf(
            allNotes
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy({setData(it)}),
            addNewNoteClicks().doOnNext { Timber.d("got a click") }.subscribeBy ({ openNoteCreateScreen() })
    )

    interface IView : IBaseView {
        fun addNewNoteClicks(): Observable<Unit>
        fun openNoteCreateScreen()
        fun setData(notes: List<Note>)
    }
}


