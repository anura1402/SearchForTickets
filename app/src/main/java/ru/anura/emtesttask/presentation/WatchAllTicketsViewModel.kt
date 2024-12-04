package ru.anura.emtesttask.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anura.emtesttask.data.model.OfferTickets
import ru.anura.emtesttask.data.model.Ticket
import ru.anura.emtesttask.data.model.Tickets
import ru.anura.emtesttask.domain.GetOffersTicketsUseCase
import ru.anura.emtesttask.domain.GetTicketsUseCase
import javax.inject.Inject

class WatchAllTicketsViewModel@Inject constructor(private val getTicketsUseCase: GetTicketsUseCase): ViewModel() {
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
}