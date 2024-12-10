package ru.anura.feature_search.di

import dagger.Module
import dagger.Provides
import ru.anura.common.repository.OffersRepository
import ru.anura.common.repository.PreferencesRepository
import ru.anura.feature_search.usecases.GetDataUseCase
import ru.anura.feature_search.usecases.GetOffersUseCase
import ru.anura.feature_search.usecases.SaveDataUseCase

@Module
class SearchModule {
    @Provides
    fun provideGetOffersUseCase(repository: OffersRepository): GetOffersUseCase {
        return GetOffersUseCase(repository)
    }

    @Provides
    fun provideGetDataUseCase(repository: PreferencesRepository): GetDataUseCase {
        return GetDataUseCase(repository)
    }

    @Provides
    fun provideSaveDataUseCase(repository: PreferencesRepository): SaveDataUseCase {
        return SaveDataUseCase(repository)
    }
}