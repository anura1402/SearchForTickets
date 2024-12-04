package ru.anura.emtesttask.presentation

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anura.emtesttask.domain.GetOffersTicketsUseCase
import ru.anura.emtesttask.domain.model.OfferTickets
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TheCountryWasChosenViewModel  @Inject constructor(private val getOffersTicketsUseCase: GetOffersTicketsUseCase): ViewModel() {
    private val _offersTickets = MutableLiveData<List<OfferTickets>>()
    val offersTickets: LiveData<List<OfferTickets>> = _offersTickets

    private var selectedDateForButtonDate: Date? = null


    fun getOffersTickets() {
        viewModelScope.launch {
            val result = getOffersTicketsUseCase()
            if (result != null) {
                _offersTickets.value = result.offersTickets
            }
            Log.d("MainActivityOffer", "OffersTickets: $result")
        }
    }

    fun formatDateWithStyle(date: Date): SpannableString {
        val dateFormat = SimpleDateFormat("d MMM, E", Locale("ru")).format(date).replace(".", "")
        val spannableString = SpannableString(dateFormat)
        val dayOfWeekStartIndex = dateFormat.indexOf(",") + 2
        spannableString.setSpan(
            ForegroundColorSpan(Color.GRAY),
            dayOfWeekStartIndex,
            dateFormat.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }
    fun getMinDateForBackWay(): Long {
        return selectedDateForButtonDate?.time ?: Calendar.getInstance().timeInMillis
    }
    fun updateSelectedDateForButtonDate(date: Date) {
        selectedDateForButtonDate = date
    }
}