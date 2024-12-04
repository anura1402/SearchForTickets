package ru.anura.emtesttask.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anura.emtesttask.data.OffersRepositoryImpl
import ru.anura.emtesttask.data.model.Offer
import ru.anura.emtesttask.domain.GetOffersUseCase
import ru.anura.emtesttask.domain.OffersRepository
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(private val getOffersUseCase:GetOffersUseCase): ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    fun getOffers() {
        viewModelScope.launch {
            val result = getOffersUseCase()
            if (result != null) {
                _offers.value = result.offers
            }
            Log.d("MainActivityOffer", "Offer: $result")
        }
    }
}