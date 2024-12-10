package ru.anura.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.anura.common.repository.OffersRepository
import ru.anura.common.repository.PreferencesRepository
import ru.anura.data.repositoryImpl.OffersRepositoryImpl
import ru.anura.data.repositoryImpl.PreferencesRepositoryImpl
import javax.inject.Singleton

@Module
interface  DataModule {
    @Binds
    @Singleton
    fun bindOffersRepository(impl: OffersRepositoryImpl): OffersRepository

    @Binds
    @Singleton
    fun bindPreferencesRepository(impl: PreferencesRepositoryImpl): PreferencesRepository



}