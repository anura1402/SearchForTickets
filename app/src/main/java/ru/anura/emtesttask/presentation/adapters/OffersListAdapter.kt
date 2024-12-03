package ru.anura.emtesttask.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.anura.emtesttask.R
import ru.anura.emtesttask.domain.FlyMusicallyItem

class OffersListAdapter(private val itemList: List<FlyMusicallyItem>) : RecyclerView.Adapter<OffersListAdapter.OfferItemViewHolder>() {

    var count = 0
//        var shopList = listOf<ShopItem>()
//            set(value) {
//                val callback = ShopListDiffCallback(shopList, value)
//                val diffResult= DiffUtil.calculateDiff(callback)
//                diffResult.dispatchUpdatesTo(this)
//                field = value
//            }
//        var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
//        var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fly_musically_item, parent, false)
        return OfferItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.image.setImageResource(currentItem.imageId)
        holder.title.text = currentItem.title
        holder.town.text = currentItem.town
        holder.price.text = currentItem.price
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class OfferItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.findViewById(R.id.iv_fly_musically)
            val title: TextView = view.findViewById(R.id.fly_musically_star)
            val town: TextView = view.findViewById(R.id.fly_musically_destination)
            val price: TextView = view.findViewById(R.id.fly_musically_price)
    }

}