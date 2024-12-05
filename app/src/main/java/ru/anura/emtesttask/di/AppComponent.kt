package ru.anura.emtesttask.di

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.BindsInstance
import dagger.Component
import ru.anura.data.di.DataModule
import ru.anura.data.di.NetworkModule
import ru.anura.emtesttask.presentation.MainActivity
import ru.anura.emtesttask.presentation.SearchApp
import ru.anura.feature_search.di.FeatureSearchComponent
import ru.anura.feature_search.di.SearchModule
import ru.anura.feature_search.ui.WelcomeFragment
import ru.anura.feature_tickets.di.FeatureTicketsComponent
import ru.anura.feature_tickets.di.TicketsModule
import ru.anura.feature_tickets.ui.TheCountryWasChosenFragment
import ru.anura.feature_tickets.ui.WatchAllTicketsFragment
import javax.inject.Provider
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetworkModule::class, DataModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(application: SearchApp)
    fun featureSearchComponent(): FeatureSearchComponent.Factory
    fun featureTicketsComponent(): FeatureTicketsComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}
