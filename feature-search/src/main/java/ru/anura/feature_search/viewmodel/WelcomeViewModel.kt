package ru.anura.feature_search.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anura.common.model.Offer
import ru.anura.feature_search.usecases.GetOffersUseCase
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val newDetailsComponent = DaggerFeatureSearchComponent.builder().build()

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private val _cachedText = MutableLiveData<String>()
    val cachedText: LiveData<String> get() = _cachedText

    fun getOffers() {
        viewModelScope.launch {
            val result = getOffersUseCase()
            if (result != null) {
                _offers.value = result.offers
            }
        }
    }

    fun loadCachedText() {
        _cachedText.value = sharedPreferences.getString("lastInput", "") ?: ""
    }

    fun saveCachedText(text: String) {
        sharedPreferences.edit().putString("lastInput", text).apply()
    }
}