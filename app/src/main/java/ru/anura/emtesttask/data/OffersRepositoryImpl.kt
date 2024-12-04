package ru.anura.emtesttask.data

import android.util.Log
import kotlinx.coroutines.delay
import ru.anura.emtesttask.domain.OffersRepository
import ru.anura.emtesttask.domain.model.Offers
import ru.anura.emtesttask.domain.model.OffersTickets
import ru.anura.emtesttask.domain.model.Tickets
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : OffersRepository {

    override suspend fun getOffers(): Offers? {
        return try {
            Log.d("OffersRepository", "Fetching offers from API...")
            val response  = apiService.getJsonOffers()
            Log.d("OffersRepository", "Response received: $response")
            response
        } catch (e: Exception) {
            Log.d("OffersRepository", "Error: ${e.message}")
            null
        }
    }

    override suspend fun getOffersTickets(): OffersTickets? {
        return try {
            Log.d("OffersRepository", "Fetching offers from API...")
            val response  = apiService.getJsonOffersTickets()
            Log.d("OffersRepository", "Response received: $response")
            response
        } catch (e: Exception) {
            Log.d("OffersRepository", "Error: ${e.message}")
            null
        }
    }

    override suspend fun getTickets(): Tickets? {
        delay(500)
        return try {
            Log.d("OffersRepository", "Fetching offers from API...")
            val response  = apiService.getJsonTickets()
            Log.d("OffersRepository", "Response received: $response")
            response
        } catch (e: Exception) {
            Log.d("OffersRepository", "Error: ${e.message}")
            null
        }
    }

}