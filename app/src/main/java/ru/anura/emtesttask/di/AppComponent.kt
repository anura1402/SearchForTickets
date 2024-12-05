package ru.anura.emtesttask.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.anura.data.di.DataModule
import ru.anura.data.di.NetworkModule
import ru.anura.emtesttask.presentation.MainActivity
import ru.anura.emtesttask.presentation.SearchApp
import ru.anura.feature_search.di.SearchModule
import ru.anura.feature_search.ui.WelcomeFragment
import ru.anura.feature_tickets.di.TicketsModule
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class, DataModule::class, ViewModelModule::class, TicketsModule::class])
@Singleton
interface AppComponent {


    fun inject(activity: MainActivity)
    fun inject(application: SearchApp)
    fun inject(fragment: ru.anura.feature_tickets.ui.TheCountryWasChosenFragment)
    fun inject(fragment: ru.anura.feature_tickets.ui.WatchAllTicketsFragment)
    fun inject(fragment: WelcomeFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}
