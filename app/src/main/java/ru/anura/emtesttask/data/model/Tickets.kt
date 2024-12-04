package ru.anura.emtesttask.data.model

import com.google.gson.annotations.SerializedName

data class Tickets(
    val tickets: List<Ticket>
)
data class Ticket(
    val id: Int,
    val badge: String,
    val price: Price,
    val providerName: String,
    val company: String,
    val departure: Departure,
    val arrival: Arrival,
    @SerializedName("has_transfer") val hasTransfer: Boolean,
    val hasVisaTransfer: Boolean,
    val luggage: Luggage,
    val handLuggage: HandLuggage,
    val isReturnable: Boolean,
    val isExchangeable: Boolean
)
data class Departure(
    val town: String,
    val date: String,
    val airport: String
)
data class Arrival(
    val town: String,
    val date: String,
    val airport: String
)
data class Luggage(
    val hasLuggage: Boolean,
    val price: Price
)
data class HandLuggage(
    val hasHandLuggage: Boolean,
    val size: String
)