package ru.anura.emtesttask.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anura.emtesttask.data.model.Offer
import ru.anura.emtesttask.data.model.OfferTickets
import ru.anura.emtesttask.data.model.OffersTickets
import ru.anura.emtesttask.domain.GetOffersTicketsUseCase
import javax.inject.Inject

class TheCountryWasChosenViewModel  @Inject constructor(private val getOffersTicketsUseCase: GetOffersTicketsUseCase): ViewModel() {
    private val _offersTickets = MutableLiveData<List<OfferTickets>>()
    val offersTickets: LiveData<List<OfferTickets>> = _offersTickets

    fun getOffersTickets() {
        viewModelScope.launch {
            val result = getOffersTicketsUseCase()
            if (result != null) {
                _offersTickets.value = result.offersTickets
            }
            Log.d("MainActivityOffer", "OffersTickets: $result")
        }
    }
}