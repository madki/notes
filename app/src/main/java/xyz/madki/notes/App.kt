package xyz.madki.notes

import android.app.Application
import android.content.Context
import mortar.MortarScope
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

class App: Application() {
    private var rootScope: MortarScope? = null
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        JodaTimeAndroid.init(this)
    }

    override fun getSystemService(name: String): Any {
        if (rootScope == null) rootScope = MortarScope.buildRootScope().build("Root")
        return if (rootScope!!.hasService(name)) rootScope!!.getService(name) else super.getSystemService(name)
    }

    companion object {
        fun component(context: Context) = (context.applicationContext as App).component
    }
}


