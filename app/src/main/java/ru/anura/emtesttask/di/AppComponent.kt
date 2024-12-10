package ru.anura.emtesttask.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.anura.data.di.DataComponent
import ru.anura.data.di.DataModule
import ru.anura.data.di.NetworkModule
import ru.anura.data.di.PreferencesModule
import ru.anura.emtesttask.presentation.MainActivity
import ru.anura.emtesttask.presentation.SearchApp
import ru.anura.feature_search.di.FeatureSearchComponent
import ru.anura.feature_tickets.di.FeatureTicketsComponent
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class, NetworkModule::class, DataModule::class,PreferencesModule::class])
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
