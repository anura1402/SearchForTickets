package ru.anura.emtesttask.data

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

class MockServer @Inject constructor(private val inputStreamMap: Map<String, InputStream>) {

    private val mockWebServer = MockWebServer()
    private val serverReadyDeferred = CompletableDeferred<String>()
    private val cachedResponses = mutableMapOf<String, String>()

    suspend fun start() {
        withContext(Dispatchers.IO) {
            mockWebServer.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    Log.d("MockServer", "Received request: ${request.method} ${request.path}")
                    val path = request.path?.removePrefix("/")
                    if (path == null) {
                        Log.d("MockServer", "Path is null!")
                        return MockResponse().setResponseCode(400).setBody("Bad Request")
                    }

                    // Если уже считаны данные для этого пути, возвращаем их
                    val responseBody = cachedResponses[path]
                    if (responseBody == null) {
                        // Если данных еще нет в кэше, читаем поток
                        val inputStream = inputStreamMap[path]
                        if (inputStream == null) {
                            Log.d("MockServer", "No input stream found for path: $path")
                            return MockResponse().setResponseCode(404).setBody("Not Found")
                        }
                        val newResponseBody = inputStream.bufferedReader().use { it.readText() }
                        Log.d("MockServer", "Response body: $newResponseBody")
                        // Сохраняем ответ в кэш для повторного использования
                        cachedResponses[path] = newResponseBody
                        return MockResponse().setResponseCode(200).setBody(newResponseBody)
                    }

                    // Если данные уже были считаны, просто возвращаем их из кэша
                    Log.d("MockServer", "Returning cached response for $path")
                    return MockResponse().setResponseCode(200).setBody(responseBody)
                }
            }
            mockWebServer.start()
            Log.d("MockServer", "Mock server started at: ${mockWebServer.url("/")}")
            serverReadyDeferred.complete(mockWebServer.url("/").toString())
        }
    }


    suspend fun getUrl(): String {
        return withContext(Dispatchers.IO) {
            mockWebServer.url("/").toString()
        }
    }

    fun stop() {
        mockWebServer.shutdown()
    }
}