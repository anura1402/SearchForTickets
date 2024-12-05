package ru.anura.emtesttask.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.anura.emtesttask.presentation.ViewModelFactory
import ru.anura.feature_search.viewmodel.WelcomeViewModel
import ru.anura.feature_tickets.viewmodel.TheCountryWasChosenViewModel
import ru.anura.feature_tickets.viewmodel.WatchAllTicketsViewModel

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    fun bindWelcomeViewModel(viewModel: ru.anura.feature_search.viewmodel.WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TheCountryWasChosenViewModel::class)
    fun bindTheCountryWasChosenViewModel(viewModel: ru.anura.feature_tickets.viewmodel.TheCountryWasChosenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WatchAllTicketsViewModel::class)
    fun bindWatchAllTicketsViewModel(viewModel: ru.anura.feature_tickets.viewmodel.WatchAllTicketsViewModel): ViewModel


    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}