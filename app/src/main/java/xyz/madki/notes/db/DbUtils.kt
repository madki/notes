package xyz.madki.notes.db

import android.database.Cursor
import org.joda.time.LocalDateTime
import xyz.madki.notes.data.Note


fun Cursor.asNote(): Note {
    return Note(
            id = getInt(getColumnIndexOrThrow(Note.ID)),
            title = getString(getColumnIndexOrThrow(Note.TITLE)),
            text = getString(getColumnIndexOrThrow(Note.TEXT)),
            photo = getString(getColumnIndexOrThrow(Note.PHOTO)),
            // TODO get from db
            created = LocalDateTime.now()
    )
}


