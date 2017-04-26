package xyz.madki.notes

import android.database.sqlite.SQLiteOpenHelper
import com.squareup.sqlbrite.BriteDatabase
import com.squareup.sqlbrite.SqlBrite
import dagger.Component
import dagger.Module
import dagger.Provides
import rx.schedulers.Schedulers
import timber.log.Timber
import xyz.madki.notes.db.Db
import xyz.madki.notes.db.DbOpenHelper
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DbModule::class))
interface AppComponent {
    fun db(): Db
}

@Module
class AppModule(private val app: App) {
    @Provides
    fun provideApp() = app
}

@Module
class DbModule {

    @Provides @Singleton
    internal fun provideSQLiteOpenHelper(app: App): SQLiteOpenHelper {
        return DbOpenHelper(app)
    }

    @Provides @Singleton
    internal fun provideSqlBrite(): SqlBrite {
        return SqlBrite.Builder().logger {  message -> Timber.tag("Database").v(message)  }.build()
    }

    @Provides @Singleton
    internal fun provideBriteDatabase(sqlBrite: SqlBrite, sqLiteOpenHelper: SQLiteOpenHelper): BriteDatabase {
        val db = sqlBrite.wrapDatabaseHelper(sqLiteOpenHelper, Schedulers.io())
        db.setLoggingEnabled(true)
        return db
    }
}