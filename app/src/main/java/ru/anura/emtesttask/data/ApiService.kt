package ru.anura.emtesttask.data

import android.util.Log
import retrofit2.Response
import retrofit2.http.GET
import ru.anura.emtesttask.domain.model.Offers
import ru.anura.emtesttask.domain.model.OffersTickets
import ru.anura.emtesttask.domain.model.Tickets

interface ApiService {
    @GET("offers")
    suspend fun getJsonOffers(): Offers

    @GET("offers_tickets")
    suspend fun getJsonOffersTickets(): OffersTickets

    @GET("tickets")
    suspend fun getJsonTickets(): Tickets
}