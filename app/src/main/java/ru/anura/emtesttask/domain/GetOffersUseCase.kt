package ru.anura.emtesttask.domain

class GetOffersUseCase(private val offersRepository: OffersRepository) {

    suspend operator fun invoke() = offersRepository.getOffers()
}