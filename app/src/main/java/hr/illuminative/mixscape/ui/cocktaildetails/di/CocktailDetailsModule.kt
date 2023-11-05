package hr.illuminative.mixscape.ui.cocktaildetails.di // ktlint-disable package-name

import hr.illuminative.mixscape.ui.cocktaildetails.CocktailDetailsViewModel
import hr.illuminative.mixscape.ui.cocktaildetails.mapper.CocktailDetailsMapper
import hr.illuminative.mixscape.ui.cocktaildetails.mapper.CocktailDetailsMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cocktailDetailsModule = module {
    viewModel { (cocktailId: Int) ->
        CocktailDetailsViewModel(
            mixscapeRepository = get(),
            cocktailDetailsMapper = get(),
            cocktailId = cocktailId.toString(),
        )
    }
    single<CocktailDetailsMapper> { CocktailDetailsMapperImpl() }
}
