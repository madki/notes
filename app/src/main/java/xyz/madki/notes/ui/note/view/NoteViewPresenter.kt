package xyz.madki.notes.ui.note.view

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import xyz.madki.notes.ui.base.BasePresenter
import xyz.madki.notes.ui.base.IBaseView
import xyz.madki.notes.ui.base.PerActivity
import javax.inject.Inject

@PerActivity
class NoteViewPresenter @Inject constructor(): BasePresenter<NoteViewPresenter.IView>() {
    override fun IView.connector() = arrayOf(
            Observable.just("Hello").subscribeBy ({ s -> Timber.d(s)})
    )

    interface IView: IBaseView {

    }
}


