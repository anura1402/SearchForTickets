package ru.anura.emtesttask.data

import retrofit2.Response
import retrofit2.http.GET
import ru.anura.emtesttask.data.model.Offers

interface ApiService {
    @GET("offers")
    suspend fun getJsonOffers(): Offers
}