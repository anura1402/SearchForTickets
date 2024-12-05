package ru.anura.data

import retrofit2.http.GET
import ru.anura.common.model.Offers
import ru.anura.common.model.OffersTickets
import ru.anura.common.model.Tickets

interface ApiService {
    @GET("offers")
    suspend fun getJsonOffers(): Offers

    @GET("offers_tickets")
    suspend fun getJsonOffersTickets(): OffersTickets

    @GET("tickets")
    suspend fun getJsonTickets(): Tickets
}