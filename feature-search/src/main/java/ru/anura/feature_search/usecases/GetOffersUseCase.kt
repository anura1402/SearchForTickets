package ru.anura.feature_search.usecases

import ru.anura.common.repository.OffersRepository
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(private val repository: OffersRepository) {

    suspend operator fun invoke() = repository.getOffers()
}