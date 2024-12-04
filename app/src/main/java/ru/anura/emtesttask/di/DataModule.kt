package ru.anura.emtesttask.di

import dagger.Binds
import dagger.Module
import ru.anura.emtesttask.data.OffersRepositoryImpl
import ru.anura.emtesttask.domain.OffersRepository
import javax.inject.Singleton

@Module
interface DataModule {
    @Binds
    @Singleton
    fun bindOffersRepository(impl: OffersRepositoryImpl): OffersRepository
}