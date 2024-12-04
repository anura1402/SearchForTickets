package ru.anura.emtesttask.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.anura.emtesttask.R
import ru.anura.emtesttask.data.model.Ticket
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

class TicketsListAdapter : RecyclerView.Adapter<TicketsListAdapter.TicketItemViewHolder>() {

    var ticketsList = listOf<Ticket>()
        set(value) {
            val callback = TicketsListDiffCallback(ticketsList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.flight_item, parent, false)
        return TicketItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketItemViewHolder, position: Int) {
        val currentItem = ticketsList[position]
        holder.price.text = formatPrice(currentItem.price.value)

        val departureTime = getTimeFromDate(currentItem.departure.date)
        val arrivalTime = getTimeFromDate(currentItem.arrival.date)
        val time = "$departureTime — $arrivalTime"
        holder.tvTime.text = time

        holder.tvDeparture.text = currentItem.departure.airport
        holder.tvArrival.text = currentItem.arrival.airport
        val differenceInDays =
            calculateDaysDifference(currentItem.departure.date, currentItem.arrival.date)
        val differenceInTime = if (differenceInDays.toInt() == 0) {
            calculateTimeDifferenceInHours(departureTime, arrivalTime)
        } else {
            val hours = calculateTimeDifferenceInHours(
                departureTime,
                arrivalTime
            ) + 24 * differenceInDays
            hours

        }
        val difference = String.format(Locale.US, "%.1fч в пути", differenceInTime)
        holder.tvTimeInWay.text = difference

        //Log.d("BadgeTag", "id: ${currentItem.id} hasTransfer: ${currentItem.hasTransfer} ")
        if (!currentItem.hasTransfer) {
            holder.withoutTransfers.visibility = View.VISIBLE
        } else {
            View.INVISIBLE
        }
        Log.d("BadgeTag", "id: ${currentItem.id} badge: ${currentItem.badge} ")
        if (currentItem.badge != null) {
            holder.tvBadge.visibility = View.VISIBLE
            holder.tvBadge.text = currentItem.badge
        }
    }

    private fun calculateTimeDifferenceInHours(departureTime: String, arrivalTime: String): Double {
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val departureLocalTime = LocalTime.parse(departureTime, timeFormatter)
        val arrivalLocalTime = LocalTime.parse(arrivalTime, timeFormatter)
        return Duration.between(departureLocalTime, arrivalLocalTime).toMinutes() / 60.0
    }

    private fun formatPrice(price: Int): String {
        val formatter = DecimalFormat("#,###", DecimalFormatSymbols(Locale("ru", "RU")))
        return formatter.format(price) + " ₽"
    }

    private fun calculateDaysDifference(date1: String, date2: String): Long {
        val dateOnly1 = date1.substring(0, 10)
        val dateOnly2 = date2.substring(0, 10)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val localDate1 = LocalDate.parse(dateOnly1, formatter)
        val localDate2 = LocalDate.parse(dateOnly2, formatter)

        return ChronoUnit.DAYS.between(localDate1, localDate2)
    }

    private fun getTimeFromDate(date: String): String {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val dateTime = LocalDateTime.parse(date, formatter)
        val time = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
        return time
    }

    override fun getItemCount(): Int {
        return ticketsList.size
    }

    class TicketItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView = view.findViewById(R.id.tv_price)
        val tvTime: TextView = view.findViewById(R.id.tv_time)
        val tvDeparture: TextView = view.findViewById(R.id.tv_departure_airport)
        val tvArrival: TextView = view.findViewById(R.id.tv_arrival_airport)
        val tvTimeInWay: TextView = view.findViewById(R.id.tv_time_in_way)
        val withoutTransfers: TextView = view.findViewById(R.id.without_transfers)
        val tvBadge: TextView = view.findViewById(R.id.tv_badge)
    }

}