package ru.anura.emtesttask.domain

import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(private val repository: OffersRepository) {

    suspend operator fun invoke() = repository.getTickets()
}