package hr.illuminative.mixscape.ui.home.di

import hr.illuminative.mixscape.ui.home.HomeViewModel
import hr.illuminative.mixscape.ui.home.mapper.HomeScreenMapper
import hr.illuminative.mixscape.ui.home.mapper.HomeScreenMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            mixscapeRepository = get(),
            homeScreenMapper = get(),
        )
    }
    single<HomeScreenMapper> { HomeScreenMapperImpl() }
}
