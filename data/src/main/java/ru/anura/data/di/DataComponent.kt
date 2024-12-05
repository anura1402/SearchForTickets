package ru.anura.data.di

import dagger.Component
import ru.anura.common.repository.OffersRepository
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DataModule::class])
@Singleton
interface DataComponent {
    fun inject(repository: OffersRepository)

    @Component.Factory
    interface Factory {
        fun create(): DataComponent
    }
}