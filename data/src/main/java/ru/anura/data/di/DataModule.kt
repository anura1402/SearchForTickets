package ru.anura.data.di

import dagger.Binds
import dagger.Module
import ru.anura.common.repository.OffersRepository
import ru.anura.data.OffersRepositoryImpl
import javax.inject.Singleton

@Module
interface  DataModule {
    @Binds
    @Singleton
    fun bindOffersRepository(impl: OffersRepositoryImpl): OffersRepository
}