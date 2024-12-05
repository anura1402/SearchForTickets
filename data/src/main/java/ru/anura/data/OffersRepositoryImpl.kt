package ru.anura.data

import android.util.Log
import kotlinx.coroutines.delay
import ru.anura.common.model.Offers
import ru.anura.common.model.OffersTickets
import ru.anura.common.model.Tickets
import ru.anura.common.repository.OffersRepository
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