package ru.anura.emtesttask

import androidx.lifecycle.ViewModelProvider
import ru.anura.common.ViewModelFactoryProvider
import ru.anura.common.repository.OffersRepository
import ru.anura.data.OffersRepositoryImpl
import ru.anura.emtesttask.presentation.ViewModelFactory
import javax.inject.Inject

class ViewModelProviderImpl @Inject constructor(
    private val offersRepository: OffersRepository
) : ViewModelFactoryProvider {
    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(offersRepository)  // Реализуйте свою фабрику здесь
    }
}