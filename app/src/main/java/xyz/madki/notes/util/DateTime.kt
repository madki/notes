package xyz.madki.notes.util

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

fun LocalDateTime.toDateString(): String {
    return DateTimeFormat.fullDateTime().print(this)
}

fun String.toLocalDateTime(): LocalDateTime {
    return DateTimeFormat.fullDateTime().parseLocalDateTime(this)
}