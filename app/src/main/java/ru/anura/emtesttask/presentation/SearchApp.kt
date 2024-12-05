package ru.anura.emtesttask.presentation

import android.app.Application
import ru.anura.emtesttask.di.AppComponent
import ru.anura.emtesttask.di.DaggerAppComponent
import ru.anura.feature_search.di.FeatureSearchComponent
import ru.anura.feature_search.di.FeatureSearchComponentProvider
import ru.anura.feature_tickets.di.FeatureTicketsComponent
import ru.anura.feature_tickets.di.FeatureTicketsComponentProvider

class SearchApp : Application(), FeatureSearchComponentProvider, FeatureTicketsComponentProvider {
    val appComponent = DaggerAppComponent.factory().create(this)
    override fun provideFeatureSearchComponent(): FeatureSearchComponent {
        return appComponent.featureSearchComponent().create()
    }

    override fun provideFeatureTicketsComponent(): FeatureTicketsComponent {
        return appComponent.featureTicketsComponent().create()
    }
}