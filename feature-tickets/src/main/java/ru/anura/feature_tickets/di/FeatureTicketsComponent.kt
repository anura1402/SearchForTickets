package ru.anura.feature_tickets.di

import dagger.Subcomponent
import ru.anura.feature_tickets.ui.TheCountryWasChosenFragment
import ru.anura.feature_tickets.ui.WatchAllTicketsFragment

@Subcomponent(modules = [TicketsModule::class])
interface FeatureTicketsComponent {
    fun inject(fragment: TheCountryWasChosenFragment)
    fun inject(fragment: WatchAllTicketsFragment)
    @Subcomponent.Factory
    interface Factory {
        fun create(): FeatureTicketsComponent
    }


}
