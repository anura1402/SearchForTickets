package ru.anura.feature_search.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.anura.feature_search.ui.WelcomeFragment

@Subcomponent(modules = [SearchModule::class])
interface FeatureSearchComponent {
    fun inject(fragment: WelcomeFragment)
    @Subcomponent.Factory
    interface Factory {
        fun create(): FeatureSearchComponent
    }


}
