package xyz.madki.notes.ui.note.view

import android.content.Intent
import dagger.Component
import dagger.Module
import xyz.madki.notes.AppComponent
import xyz.madki.notes.ui.base.IActivityInjector
import xyz.madki.notes.ui.base.PerActivity

@PerActivity
@Component(modules = arrayOf(NoteViewModule::class), dependencies = arrayOf(AppComponent::class))
interface NoteViewComponent: IActivityInjector<NoteViewActivity>

@Module
class NoteViewModule(intent: Intent) {
}
