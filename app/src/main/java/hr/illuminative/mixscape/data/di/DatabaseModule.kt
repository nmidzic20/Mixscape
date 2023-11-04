package hr.illuminative.mixscape.data.di

import androidx.room.Room
import hr.illuminative.mixscape.data.database.MixscapeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val APP_DATABASE_NAME = "mixscape_database.db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MixscapeDatabase::class.java,
            APP_DATABASE_NAME,
        ).build()
    }
}
