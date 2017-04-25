package xyz.madki.notes.data

import org.joda.time.LocalDateTime


data class Note(
        val title: String,
        val text: String,
        val photo: String?,
        val created: LocalDateTime
)