package hr.illuminative.mixscape.data.di

import hr.illuminative.mixscape.data.database.FavoriteCocktailDAO
import hr.illuminative.mixscape.data.database.MixscapeDatabase
import hr.illuminative.mixscape.data.repository.MixscapeRepository
import hr.illuminative.mixscape.data.repository.MixscapeRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<FavoriteCocktailDAO> {
        val database = get<MixscapeDatabase>()
        database.favoriteCocktailDao()
    }
    single<MixscapeRepository> {
        MixscapeRepositoryImpl(
            cocktailService = get(),
            cocktailDao = get(),
            bgDispatcher = Dispatchers.IO,
        )
    }
}
