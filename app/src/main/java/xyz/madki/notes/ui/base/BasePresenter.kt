package xyz.madki.notes.ui.base

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import mortar.Presenter
import mortar.bundler.BundleService

abstract class BasePresenter<V: IBaseView>: Presenter<V>() {
    private var viewDisposables: CompositeDisposable = CompositeDisposable()

    override fun onLoad(savedState: Bundle?) {
        addViewDisposables(view.connector())
    }

    override fun dropView(view: V) {
        if (!viewDisposables.isDisposed) {
            viewDisposables.dispose()
            viewDisposables = CompositeDisposable()
        }
    }

    override fun extractBundleService(view: V): BundleService {
        return view.getBundleService()
    }

    abstract fun V.connector(): Array<Disposable>

    protected fun addViewDisposables(d: Array<Disposable>) {
        d.forEach { viewDisposables.add(it) }
    }
}


