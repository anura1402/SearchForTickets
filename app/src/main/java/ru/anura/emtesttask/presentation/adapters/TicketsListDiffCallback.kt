package ru.anura.emtesttask.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.anura.emtesttask.data.model.Offer
import ru.anura.emtesttask.data.model.Ticket

class TicketsListDiffCallback (
    private val oldList: List<Ticket>,
    private val newList: List<Ticket>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}