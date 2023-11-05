package hr.illuminative.mixscape.ui.mylist.di

import hr.illuminative.mixscape.ui.mylist.MyListViewModel
import hr.illuminative.mixscape.ui.mylist.mapper.MyListMapper
import hr.illuminative.mixscape.ui.mylist.mapper.MyListMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myListModule = module {
    viewModel {
        MyListViewModel(
            mixscapeRepository = get(),
            myListScreenMapper = get(),
        )
    }
    single<MyListMapper> { MyListMapperImpl() }
}
