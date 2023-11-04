package hr.illuminative.mixscape

import android.app.Application
import android.util.Log
import hr.illuminative.mixscape.data.di.networkModule
import hr.illuminative.mixscape.data.di.dataModule
import hr.illuminative.mixscape.data.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Mixscape : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Mixscape)
            modules(
                databaseModule,
                dataModule,
                networkModule,
            )
        }

        Log.d("Mixscape", "App started")
    }
}