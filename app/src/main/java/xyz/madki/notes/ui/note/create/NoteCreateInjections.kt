package xyz.madki.notes.ui.note.create

import android.content.Intent
import dagger.Component
import dagger.Module
import xyz.madki.notes.AppComponent
import xyz.madki.notes.ui.base.IActivityInjector
import xyz.madki.notes.ui.base.PerActivity


@PerActivity
@Component(modules = arrayOf(NoteCreateModule::class), dependencies = arrayOf(AppComponent::class))
interface NoteCreateComponent: IActivityInjector<NoteCreateActivity>

@Module
class NoteCreateModule(intent: Intent) {
}


