package ru.anura.emtesttask.presentation

import android.app.Application
import ru.anura.emtesttask.di.DaggerAppComponent
import javax.inject.Inject

class SearchApp : Application(){

    val component by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

}