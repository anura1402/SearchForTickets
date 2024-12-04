package ru.anura.emtesttask.data.model

data class OffersTickets(
    val offerTickets: List<OfferTickets>
)
data class OfferTickets(
    val id: Int,
    val title:String,
    val timeRange: List<String>,
    val price: Price
)