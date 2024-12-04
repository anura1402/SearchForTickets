package ru.anura.emtesttask.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    companion object {
        @Provides
        @Singleton
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }

        @Provides
        fun provideSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        }
    }
}