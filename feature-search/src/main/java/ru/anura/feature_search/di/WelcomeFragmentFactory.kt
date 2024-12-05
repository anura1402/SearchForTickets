package ru.anura.feature_search.di

import ru.anura.feature_search.ui.WelcomeFragment
import ru.anura.feature_search.usecases.GetOffersUseCase
import javax.inject.Inject

class WelcomeFragmentFactory @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase
) {
    fun create(): WelcomeFragment {
        return WelcomeFragment().apply {
            // Передаем зависимость в фрагмент
            this.getOffersUseCase = getOffersUseCase
        }
    }
}