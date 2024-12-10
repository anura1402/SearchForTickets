package ru.anura.data.di

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.anura.data.ApiFactory
import ru.anura.data.ApiService
import ru.anura.data.MockServer
import java.io.InputStream
import javax.inject.Singleton


@Module
interface NetworkModule {
    companion object {

        @Provides
        @Singleton
        @JvmSuppressWildcards
        fun provideMockServer(inputStreamMap: Map<String, InputStream>): MockServer {
            val mockServer = MockServer(inputStreamMap)
            val job = CoroutineScope(Dispatchers.IO).launch {
                mockServer.start()
            }
            runBlocking {
                job.join()
            }
            return mockServer
        }

        @Provides
        @Singleton
        @JvmSuppressWildcards
        fun provideInputStreamMap(context: Context): Map<String, InputStream> {
            // Карта файлов, где ключ - это endpoint, а значение - InputStream файла
            return mapOf(
                "offers" to context.assets.open("offers.json"),
                "offers_tickets" to context.assets.open("offers_tickets.json"),
                "tickets" to context.assets.open("tickets.json")
            )
        }

        @Provides
        @Singleton
        fun provideApiService(mockServer: MockServer): ApiService {
            val baseUrl = runBlocking {
                mockServer.getUrl()
            }
            Log.d("MockServer", "Using base URL: $baseUrl")
            return ApiFactory.createApiService(baseUrl)
        }
    }
}
