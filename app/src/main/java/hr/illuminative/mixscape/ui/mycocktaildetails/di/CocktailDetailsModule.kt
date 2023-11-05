package hr.illuminative.mixscape.ui.mycocktaildetails.di // ktlint-disable package-name

import hr.illuminative.mixscape.ui.mycocktaildetails.MyCocktailDetailsViewModel
import hr.illuminative.mixscape.ui.mycocktaildetails.mapper.MyCocktailDetailsMapper
import hr.illuminative.mixscape.ui.mycocktaildetails.mapper.MyCocktailDetailsMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myCocktailDetailsModule = module {
    viewModel { (cocktailId: Int) ->
        MyCocktailDetailsViewModel(
            mixscapeRepository = get(),
            myCocktailDetailsMapper = get(),
            cocktailId = cocktailId.toString(),
        )
    }
    single<MyCocktailDetailsMapper> { MyCocktailDetailsMapperImpl() }
}
