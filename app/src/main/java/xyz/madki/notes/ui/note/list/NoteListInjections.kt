package xyz.madki.notes.ui.note.list

import dagger.Component
import dagger.Module
import dagger.Provides
import org.joda.time.LocalDateTime
import xyz.madki.notes.data.Note

@Component(modules = arrayOf(NotesModule::class))
interface NotesComponent {
    fun inject(noteListActivity: NoteListActivity)
}

@Module
class NotesModule {
    @Provides
    fun provideNote(): Note {
        return Note(1, "MK", "Madhu", null, LocalDateTime.now())
    }
}