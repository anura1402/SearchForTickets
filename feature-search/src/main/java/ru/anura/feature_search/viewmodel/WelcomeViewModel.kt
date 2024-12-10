package ru.anura.feature_search.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anura.common.model.Offer
import ru.anura.feature_search.usecases.GetDataUseCase
import ru.anura.feature_search.usecases.GetOffersUseCase
import ru.anura.feature_search.usecases.SaveDataUseCase
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val saveDataUseCase: SaveDataUseCase,
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {

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
        _cachedText.value = getDataUseCase(SHARED_PREFS_KEY, SHARED_PREFS_DEFAULT_VALUE)
    }

    fun saveCachedText(text: String) {
        saveDataUseCase(SHARED_PREFS_KEY, text)
    }

    companion object{
        private const val SHARED_PREFS_KEY = "lastInput"
        private const val SHARED_PREFS_DEFAULT_VALUE = ""
    }
}