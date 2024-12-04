package ru.anura.emtesttask.domain

import ru.anura.emtesttask.data.model.Offer

interface OffersRepository {
    suspend fun getOffers(): List<Offer>?
}