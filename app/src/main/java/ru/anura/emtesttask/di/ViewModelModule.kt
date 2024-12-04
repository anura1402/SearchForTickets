package ru.anura.emtesttask.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.anura.emtesttask.presentation.TheCountryWasChosenViewModel
import ru.anura.emtesttask.presentation.ViewModelFactory
import ru.anura.emtesttask.presentation.WatchAllTicketsViewModel
import ru.anura.emtesttask.presentation.WelcomeViewModel

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TheCountryWasChosenViewModel::class)
    fun bindTheCountryWasChosenViewModel(viewModel: TheCountryWasChosenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WatchAllTicketsViewModel::class)
    fun bindWatchAllTicketsViewModel(viewModel: WatchAllTicketsViewModel): ViewModel


    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}