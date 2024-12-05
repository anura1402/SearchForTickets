package ru.anura.feature_search.di

import dagger.Module
import dagger.Provides
import ru.anura.common.repository.OffersRepository
import ru.anura.feature_search.usecases.GetOffersUseCase

@Module
class SearchModule {
    @Provides
    fun provideGetOffersUseCase(repository: OffersRepository): GetOffersUseCase {
        return GetOffersUseCase(repository)
    }
}