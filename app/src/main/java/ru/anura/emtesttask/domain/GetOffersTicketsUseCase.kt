package ru.anura.emtesttask.domain

import javax.inject.Inject

class GetOffersTicketsUseCase @Inject constructor(private val repository: OffersRepository) {

    suspend operator fun invoke() = repository.getOffersTickets()
}