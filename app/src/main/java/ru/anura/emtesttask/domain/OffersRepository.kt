package ru.anura.emtesttask.domain

import ru.anura.emtesttask.domain.model.Offers
import ru.anura.emtesttask.domain.model.OffersTickets
import ru.anura.emtesttask.domain.model.Tickets

interface OffersRepository {
    suspend fun getOffers(): Offers?
    suspend fun getOffersTickets(): OffersTickets?
    suspend fun getTickets(): Tickets?
}