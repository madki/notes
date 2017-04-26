package xyz.madki.notes.data

import android.content.ContentValues
import org.joda.time.LocalDateTime
import xyz.madki.notes.util.toDateString


data class Note(
        val id: Int? = null,
        val title: String,
        val text: String,
        val photo: String?,
        val created: LocalDateTime = LocalDateTime.now()
) {
    fun toContentValues(): ContentValues {
        val contentValues = ContentValues()
        if (id != null) {
            contentValues.put(Note.ID, id)
        }
        contentValues.put(Note.TITLE, title)
        contentValues.put(Note.TEXT, text)
        contentValues.put(Note.PHOTO, photo)
        contentValues.put(Note.CREATED, created.toDateString())
        return contentValues
    }

    companion object {
        val TABLE = "note"
        val ID = "_id"
        val TITLE = "title"
        val TEXT = "text"
        val PHOTO = "photo"
        val CREATED = "created"
    }
}