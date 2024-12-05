package ru.anura.feature_tickets.usecases

import ru.anura.common.repository.OffersRepository
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(private val repository: OffersRepository) {

    suspend operator fun invoke() = repository.getTickets()
}