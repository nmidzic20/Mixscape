package hr.illuminative.mixscape.ui.favorites.di

import hr.illuminative.mixscape.ui.favorites.FavoritesViewModel
import hr.illuminative.mixscape.ui.favorites.mapper.FavoritesMapper
import hr.illuminative.mixscape.ui.favorites.mapper.FavoritesMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel {
        FavoritesViewModel(
            mixscapeRepository = get(),
            favoritesScreenMapper = get()
        )
    }
    single<FavoritesMapper> { FavoritesMapperImpl() }
}