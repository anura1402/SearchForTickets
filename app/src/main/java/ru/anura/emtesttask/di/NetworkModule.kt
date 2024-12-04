package ru.anura.emtesttask.di

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.anura.emtesttask.data.ApiFactory
import ru.anura.emtesttask.data.ApiService
import ru.anura.emtesttask.data.MockServer
import java.io.InputStream
import javax.inject.Singleton


@Module
interface NetworkModule {
    companion object {
        @Provides
        @Singleton
        fun provideMockServer(inputStream: InputStream): MockServer {
            return MockServer(inputStream).apply {
                CoroutineScope(Dispatchers.IO).launch {
                    start()
                }
            }
        }

        @Provides
        @Singleton
        fun provideInputStream(context: Context): InputStream {
            return context.assets.open("offers.json")
        }

        @Provides
        @Singleton
        fun provideApiService(mockServer: MockServer): ApiService {
            val baseUrl = mockServer.getUrl()
            return ApiFactory.createApiService(baseUrl)
        }
    }
}