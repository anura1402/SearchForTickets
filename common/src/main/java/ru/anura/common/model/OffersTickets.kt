package ru.anura.common.model

import com.google.gson.annotations.SerializedName

data class OffersTickets(
    @SerializedName("tickets_offers")
    val offersTickets: List<OfferTickets>
)
data class OfferTickets(
    val id: Int,
    val title:String,
    @SerializedName("time_range") val timeRange: List<String>,
    val price: Price
)