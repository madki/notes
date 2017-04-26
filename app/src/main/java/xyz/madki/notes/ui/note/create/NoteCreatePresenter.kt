package xyz.madki.notes.ui.note.create

import hugo.weaving.DebugLog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import xyz.madki.notes.data.Note
import xyz.madki.notes.db.Db
import xyz.madki.notes.ui.base.BasePresenter
import xyz.madki.notes.ui.base.IBaseView
import xyz.madki.notes.ui.base.PerActivity
import javax.inject.Inject

@PerActivity
class NoteCreatePresenter @Inject constructor(private val db: Db) : BasePresenter<NoteCreatePresenter.IView>() {
    override fun IView.connector(): Array<Disposable> {
        val title = titleChanges().distinctUntilChanged()
        val text = textChanges().distinctUntilChanged()

        val titleValidity = title.map { it.isValidTitle() }
        val textValidity = text.map { it.isValidText() }

        val noteValidity = Observable.combineLatest(
                titleValidity,
                textValidity,
                BiFunction<Boolean, Boolean, Boolean>
                { isTitleValid, isTextValid -> isTitleValid && isTextValid }
        ).distinctUntilChanged()

        val enableSaveStream = noteValidity.filter { it }
        val disableSaveStream = noteValidity.filter { !it }

        val note = Observable.combineLatest(
                title, text,
                BiFunction<String, String, Note> { title, text -> createNote(title, text) }
        )

        val saveNoteStream = saveClicks().flatMap { _ -> note }
                .map { db.insertNote(it) }
                .observeOn(AndroidSchedulers.mainThread())

        return arrayOf(
                enableSaveStream.subscribeBy({ _ -> enableSave() }),
                disableSaveStream.subscribeBy({ _ -> disableSave() }),
                saveNoteStream.
                        subscribeBy(
                                onNext = { closeScreen() },
                                onError = { showError(it.message ?: "Unknown error occurred") }
                        )
        )
    }

    @DebugLog
    fun String.isValidTitle(): Boolean {
        return isNotBlank()
    }

    @DebugLog
    fun String.isValidText(): Boolean {
        return isNotBlank()
    }

    @DebugLog
    fun createNote(title: String, text: String, photo: String? = null): Note {
        if (!title.isValidTitle()) {
            throw IllegalArgumentException("Title cannot be empty for a note")
        }
        if (!text.isValidText()) {
            throw IllegalArgumentException("Description is required for a note")
        }
        return Note(
                title = title,
                text = text,
                photo = photo
        )
    }

    interface IView : IBaseView {
        fun titleChanges(): Observable<String>
        fun textChanges(): Observable<String>
        fun saveClicks(): Observable<Unit>
        fun showError(message: String)
        fun closeScreen()
        fun disableSave()
        fun enableSave()
    }
}


