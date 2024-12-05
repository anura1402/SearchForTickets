package ru.anura.common

import androidx.lifecycle.ViewModelProvider

interface ViewModelFactoryProvider {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}