package ru.anura.emtesttask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.anura.emtesttask.domain.GetOffersUseCase
import ru.anura.emtesttask.domain.OffersRepository

class WelcomeViewModelFactory(private val repository: OffersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            WelcomeViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}