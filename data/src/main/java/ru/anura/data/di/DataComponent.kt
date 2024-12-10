package ru.anura.data.di

import dagger.Component
import ru.anura.common.repository.OffersRepository
import ru.anura.common.repository.PreferencesRepository
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DataModule::class, PreferencesModule::class])
@Singleton
interface DataComponent {
    fun inject(repository: OffersRepository)
    fun inject(repository: PreferencesRepository)

    @Component.Factory
    interface Factory {
        fun create(): DataComponent
    }
}