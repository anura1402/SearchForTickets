package ru.anura.emtesttask.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.anura.emtesttask.presentation.ViewModelFactory
import ru.anura.emtesttask.presentation.WelcomeViewModel

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    fun bindCoinViewModel(viewModel: WelcomeViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}