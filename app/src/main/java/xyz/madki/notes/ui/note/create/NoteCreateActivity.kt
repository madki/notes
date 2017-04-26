package xyz.madki.notes.ui.note.create

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.support.annotation.ColorRes
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.widget.EditText
import butterknife.Bind
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import xyz.madki.notes.App
import xyz.madki.notes.R
import xyz.madki.notes.ui.base.BaseActivity
import javax.inject.Inject

class NoteCreateActivity : BaseActivity<NoteCreatePresenter, NoteCreateComponent>(), NoteCreatePresenter.IView {
    @Inject lateinit var noteCreatePresenter: NoteCreatePresenter
    @Bind(R.id.et_title) lateinit var etTitle: EditText
    @Bind(R.id.et_text) lateinit var etText: EditText
    @Bind(R.id.et_photo) lateinit var etPhoto: EditText
    @Bind(R.id.fab) lateinit var fab: FloatingActionButton

    override fun layout() = R.layout.activity_note_create

    override fun createComponent(): NoteCreateComponent = DaggerNoteCreateComponent
            .builder()
            .appComponent(App.component(this))
            .noteCreateModule(NoteCreateModule(intent))
            .build()

    override fun getPresenter() = noteCreatePresenter

    override fun titleChanges(): Observable<String> {
        return etTitle.textChanges().map(CharSequence::toString)
    }

    override fun textChanges(): Observable<String> {
        return etText.textChanges().map(CharSequence::toString)
    }

    override fun saveClicks(): Observable<Unit> {
        return fab.clicks()
    }

    override fun showError(message: String) {
        Snackbar.make(fab, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    override fun closeScreen() {
        finish()
    }

    override fun disableSave() {
        fab.backgroundTintList = getBackgroundColorList(false)
    }

    override fun enableSave() {
        fab.backgroundTintList = getBackgroundColorList(true)
    }

    fun getBackgroundColorList(enabled: Boolean): ColorStateList {
        @ColorRes val color = when(enabled) {
            true -> R.color.colorAccent
            false -> R.color.deHighlighted
        }
        return ColorStateList.valueOf(ContextCompat.getColor(this, color))
    }

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, NoteCreateActivity::class.java)
            context.startActivity(intent)
        }
    }
}
