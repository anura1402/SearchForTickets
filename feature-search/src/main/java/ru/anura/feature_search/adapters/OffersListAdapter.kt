package ru.anura.feature_search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.anura.common.model.Offer
import ru.anura.feature_search.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class OffersListAdapter: RecyclerView.Adapter<OffersListAdapter.OfferItemViewHolder>() {

    private lateinit var context: Context

    var offerList = listOf<Offer>()
        set(value) {
            val callback = OffersListDiffCallback(offerList, value)
            val diffResult= DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }
    private val imageList = listOf(R.drawable.die_antwood, R.drawable.socrat_and_lera, R.drawable.lampabict)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fly_musically_item, parent, false)
        context = parent.context
        return OfferItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferItemViewHolder, position: Int) {
        val currentItem = offerList[position]
        holder.image.setImageResource(imageList[position])
        holder.title.text = currentItem.title
        holder.town.text = currentItem.town
        val priceString = context.getString(R.string.price,formatPrice(currentItem.price.value))
        holder.price.text = priceString
    }

    private fun formatPrice(price:Int):String{
        val formatter = DecimalFormat("#,###", DecimalFormatSymbols(Locale("ru", "RU")))
        return formatter.format(price)
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    class OfferItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.iv_fly_musically)
            val title: TextView = view.findViewById(R.id.fly_musically_star)
            val town: TextView = view.findViewById(R.id.fly_musically_destination)
            val price: TextView = view.findViewById(R.id.fly_musically_price)

    }

}