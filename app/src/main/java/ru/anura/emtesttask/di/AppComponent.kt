package ru.anura.emtesttask.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.anura.emtesttask.presentation.MainActivity
import ru.anura.emtesttask.presentation.SearchApp
import ru.anura.emtesttask.presentation.WelcomeFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class, DataModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: WelcomeFragment)
    fun inject(application: SearchApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}