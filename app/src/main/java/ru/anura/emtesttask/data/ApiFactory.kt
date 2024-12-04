package ru.anura.emtesttask.data

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    //private const val BASE_URL = "https://example.com/"

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor { chain ->
                    val request = chain.request()
                    val response = chain.proceed(request)
                    val responseBody = response.peekBody(Long.MAX_VALUE).string()
                    Log.d("MainActivityOffer", "Request: ${request.url}")
                    Log.d("MainActivityOffer", "Response Code: ${response.code}")
                    Log.d("MainActivityOffer", "Response: ${responseBody}")
                    response
                }.build()
            )
            .build()
    }

    fun createApiService(baseUrl: String): ApiService {
        Log.d("MainActivityOffer", "urlApi: ${baseUrl}")
        val retrofit = createRetrofit(baseUrl)
        return retrofit.create(ApiService::class.java)
    }
}
