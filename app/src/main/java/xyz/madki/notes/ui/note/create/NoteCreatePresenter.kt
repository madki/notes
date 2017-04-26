package xyz.madki.notes.ui.note.create

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import xyz.madki.notes.ui.base.BasePresenter
import xyz.madki.notes.ui.base.IBaseView
import xyz.madki.notes.ui.base.PerActivity
import javax.inject.Inject

@PerActivity
class NoteCreatePresenter @Inject constructor(): BasePresenter<NoteCreatePresenter.IView>() {
    override fun IView.connector() = arrayOf(
            Observable.just("Hello").subscribeBy({ s -> Timber.d(s) })
    )

    interface IView : IBaseView {

    }
}


