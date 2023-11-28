package com.tomsk.alykov.easypaytesttask.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.tomsk.alykov.easypaytesttask.data.network.models.PaymentModel

class PaymentsDiffCallback(
    private val oldList: List<PaymentModel>,
    private val newList: List<PaymentModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}