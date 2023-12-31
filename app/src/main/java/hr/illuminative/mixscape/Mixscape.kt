package hr.illuminative.mixscape

import android.app.Application
import android.util.Log
import hr.illuminative.mixscape.data.di.dataModule
import hr.illuminative.mixscape.data.di.databaseModule
import hr.illuminative.mixscape.data.di.networkModule
import hr.illuminative.mixscape.ui.cocktaildetails.di.cocktailDetailsModule
import hr.illuminative.mixscape.ui.favorites.di.favoritesModule
import hr.illuminative.mixscape.ui.home.di.homeModule
import hr.illuminative.mixscape.ui.mycocktaildetails.di.myCocktailDetailsModule
import hr.illuminative.mixscape.ui.mylist.di.myListModule
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
                homeModule,
                cocktailDetailsModule,
                favoritesModule,
                myListModule,
                myCocktailDetailsModule
            )
        }

        Log.d("Mixscape", "App started")
    }
}
