package ru.anura.emtesttask.data

import android.app.Application
import android.util.Log
import retrofit2.Response
import ru.anura.emtesttask.data.model.Offer
import ru.anura.emtesttask.data.model.Offers
import ru.anura.emtesttask.domain.OffersRepository
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : OffersRepository {

    override suspend fun getOffers(): List<Offer>? {
        return try {
            Log.d("OffersRepository", "Fetching offers from API...")
            val response  = apiService.getJsonOffers()
            Log.d("OffersRepository", "Response received: $response")
            response .offers
        } catch (e: Exception) {
            Log.d("OffersRepository", "Error: ${e.message}")
            null
        }
    }
}