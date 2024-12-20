package ru.anura.feature_tickets.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anura.common.model.Ticket
import ru.anura.feature_tickets.usecases.GetTicketsUseCase
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class WatchAllTicketsViewModel @Inject constructor(private val getTicketsUseCase: GetTicketsUseCase) :
    ViewModel() {
    private val _tickets = MutableLiveData<List<Ticket>>()
    val tickets: LiveData<List<Ticket>> = _tickets

    fun getTickets() {
        viewModelScope.launch {
            val result = getTicketsUseCase()
            if (result != null) {
                _tickets.value = result.tickets
            }
            Log.d("MainActivityOffer", "Tickets: $result")
        }
    }

    fun getFormattedDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("d MMM, E", Locale("ru"))
        val outputFormat = SimpleDateFormat("d MMMM", Locale("ru"))
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date ?: throw IllegalArgumentException("Invalid date"))
    }
}