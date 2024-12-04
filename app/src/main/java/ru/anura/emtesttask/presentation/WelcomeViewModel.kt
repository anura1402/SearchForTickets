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

class WelcomeViewModel(private val repository: OffersRepository): ViewModel() {
    //private val repository = OffersRepositoryImpl(application)

    private val getOffersUseCase= GetOffersUseCase(repository)

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    fun getOffers() {
        viewModelScope.launch {
            val result = getOffersUseCase()
            _offers.value = result ?: emptyList()
            Log.d("MainActivityOffer", "Offer: $result")
        }
    }
}