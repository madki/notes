package xyz.madki.notes.util

import android.content.Context

val DAGGER_SERVICE = "xyz.madki.notes.DaggerService"

fun <T> getDaggerService(context: Context): T {
    return context.getSystemService(DAGGER_SERVICE) as T
}


