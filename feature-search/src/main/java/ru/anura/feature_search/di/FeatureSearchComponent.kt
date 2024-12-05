package ru.anura.feature_search.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.anura.feature_search.ui.WelcomeFragment

@Component(modules = [SearchModule::class])
internal interface FeatureSearchComponent {
    fun inject(fragment: WelcomeFragment)

}
