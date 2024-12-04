package ru.anura.emtesttask.data

import android.util.Log
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

class MockServer(private val inputStream: InputStream) {

    private val mockWebServer = MockWebServer()

    // Метод для старта мок-сервера
    fun start() {

        // Подготавливаем ответ из локального файла JSON
        val jsonResponse = inputStream.bufferedReader().use { it.readText() }
        Log.d("MainActivityOffer", "Content of offers.json: $jsonResponse")
        // Настроим MockWebServer для возврата этого JSON как ответа на GET-запрос
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // Запускаем сервер
        mockWebServer.start()
        Log.d("MainActivityOffer", "Mock Web Server started at: ${mockWebServer.url("/")}")
    }

    // Получаем URL мок-сервера для использования в Retrofit
    fun getUrl(): String {
        Log.d("MainActivityOffer", "urlMock: ${mockWebServer.url("/").toString()}")
        return mockWebServer.url("/").toString()

    }

    // Останавливаем сервер
    fun stop() {
        mockWebServer.shutdown()
    }
}