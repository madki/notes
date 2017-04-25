package xyz.madki.notes.db

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import xyz.madki.notes.App
import xyz.madki.notes.data.Note

class DbOpenHelper constructor(context: App) : SQLiteOpenHelper(context, "indiez.db", null, DbOpenHelper.VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(createTableQuery())
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqLiteDatabase.execSQL(dropTableQuery(Note.TABLE))
        onCreate(sqLiteDatabase)
    }

    companion object {
        private val VERSION = 1

        private fun createTableQuery(): String {
            return "CREATE TABLE ${Note.TABLE} (" +
                    "${Note.ID} INTEGER NOT NULL PRIMARY KEY, " +
                    "${Note.TITLE} TEXT NOT NULL, " +
                    "${Note.TEXT} TEXT NOT NULL, " +
                    "${Note.PHOTO} TEXT, " +
                    "${Note.CREATED} TEXT" +
                    ")"
        }

        private fun dropTableQuery(tableName: String): String {
            return "DROP TABLE IF EXISTS " + tableName
        }
    }
}

