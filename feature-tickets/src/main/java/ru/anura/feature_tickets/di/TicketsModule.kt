package ru.anura.feature_tickets.di

import dagger.Module
import dagger.Provides
import ru.anura.common.repository.OffersRepository
import ru.anura.feature_tickets.usecases.GetOffersTicketsUseCase
import ru.anura.feature_tickets.usecases.GetTicketsUseCase

@Module
class TicketsModule {
    @Provides
    fun provideOffersTicketsUseCase(repository: OffersRepository): GetOffersTicketsUseCase {
        return GetOffersTicketsUseCase(repository)
    }
    @Provides
    fun provideTicketsUseCase(repository: OffersRepository): GetTicketsUseCase {
        return GetTicketsUseCase(repository)
    }
}