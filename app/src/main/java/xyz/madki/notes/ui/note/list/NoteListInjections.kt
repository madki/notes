package xyz.madki.notes.ui.note.list

import android.content.Intent
import dagger.Component
import dagger.Module
import dagger.Provides
import org.joda.time.LocalDateTime
import xyz.madki.notes.AppComponent
import xyz.madki.notes.data.Note
import xyz.madki.notes.ui.base.IActivityInjector
import xyz.madki.notes.ui.base.PerActivity

@PerActivity
@Component(modules = arrayOf(NotesModule::class), dependencies = arrayOf(AppComponent::class))
interface NotesComponent: IActivityInjector<NoteListActivity>

@Module
class NotesModule(intent: Intent) {
    @Provides
    fun provideNote(): Note {
        return Note(1, "MK", "Madhu", null, LocalDateTime.now())
    }
}