package ru.anura.common.repository

import ru.anura.common.model.Offers
import ru.anura.common.model.OffersTickets
import ru.anura.common.model.Tickets

interface OffersRepository {
    suspend fun getOffers(): Offers?
    suspend fun getOffersTickets(): OffersTickets?
    suspend fun getTickets(): Tickets?
}