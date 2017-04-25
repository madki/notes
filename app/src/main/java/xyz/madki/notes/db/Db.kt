package xyz.madki.notes.db

import android.database.Cursor
import com.squareup.sqlbrite.BriteDatabase
import io.reactivex.Observable
import xyz.madki.notes.data.Note
import xyz.madki.notes.util.toV2Observable

class Db(private val database: BriteDatabase) {

    fun getAllNotes(): Observable<List<Note>> {
        return database.createQuery(Note.TABLE, "SELECT * FROM ${Note.TABLE}")
                .mapToList(Cursor::asNote)
                .toV2Observable()
    }

    fun insertNote(note: Note): Note {
        if (note.id != null) {
            throw IllegalArgumentException("id should be null for inserting new note")
        }
        return note.copy(id = database.insert(Note.TABLE, note.toContentValues()).toInt())
    }
}
