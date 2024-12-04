package ru.anura.emtesttask.domain

import ru.anura.emtesttask.data.model.Offer
import ru.anura.emtesttask.data.model.OfferTickets
import ru.anura.emtesttask.data.model.Offers
import ru.anura.emtesttask.data.model.OffersTickets
import ru.anura.emtesttask.data.model.Tickets

interface OffersRepository {
    suspend fun getOffers(): Offers?
    suspend fun getOffersTickets(): OffersTickets?
    suspend fun getTickets(): Tickets?
}